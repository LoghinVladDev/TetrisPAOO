package tetris.gameObject;

import tetris.board.Tile;

public class ShapeS extends TetrisShape {

    private static final int SHAPE_TILE_COUNT = 4;
    private static final int ROTATE_COUNT = 2;

    private static final int[][] xShapeOffsets = { { -1, 0, 0, 1 }, { 0, 0, 1, 1 } };
    private static final int[][] yShapeOffsets = { {  1, 1, 0, 0 }, {-1, 0, 0, 1 } };

    @Override
    public String toString() {
        return "Shape S";
    }

    @Override
    public int getShapeTileCount() {
        return ShapeS.SHAPE_TILE_COUNT;
    }

    @Override
    public Tile.TileType getShapeTileType() {
        return Tile.TileType.TILE_SHAPE_S;
    }

    @Override
    public int getRotationCount() {
        return ShapeS.ROTATE_COUNT;
    }

    @Override
    public int[] getXShapeOffsetsForRotateIndex(int rotateIndex) {
        return ShapeS.xShapeOffsets[ super.rotateIndex % ShapeS.ROTATE_COUNT ];
    }

    @Override
    public int[] getYShapeOffsetsForRotateIndex(int rotateIndex) {
        return ShapeS.yShapeOffsets[ super.rotateIndex % ShapeS.ROTATE_COUNT ];
    }
}
