package tetris.gameObject;

import tetris.board.Board;
import tetris.board.Tile;
import tetris.gameMechanic.ShapeKeyListener;

public class ShapeI extends TetrisShape {

    private static final int SHAPE_TILE_COUNT = 4;
    private static final int ROTATE_COUNT = 2;

    private static final int[][] xShapeOffsets = { {  0, 0, 0, 0 }, {-1, 0, 1, 2 } };
    private static final int[][] yShapeOffsets = { { -1, 0, 1, 2 }, { 0, 0, 0, 0 } };

    @Override
    public String toString() {
        return "Shape I";
    }

    @Override
    public int getShapeTileCount() {
        return ShapeI.SHAPE_TILE_COUNT;
    }

    @Override
    public Tile.TileType getShapeTileType() {
        return Tile.TileType.TILE_SHAPE_I;
    }

    @Override
    public int getRotationCount() {
        return ShapeI.ROTATE_COUNT;
    }

    @Override
    public int[] getXShapeOffsetsForRotateIndex(int rotateIndex) {
        return ShapeI.xShapeOffsets[ super.rotateIndex % ShapeI.ROTATE_COUNT ];
    }

    @Override
    public int[] getYShapeOffsetsForRotateIndex(int rotateIndex) {
        return ShapeI.yShapeOffsets[ super.rotateIndex % ShapeI.ROTATE_COUNT ];
    }
}
