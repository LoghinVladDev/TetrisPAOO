package tetris.board;

import tetris.application.Application;
import tetris.exceptions.BoardOutOfBoundsException;
import tetris.gameObject.TetrisShape;

import java.awt.*;

public class Board {
    private Tile[][] tiles;
    private Application parentApplication;

    private static int tileSquareLength;

    public static int getTileSquareLength () {
        return Board.tileSquareLength;
    }

    private void buildBoard() {
        Rectangle renderArea = this.parentApplication.getGameWindow().getGameRenderPanel().getRenderSize();
        int tileWidthMax = renderArea.width / Application.SQUARE_COUNT_WIDTH;
        int tileHeightMax = renderArea.height / Application.SQUARE_COUNT_HEIGHT;

        Board.tileSquareLength = Math.min( tileWidthMax, tileHeightMax );
        renderArea = this.parentApplication.getGameWindow().getGameRenderPanel().getRenderSize();

        int tileXOffset = renderArea.x + ( renderArea.width - tileSquareLength * Application.SQUARE_COUNT_WIDTH ) / 2;
        int tileYOffset = renderArea.y + ( 5 );

        this.tiles = new Tile[ Application.SQUARE_COUNT_HEIGHT ][Application.SQUARE_COUNT_WIDTH ];

        for ( int i = 0; i < Application.SQUARE_COUNT_HEIGHT; i++ ) {
            for ( int j = 0; j < Application.SQUARE_COUNT_WIDTH; j++ ) {
                this.tiles [i][j] = new Tile(
                    j * Board.tileSquareLength + tileXOffset,
                    i * Board.tileSquareLength + tileYOffset,
                    Board.tileSquareLength
                );

                this.tiles [i][j] .setBoardPos( j, i );
            }
        }
    }

    public Board ( Application parentApplication ) {
        this.parentApplication = parentApplication;

        this.buildBoard();
    }

    public int getWidth () {
        if ( this.tiles == null )
            return 0;
        return this.tiles[0].length;
    }

    public int getHeight () {
        if ( this.tiles == null )
            return 0;
        return this.tiles.length;
    }

    public boolean isLineFull ( int lineIndex ) {
        if ( this.tiles == null )
            return false;

        for ( int j = 0; j < this.tiles[ lineIndex ].length; j++ ) {
            if (this.tiles[lineIndex][j].getTileType().equals(Tile.TileType.EMPTY))
                return false;
        }

        return true;
    }

    public void removeLine ( int lineIndex, TetrisShape activeShape ) {
        if ( this.tiles == null )
            return;

        if ( lineIndex < 0 || lineIndex >= this.tiles.length )
            return;

        for ( int i = lineIndex; i >= 1; i-- ) {
            for ( int j = 0; j < this.tiles[ i ].length; j++ ) {
                if ( ! activeShape.contains( this.tiles[ i ][ j ] ) && ! activeShape.contains( this.tiles[ i - 1 ][ j ] ) )
                    this.tiles[ i ][ j ].setTileType(this.tiles[ i - 1 ][ j ].getTileType());
            }
        }

        for ( int j = 0; j < this.tiles[ 0 ].length; j++ )
            if ( ! activeShape.contains( this.tiles[ 0 ][ j ] ) )
                this.tiles[ 0 ][ j ].setTileType( Tile.TileType.EMPTY );
    }

    public int getFullLineIndex() {
        if( this.tiles == null )
            return -1;

        for ( int i = this.tiles.length - 1 ; i >= 0; i-- ) {
            if ( this.isLineFull ( i ) )
                return i;
        }

        return -1;
    }

    public Tile getTile (int x, int y) throws BoardOutOfBoundsException {
        if ( this.tiles == null )
            return null;

        if ( x < 0 || x >= this.getWidth() || y < 0 || y >= this.getHeight() )
            throw new BoardOutOfBoundsException ( x, y );

        return this.tiles[y][x];
    }

    public void draw ( Graphics graphics ) {
        for ( Tile[] tileRow : this.tiles )
            for ( Tile tile : tileRow )
                tile.draw ( graphics );
    }
}
