package tetris.application;

import tetris.board.Board;
import tetris.gameMechanic.FrameTimer;
import tetris.gameMechanic.ShapeKeyListener;
import tetris.gameMechanic.ShapeQueue;
import tetris.exceptions.ShapeCollideException;
import tetris.exceptions.ShapeNoSpaceException;
import tetris.gameObject.TetrisShape;
import tetris.window.Window;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class Application {

    public static final int DEFAULT_FPS = 60;

    public static final int SQUARE_COUNT_WIDTH  = 10;
    public static final int SQUARE_COUNT_HEIGHT = 20;

    private final Window    gameWindow;
    private Board           gameBoard;

    private FrameTimer      dropShapesTimer;
    private FrameTimer      descendCoolDown;
    private FrameTimer      moveCoolDown;

    private static final int[] points = {40, 100, 300, 1200};
    private static final int[] scoreAccelerateThresholds = {600, 2000, 8000, 32000, 128000};
    private static int currentScoreThresholdIndex = 0;

    private int nextAccelerate = 6;
    /// 6 -> 1, 5 -> 2 ..

    private ShapeQueue      shapeQueue;
    private TetrisShape     activeShape;
    private ShapeKeyListener shapeKeyListener;

    private boolean isRotatePressed = false;

    public int getLevel () {
        return 7 - this.nextAccelerate;
    }

    public int getScoreForLevel ( int rowsRemoved ) {
        return points[rowsRemoved - 1] * this.getLevel();
    }

    public void buildGameComponents () {
        this.gameBoard          = new Board( this );
        this.dropShapesTimer    = new FrameTimer( 30 );
        this.descendCoolDown    = new FrameTimer( 5 );
        this.moveCoolDown       = new FrameTimer( 3 );
        this.shapeQueue         = new ShapeQueue();

        this.gameWindow.getTilesPanel().buildQueueTiles();
    }

    /**
     * Application Ctor
     * Builds window object.
     */
    public Application () {
        this.gameWindow = new Window( this, Window.DEFAULT_WINDOW_TITLE );
        this.gameWindow.setLocationRelativeTo( null );
        this.gameWindow.setResizable( false );
        this.gameWindow.setIgnoreRepaint(true);
    }

    public void draw () {
        BufferStrategy bs = this.gameWindow.getBufferStrategy();
        if(bs == null) {
            this.gameWindow.createBufferStrategy(3);
            bs = this.gameWindow.getBufferStrategy();
        }

        Graphics drawGraphics = bs.getDrawGraphics();

        drawGraphics.clearRect( 0, 0, this.gameWindow.getGameRenderPanel().getWidth(), this.gameWindow.getGameRenderPanel().getHeight() );

        this.gameBoard.draw( drawGraphics );
        this.gameWindow.getPlayerInfoPanel().draw(drawGraphics);

        this.gameWindow.getTilesPanel().draw( this.shapeQueue, drawGraphics);
        drawGraphics.dispose();
        bs.show();
    }

    public void gameUpdate () {
        if ( this.activeShape != null ) {
            try {
                this.activeShape.update();
            } catch ( ShapeCollideException ignored) {
                this.activeShape = null;
                this.spawnShape();

                this.removeLines();
            }
        }
    }

    private void removeLines () {
        int lineToBeRemovedIndex = this.gameBoard.getFullLineIndex();

        int totalLinesRemoved = 0;

        while ( lineToBeRemovedIndex != -1 ) {
            totalLinesRemoved++;
            this.gameBoard.removeLine ( lineToBeRemovedIndex, this.activeShape );

            lineToBeRemovedIndex = this.gameBoard.getFullLineIndex();
        }
        if ( totalLinesRemoved > 0 )
            this.gameWindow.getPlayerInfoPanel().setScore(
                    this.gameWindow.getPlayerInfoPanel().getScore() +
                    this.getScoreForLevel( totalLinesRemoved )
            );
    }

    public void frameUpdate () {
        if (this.activeShape == null)
            this.spawnShape();

        if ( this.activeShape != null ) {
            try {
                if (this.descendCoolDown.isDone()) {
                    this.activeShape.descend(this.shapeKeyListener);
                }

                if (this.moveCoolDown.isDone()) {
                    this.activeShape.move(this.shapeKeyListener);
                }

                if ( !this.isRotatePressed && ( this.shapeKeyListener.isRightRotatePressed() || this.shapeKeyListener.isLeftRotatePressed() ) ) {
                    this.isRotatePressed = true;
                    this.activeShape.rotate(this.shapeKeyListener);
                } else if (!this.shapeKeyListener.isLeftRotatePressed() && !this.shapeKeyListener.isRightRotatePressed())
                    this.isRotatePressed = false;
            } catch ( ShapeCollideException ignored ) {
                this.activeShape = null;
                this.spawnShape();
                this.removeLines();
            }
        }

        this.dropShapesTimer.update();
        this.descendCoolDown.update();
        this.moveCoolDown.update();

        if ( this.gameWindow.getPlayerInfoPanel().getScore() > scoreAccelerateThresholds[currentScoreThresholdIndex] && currentScoreThresholdIndex < scoreAccelerateThresholds.length ) {
            this.dropShapesTimer.changeTimerSetOff( this.dropShapesTimer.getFrameDuration() - this.nextAccelerate );
            this.nextAccelerate--;
            currentScoreThresholdIndex ++;

            this.gameWindow.getPlayerInfoPanel().setLevel( this.getLevel() );
        }

        if ( this.dropShapesTimer.isDone() && ! this.shapeKeyListener.isFastDescendPressed() ) {
            this.gameUpdate();
        }

        this.draw();
    }

    private void stopAllTimers() {
        FrameTimer stopGameTimer = new FrameTimer (1000);

        this.dropShapesTimer.pause();
        this.descendCoolDown.pause();
        this.moveCoolDown.pause();

        stopGameTimer.start();

        while ( ! stopGameTimer.isDone() ) {
            Graphics g = this.gameWindow.getBufferStrategy().getDrawGraphics();
            g.setFont( new Font ("SansSerif", Font.BOLD, 30) );
            g.drawString("You lost", 25, 400);
            g.dispose();
            this.gameWindow.getBufferStrategy().show();
            stopGameTimer.update();
        }
        
        System.exit(0);
    }

    private void spawnShape() {
        if ( this.activeShape != null )
            return;

        this.activeShape = this.shapeQueue.pop();
        System.out.println(" Spawning :  " + this.activeShape.toString());
        try {
            this.activeShape.spawn(this.gameBoard);
        } catch (ShapeNoSpaceException exception) {
            this.stopAllTimers();
        }
    }

    public void run () {
        this.gameWindow.setVisible( true );
        this.buildGameComponents();

        this.dropShapesTimer.start();
        this.descendCoolDown.start();
        this.moveCoolDown.start();

        this.shapeKeyListener = new ShapeKeyListener();

        this.gameWindow.addKeyListener( this.shapeKeyListener );

        int sleepTimer = 1000 / Application.DEFAULT_FPS;

        Thread gameThread = new Thread (
            () -> {
                long startFrameTime ;
                long frameTime ;

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

    /**
     * Main Function for Program
     * @param args args
     */
    public static void main(String[] args) {
        Application app = new Application();
        app.run();
    }
}
