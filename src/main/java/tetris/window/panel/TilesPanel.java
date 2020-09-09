package tetris.window.panel;


import tetris.board.Board;
import tetris.gameMechanic.ShapeQueue;
import tetris.gameObject.TetrisShape;
import tetris.window.Window;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TilesPanel extends JPanel {

    public static final int MINIMUM_PANEL_WIDTH     = 200;
    public static final int MINIMUM_PANEL_HEIGHT    = 0;

    private Window parentWindow = null;

    public void draw (ShapeQueue shapeQueue, Graphics drawGraphics ) {
        Rectangle renderArea = this.parentWindow.getGameRenderPanel().getRenderSize();

        int xOffset = this.parentWindow.getWidth() - this.getWidth() + ( this.getWidth() - 3 * Board.getTileSquareLength() ) / 2;
        int yOffset = renderArea.y + 150;

        drawGraphics.setFont ( new Font("SansSerif", Font.BOLD, 15) );
        drawGraphics.drawString("Next Shapes : ", xOffset + 10, yOffset - 30);


        for ( int i = 0; i < 4 * 3 + 2; i++ )
            for ( int j = 0; j < 3; j++ ) {

                if ( i != 4 && i != 9 ) {
                    drawGraphics.setColor(Color.WHITE);
                    drawGraphics.fillRect(
                            xOffset + j * Board.getTileSquareLength(),
                            yOffset + i * Board.getTileSquareLength(),
                            Board.getTileSquareLength(),
                            Board.getTileSquareLength()
                    );
                    drawGraphics.setColor(Color.BLACK);
                    drawGraphics.drawRect(
                            xOffset + j * Board.getTileSquareLength(),
                            yOffset + i * Board.getTileSquareLength(),
                            Board.getTileSquareLength(),
                            Board.getTileSquareLength()
                    );
                }
            }

        int vertOffset = yOffset + Board.getTileSquareLength();
        List<TetrisShape> shapes = shapeQueue.getShapes();
        for ( TetrisShape shape : shapes ) {
            shape.drawShapeAt(xOffset + Board.getTileSquareLength(), vertOffset, drawGraphics);
            vertOffset += 5 * Board.getTileSquareLength();
        }
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

    private void configurePanel () {
        super.setMinimumSize( new Dimension ( TilesPanel.MINIMUM_PANEL_WIDTH, TilesPanel.MINIMUM_PANEL_HEIGHT ) );
        super.setPreferredSize( this.getMinimumSize() );
    }

    public TilesPanel ( Window parentWindow ) {
        super();
        this.parentWindow = parentWindow;

        this.configurePanel();
    }
}
