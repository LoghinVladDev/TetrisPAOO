package tetris.gameObject;

import tetris.board.Board;
import tetris.board.BoardOutOfBoundsException;
import tetris.board.Tile;

public class ShapeZ extends TetrisShape {

    public static final int SHAPE_TILE_COUNT = 4;

    private Board gameBoard = null;

    public ShapeZ () {
        this.shapeTiles = new Tile[ SHAPE_TILE_COUNT ];
    }

    public static final int[] xShapeOffsets = { -1, 0, 0, 1 };
    public static final int[] yShapeOffsets = { 0, 0, 1, 1 };

    @Override
    public String toString() {
        return "Shape Z";
    }

    @Override
    public void spawn(Board gameBoard) throws ShapeNoSpaceException {
        this.gameBoard = gameBoard;

        int xPos = this.gameBoard.getWidth() / 2 + (int) ( ( Math.random() * 3 ) - 1 ); // [ -1, 1 ]
        int yPos = 0;

        for ( int shapeTileIndex = 0; shapeTileIndex < ShapeZ.SHAPE_TILE_COUNT; shapeTileIndex++ ) {

            try {
                Tile currentTile = this.gameBoard.getTile(
                        xPos + ShapeZ.xShapeOffsets[shapeTileIndex],
                        yPos + ShapeZ.yShapeOffsets[shapeTileIndex]
                );


                if (!currentTile.getTileType().equals(Tile.TileType.EMPTY))
                    throw new ShapeNoSpaceException(xPos + ShapeZ.xShapeOffsets[shapeTileIndex], yPos + ShapeZ.yShapeOffsets[shapeTileIndex], currentTile);

                this.shapeTiles[shapeTileIndex] = currentTile;
            } catch ( BoardOutOfBoundsException exception ) {
                System.out.println("Caught unexpected BoardOutOfBoundsException in Shape.spawn : " + exception.toString());
            }
        }

        for ( Tile tile : this.shapeTiles ) {
           tile.setTileType( Tile.TileType.TILE_SHAPE_Z );
        }
    }

    public boolean contains ( Tile tile ) {
        for ( Tile currentTile : this.shapeTiles )
            if ( currentTile.equals(tile) )
                return true;
        return false;
    }

    @Override
    public void update() throws ShapeCollideException {
        System.out.println("update");

        Tile[] newTileArray = new Tile[ ShapeZ.SHAPE_TILE_COUNT ];

        try {
            int tileIndex = 0;
            for (Tile currentTile : this.shapeTiles) {
                Tile nextTile = this.gameBoard.getTile(currentTile.getBoardX(), currentTile.getBoardY() + 1);
                newTileArray[ tileIndex++ ] = nextTile;

                if ( ! nextTile.getTileType().equals(Tile.TileType.EMPTY) && ! this.contains ( nextTile ) )
                    throw new ShapeCollideException ();
            }
        } catch ( BoardOutOfBoundsException exception ) {
            throw new ShapeCollideException ();
        }

        for ( Tile oldTile : this.shapeTiles ) {
            oldTile.setTileType( Tile.TileType.EMPTY );
        }

        this.shapeTiles = newTileArray;

        for ( Tile currentTile : this.shapeTiles ) {
            currentTile.setTileType( Tile.TileType.TILE_SHAPE_Z );
        }
    }
}
