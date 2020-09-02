package tetris.application;

import tetris.board.Board;
import tetris.gameMechanic.FrameTimer;
import tetris.gameMechanic.ShapeKeyListener;
import tetris.gameMechanic.ShapeQueue;
import tetris.gameObject.ShapeCollideException;
import tetris.gameObject.ShapeNoSpaceException;
import tetris.gameObject.TetrisShape;
import tetris.window.Window;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Application {

    public static final int DEFAULT_FPS = 60;

    public static final int SQUARE_COUNT_WIDTH  = 10;
    public static final int SQUARE_COUNT_HEIGHT = 20;

    private final Window    gameWindow;
    private Board           gameBoard;

    private FrameTimer      dropShapesTimer;
    private FrameTimer      descendCoolDown;
    private FrameTimer      moveCoolDown;
    private FrameTimer      rotateCoolDown;

    private ShapeQueue      shapeQueue;

    private TetrisShape     activeShape;

    private ShapeKeyListener shapeKeyListener;

    private int fps = Application.DEFAULT_FPS;

    private boolean isRotatePressed = false;

    private void debugDrawSquareSeparators ( Graphics graphics, Rectangle drawArea ) {
        int width = drawArea.width / SQUARE_COUNT_WIDTH;
        int height = drawArea.height / SQUARE_COUNT_HEIGHT;

        int length = Math.min ( width, height );

        int xOffset = drawArea.x + ( drawArea.width - SQUARE_COUNT_WIDTH * length ) / 2;
        int yOffset = drawArea.y + Window.DEFAULT_WINDOW_HEIGHT / 90;

        System.out.println( width + ", " + height );

        for ( int i = 0; i < SQUARE_COUNT_HEIGHT; i++ ) {
            for ( int j = 0; j < SQUARE_COUNT_WIDTH; j++ ) {
                graphics.drawRect(
                    j * length + xOffset,
                    i * length + yOffset,
                    length,
                    length
                );
            }
        }
    }

    public void buildGameComponents () {
        this.gameBoard = new Board( this );
        this.dropShapesTimer = new FrameTimer( 30 );
        this.descendCoolDown = new FrameTimer( 5 );
        this.moveCoolDown    = new FrameTimer( 3 );
        this.rotateCoolDown  = new FrameTimer( 1 );
        this.shapeQueue = new ShapeQueue();
    }

    public Application () {
        this.gameWindow = new Window( this, Window.DEFAULT_WINDOW_TITLE );
        this.gameWindow.setLocationRelativeTo( null );
        this.gameWindow.setResizable( false );
    }

    public void draw () {
        Graphics drawGraphics = this.gameWindow.getGameRenderPanel().getDrawGraphics();

        drawGraphics.clearRect( 0, 0, this.gameWindow.getGameRenderPanel().getWidth(), this.gameWindow.getGameRenderPanel().getHeight() );

        drawGraphics.setColor( Color.BLACK );
        Rectangle rectArea = this.gameWindow.getGameRenderPanel().getRenderSize();

        drawGraphics.drawRect(
                rectArea.x,
                rectArea.y,
                rectArea.width,
                rectArea.height
        );

        this.gameBoard.draw( drawGraphics );
        this.gameWindow.getGameRenderPanel().draw();
    }

    public void gameUpdate () {
        if ( this.activeShape != null ) {
            try {
                this.activeShape.update();
            } catch ( ShapeCollideException ignored) {
                this.activeShape = null;

                try {
                    this.spawnShape();
                } catch ( ShapeNoSpaceException exception ) {
                    System.out.println( exception.toString() );
                    this.activeShape = null;
                }

                this.removeLines();
            }
        }
    }

    private void removeLines () {
        int lineToBeRemovedIndex = this.gameBoard.getFullLineIndex();

        while ( lineToBeRemovedIndex != -1 ) {
            this.gameBoard.removeLine ( lineToBeRemovedIndex, this.activeShape );

            lineToBeRemovedIndex = this.gameBoard.getFullLineIndex();
        }
    }

    public void frameUpdate () {
        try {
            if (this.activeShape == null)
                this.spawnShape();
        } catch ( ShapeNoSpaceException exception ) {
            System.out.println( exception.toString() );
            this.activeShape = null;
        }

        if ( this.activeShape != null ) {
            try {
                if (this.descendCoolDown.isDone()) {
                    this.activeShape.descend(this.shapeKeyListener);
                }

                if (this.moveCoolDown.isDone()) {
                    this.activeShape.move(this.shapeKeyListener);
                }

                if (this.rotateCoolDown.isDone() & !this.isRotatePressed) {
                    this.isRotatePressed = true;
                    this.activeShape.rotate(this.shapeKeyListener);
                } else if (!this.shapeKeyListener.isLeftRotatePressed() && !this.shapeKeyListener.isRightRotatePressed())
                    this.isRotatePressed = false;
            } catch ( ShapeCollideException ignored ) {
                this.activeShape = null;

                try {
                    this.spawnShape();
                } catch ( ShapeNoSpaceException exception ) {
                    System.out.println( exception.toString() );
                    this.activeShape = null;
                }

                this.removeLines();
            }
        }

        this.dropShapesTimer.update();
        this.descendCoolDown.update();
        this.moveCoolDown.update();
        this.rotateCoolDown.update();
        if ( this.dropShapesTimer.isDone() && ! this.shapeKeyListener.isFastDescendPressed() ) {
//            System.out.println("tick");
            this.gameUpdate();
        }

        this.draw();
    }

    private void spawnShape() throws ShapeNoSpaceException {
        if ( this.activeShape != null )
            return;

        this.activeShape = this.shapeQueue.pop();
        System.out.println(" Spawning :  " + this.activeShape.toString());

        this.activeShape.spawn(this.gameBoard);
    }

    public void run () {
        this.gameWindow.setVisible( true );
        this.buildGameComponents();

        this.dropShapesTimer.start();
        this.descendCoolDown.start();
        this.moveCoolDown.start();
        this.rotateCoolDown.start();

        this.shapeKeyListener = new ShapeKeyListener();

        this.gameWindow.addKeyListener( this.shapeKeyListener );

        int sleepTimer = 1000 / this.fps;

        Thread gameThread = new Thread (
            () -> {
                long startFrameTime = 0;
                long frameTime = 0;

                while ( true ) {
                    startFrameTime = System.nanoTime();

                    frameUpdate();

                    frameTime = System.nanoTime() - startFrameTime;
                    int frameTimeInMS = (int) (frameTime / 1000000);
                    try { 

                        if ( sleepTimer >= frameTimeInMS )
                            Thread.sleep( sleepTimer - frameTimeInMS );

                    } catch ( InterruptedException ignored ) {

                    }
                }
            }
        );

        gameThread.start();
    }

    public Window getGameWindow() {
        return gameWindow;
    }

    public static void main(String[] args) {
        Application app = new Application();
        app.run();
    }
}
