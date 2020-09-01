package tetris.application;

import tetris.board.Board;
import tetris.gameMechanic.FrameTimer;
import tetris.window.Window;

import java.awt.*;

public class Application {

    public static final int DEFAULT_FPS = 60;

    public static final int SQUARE_COUNT_WIDTH  = 10;
    public static final int SQUARE_COUNT_HEIGHT = 20;

    private final Window    gameWindow;
    private Board           gameBoard;

    private FrameTimer      dropShapesTimer;

    private int fps = Application.DEFAULT_FPS;

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
    }

    public Application () {
        this.gameWindow = new Window( this, Window.DEFAULT_WINDOW_TITLE );
        this.gameWindow.setLocationRelativeTo( null );
        this.gameWindow.setResizable( false );
    }

    public void update () {
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

        this.dropShapesTimer.update();
        if ( this.dropShapesTimer.isDone() )
            System.out.println("tick");

//        this.debugDrawSquareSeparators( drawGraphics, rectArea );

        this.gameWindow.getGameRenderPanel().draw();
    }

    public void run () {
        this.gameWindow.setVisible( true );
        this.buildGameComponents();
        this.dropShapesTimer.start();

        int sleepTimer = 1000 / this.fps;

        Thread gameThread = new Thread (
            () -> {
                long startFrameTime = 0;
                long frameTime = 0;

                while ( true ) {
                    startFrameTime = System.nanoTime();

                    update();

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
