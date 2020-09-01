package tetris.board;

import tetris.application.Application;
import tetris.window.Window;

import java.awt.*;

public class Board {
    private Tile[][] tiles;
    private Application parentApplication;

    private void buildBoard() {
        Rectangle renderArea = this.parentApplication.getGameWindow().getGameRenderPanel().getRenderSize();
        int tileWidthMax = renderArea.width / Application.SQUARE_COUNT_WIDTH;
        int tileHeightMax = renderArea.height / Application.SQUARE_COUNT_HEIGHT;

        int tileSquareLength = Math.min( tileWidthMax, tileHeightMax );

        int tileXOffset = renderArea.x + ( renderArea.width - tileSquareLength * Application.SQUARE_COUNT_WIDTH ) / 2;
        int tileYOffset = renderArea.y + ( Window.DEFAULT_WINDOW_HEIGHT / 90 );

        this.tiles = new Tile[ Application.SQUARE_COUNT_HEIGHT ][Application.SQUARE_COUNT_WIDTH ];

        for ( int i = 0; i < Application.SQUARE_COUNT_HEIGHT; i++ ) {
            for ( int j = 0; j < Application.SQUARE_COUNT_WIDTH; j++ ) {
                this.tiles [i][j] = new Tile(
                    j * tileSquareLength + tileXOffset,
                    i * tileSquareLength + tileYOffset,
                    tileSquareLength
                );
            }
        }
    }

    public Board ( Application parentApplication ) {
        this.parentApplication = parentApplication;

        this.buildBoard();
    }

    public void draw ( Graphics graphics ) {
        for ( Tile[] tileRow : this.tiles )
            for ( Tile tile : tileRow )
                tile.draw ( graphics );
    }
}
