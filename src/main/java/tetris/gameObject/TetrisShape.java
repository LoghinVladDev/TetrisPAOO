package tetris.gameObject;

import tetris.board.Board;
import tetris.board.Tile;

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
    protected int rotationStage;

    protected TetrisShape() {

    }

    public abstract String toString();
    public abstract void spawn( Board gameBoard ) throws ShapeNoSpaceException;
    public abstract void update() throws ShapeCollideException;

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
