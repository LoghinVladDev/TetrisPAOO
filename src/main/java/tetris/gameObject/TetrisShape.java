package tetris.gameObject;

import tetris.board.Board;
import tetris.board.BoardOutOfBoundsException;
import tetris.board.Tile;
import tetris.gameMechanic.FrameTimer;
import tetris.gameMechanic.ShapeKeyListener;

public abstract class TetrisShape {

    public static final int SHAPE_COUNT = 7;

    public static final int SHAPE_I_INDEX = 0;
    public static final int SHAPE_J_INDEX = 1;
    public static final int SHAPE_L_INDEX = 2;
    public static final int SHAPE_O_INDEX = 3;
    public static final int SHAPE_S_INDEX = 4;
    public static final int SHAPE_T_INDEX = 5;
    public static final int SHAPE_Z_INDEX = 6;

    protected Tile[] shapeTiles;
    protected int xPos;
    protected int yPos;
    protected int rotateIndex;

    protected Board gameBoard = null;

    private static final FrameTimer rotateCoolDown = new FrameTimer( 3 );

    public static FrameTimer getRotateCoolDown() {
        return rotateCoolDown;
    }

    protected TetrisShape() {
        this.shapeTiles = new Tile [ this.getShapeTileCount() ];
    }

    public boolean contains ( Tile tile ) {
        for ( Tile currentTile : this.shapeTiles )
            if ( currentTile.equals(tile) )
                return true;
        return false;
    }

    public void setGameBoard(Board gameBoard) {
        this.gameBoard = gameBoard;
    }

    public abstract String toString();
    public abstract int getShapeTileCount ();
    public abstract Tile.TileType getShapeTileType ();
    public abstract int getRotationCount ();
    public abstract int[] getXShapeOffsetsForRotateIndex (int rotateIndex);
    public abstract int[] getYShapeOffsetsForRotateIndex (int rotateIndex);

    public void applyRotation() throws RotateFailedException {
        Tile[] newTileArray = new Tile[ this.getShapeTileCount() ];

        for ( int shapeTileIndex = 0; shapeTileIndex < this.getShapeTileCount(); shapeTileIndex++ ) {
            try {
                Tile currentTile = this.gameBoard.getTile(
                    this.xPos + this.getXShapeOffsetsForRotateIndex( this.rotateIndex ) [ shapeTileIndex ],
                    this.yPos + this.getYShapeOffsetsForRotateIndex( this.rotateIndex ) [ shapeTileIndex ]
                );
                newTileArray[ shapeTileIndex ] = currentTile;

                if ( ! currentTile.getTileType().equals( Tile.TileType.EMPTY ) && ! this.contains( currentTile ) )
                    return;
            } catch ( BoardOutOfBoundsException ignored ) {
                return;
            }
        }

        for ( Tile tile : this.shapeTiles )
            tile.setTileType( Tile.TileType.EMPTY );

        this.shapeTiles = newTileArray;

        for ( Tile tile : this.shapeTiles )
            tile.setTileType( this.getShapeTileType() );
    }

    public void spawn( Board gameBoard ) throws ShapeNoSpaceException {
        this.setGameBoard( gameBoard );

        this.xPos = this.gameBoard.getWidth() / 2 + (int) ( ( Math.random() * 3 ) - 1 ); // [ -1, 1 ]
        this.yPos = 0;

        if (
            this.getShapeTileType().equals( Tile.TileType.TILE_SHAPE_I ) ||
            this.getShapeTileType().equals( Tile.TileType.TILE_SHAPE_J ) ||
            this.getShapeTileType().equals( Tile.TileType.TILE_SHAPE_L )
        )
            this.yPos = 1;

        for ( int shapeTileIndex = 0; shapeTileIndex < this.getShapeTileCount(); shapeTileIndex++ ) {

            try {
                Tile currentTile = this.gameBoard.getTile(
                    this.xPos + this.getXShapeOffsetsForRotateIndex( this.rotateIndex )[ shapeTileIndex ],
                    this.yPos + this.getYShapeOffsetsForRotateIndex( this.rotateIndex )[ shapeTileIndex ]
                );

                if (!currentTile.getTileType().equals(Tile.TileType.EMPTY))
                    throw new ShapeNoSpaceException(
                        this.xPos + this.getXShapeOffsetsForRotateIndex( this.rotateIndex )[ shapeTileIndex ],
                        this.yPos + this.getYShapeOffsetsForRotateIndex( this.rotateIndex )[ shapeTileIndex ],
                        currentTile
                    );

                this.shapeTiles[shapeTileIndex] = currentTile;
            } catch ( BoardOutOfBoundsException exception ) {
                System.out.println("Caught unexpected BoardOutOfBoundsException in Shape.spawn : " + exception.toString());
            }
        }

        for ( Tile tile : this.shapeTiles ) {
            tile.setTileType( this.getShapeTileType() );
        }
    }

    public void rotateLeft() {
        int originalRotateIndex = this.rotateIndex;

        this.rotateIndex = ( this.rotateIndex - 1 ) % this.getRotationCount();
        if ( this.rotateIndex < 0 )
            this.rotateIndex += this.getRotationCount();

        try {
            this.applyRotation();
        } catch ( RotateFailedException ignored ) {
            System.out.println( "e" );
            this.rotateIndex = originalRotateIndex;
        }
    }

    public void rotateRight() {
        int originalRotateIndex = this.rotateIndex;

        this.rotateIndex = ( this.rotateIndex + 1 ) % this.getRotationCount();

        try {
            this.applyRotation();
        } catch ( RotateFailedException ignored ) {
            this.rotateIndex = originalRotateIndex;
        }
    }

    public void move ( ShapeKeyListener keyListener ) {
        if ( keyListener.isLeftPressed() )
            this.moveLeft();
        if ( keyListener.isRightPressed() )
            this.moveRight();
    }

    public void rotate ( ShapeKeyListener keyListener ) {
        if ( keyListener.isLeftRotatePressed() )
            this.rotateLeft();
        if ( keyListener.isRightRotatePressed() )
            this.rotateRight();
    }

    public void descend ( ShapeKeyListener keyListener ) throws ShapeCollideException {
        if ( keyListener.isFastDescendPressed() )
            this.update();
    }

    public void moveLeft() {
        this.moveHorizontal( -1 );
    }

    public void moveRight() {
        this.moveHorizontal( 1 );
    }

    public void moveHorizontal( int xOffset ) {
        Tile[] newTileArray = new Tile[ this.getShapeTileCount() ];

        try {
            int tileIndex = 0;
            for ( Tile currentTile : this.shapeTiles ) {
                Tile nextTile = this.gameBoard.getTile( currentTile.getBoardX() + xOffset, currentTile.getBoardY() );
                newTileArray[ tileIndex++ ] = nextTile;

                if ( ! nextTile.getTileType().equals( Tile.TileType.EMPTY ) && ! this.contains( nextTile ) )
                    return;
            }
        } catch ( BoardOutOfBoundsException ignored ) {
            return;
        }

        for ( Tile oldTile : this.shapeTiles )
            oldTile.setTileType( Tile.TileType.EMPTY );

        this.xPos += xOffset;
        this.shapeTiles = newTileArray;

        for( Tile currentTile : this.shapeTiles )
            currentTile.setTileType( this.getShapeTileType() );
    }

    public void update() throws ShapeCollideException {
//        System.out.println("update");

        Tile[] newTileArray = new Tile[ this.getShapeTileCount() ];

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

        for ( Tile oldTile : this.shapeTiles )
            oldTile.setTileType( Tile.TileType.EMPTY );

        this.yPos ++;
        this.shapeTiles = newTileArray;

        for ( Tile currentTile : this.shapeTiles ) {
            currentTile.setTileType( this.getShapeTileType() );
        }
    }

    public static class ShapeFactory {

        public TetrisShape createShapeI () {
            return new ShapeI();
        }

        public TetrisShape createShapeJ () {
            return new ShapeJ();
        }

        public TetrisShape createShapeL () {
            return new ShapeL();
        }

        public TetrisShape createShapeO () {
            return new ShapeO();
        }

        public TetrisShape createShapeS () {
            return new ShapeS();
        }

        public TetrisShape createShapeT () {
            return new ShapeT();
        }

        public TetrisShape createShapeZ () {
            return new ShapeZ();
        }

        public TetrisShape createRandomShape () {
            int shapeNumber = (int) ( Math.random() * TetrisShape.SHAPE_COUNT );

            switch ( shapeNumber ) {
                case TetrisShape.SHAPE_I_INDEX: return this.createShapeI();
                case TetrisShape.SHAPE_J_INDEX: return this.createShapeJ();
                case TetrisShape.SHAPE_L_INDEX: return this.createShapeL();
                case TetrisShape.SHAPE_O_INDEX: return this.createShapeO();
                case TetrisShape.SHAPE_S_INDEX: return this.createShapeS();
                case TetrisShape.SHAPE_T_INDEX: return this.createShapeT();
                case TetrisShape.SHAPE_Z_INDEX: return this.createShapeZ();
                default:                        return null;
            }
        }

    }
}
