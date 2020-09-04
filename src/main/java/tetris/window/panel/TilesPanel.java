package tetris.window.panel;


import tetris.board.Board;
import tetris.gameMechanic.ShapeQueue;
import tetris.window.Window;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

public class TilesPanel extends JPanel {

    public static final int MINIMUM_PANEL_WIDTH     = 200;
    public static final int MINIMUM_PANEL_HEIGHT    = 0;

    private Window parentWindow = null;

    private Canvas tileCanvas;
    private Graphics drawGraphics;
    private BufferStrategy bufferStrategy;

    @Override
    protected void paintComponent(Graphics g) {
//        super.paintComponent(g);
    }

    public void draw (ShapeQueue shapeQueue ) {
        Graphics drawGraphics = this.getDrawGraphics();

        drawGraphics.clearRect( 0, 0, this.tileCanvas.getWidth(), this.tileCanvas.getHeight() );

        int xOffset = ( this.getWidth() - 3 * Board.getTileSquareLength() ) / 2;

        for ( int i = 0; i < 4 * 3 + 2; i++ )
            for ( int j = 0; j < 3; j++ ) {

                if ( i != 4 && i != 9 ) {
                    drawGraphics.setColor(Color.BLACK);
                    drawGraphics.drawRect(
                            xOffset + j * Board.getTileSquareLength(),
                            i * Board.getTileSquareLength(),
                            Board.getTileSquareLength(),
                            Board.getTileSquareLength()
                    );
                }
            }

        this.draw();
    }

    public void buildQueueTiles () {
        Graphics drawGraphics = this.getGraphics();

        for ( int i = 0; i < 4 * 3 + 2; i++ )
            for ( int j = 0; j < 3; j++ ) {

                drawGraphics.setColor( Color.BLACK );
                drawGraphics.drawRect(
                    j * Board.getTileSquareLength(),
                    i * Board.getTileSquareLength(),
                    Board.getTileSquareLength(),
                    Board.getTileSquareLength()
                );
            }
    }

    private void buildCanvas () {
        this.tileCanvas = new Canvas();
        this.tileCanvas.setFocusable(false);
        this.add ( this.tileCanvas );
        this.tileCanvas.setMinimumSize( super.getSize() );
        this.tileCanvas.setPreferredSize( super.getSize() );
    }

    public Graphics getDrawGraphics () {
        if ( this.tileCanvas == null ) {
            this.buildCanvas();
        }

        if ( this.drawGraphics == null ) {
            if ( this.bufferStrategy == null ) {
                this.bufferStrategy = this.tileCanvas.getBufferStrategy();

                if (this.bufferStrategy == null) {
                    this.tileCanvas.createBufferStrategy(3);
                    this.bufferStrategy = this.tileCanvas.getBufferStrategy();
                }

                this.drawGraphics = this.bufferStrategy.getDrawGraphics();
            }
        }

        return this.drawGraphics;
    }

    public void draw () {
        if ( this.bufferStrategy == null )
            return;

        this.bufferStrategy.show();
        this.drawGraphics.dispose();

        this.bufferStrategy = null;
        this.drawGraphics = null;
    }

    private void configurePanel () {
        super.setMinimumSize( new Dimension ( TilesPanel.MINIMUM_PANEL_WIDTH, TilesPanel.MINIMUM_PANEL_HEIGHT ) );
        super.setPreferredSize( this.getMinimumSize() );

//        super.setBackground( Color.BLUE );
    }

    public TilesPanel ( Window parentWindow ) {
        super();
        this.parentWindow = parentWindow;

        this.configurePanel();
    }
}
