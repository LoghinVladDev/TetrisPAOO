package tetris.gameObject;

import tetris.board.Board;
import tetris.board.Tile;
import tetris.gameMechanic.ShapeKeyListener;

public class ShapeO extends TetrisShape {

    private static final int SHAPE_TILE_COUNT = 4;
    private static final int ROTATE_COUNT = 1;

    private static final int[][] xShapeOffsets = { { 0, 0, 1, 1 } };
    private static final int[][] yShapeOffsets = { { 0, 1, 0, 1 } };

    @Override
    public String toString() {
        return "Shape O";
    }

    @Override
    public int getShapeTileCount() {
        return ShapeO.SHAPE_TILE_COUNT;
    }

    @Override
    public Tile.TileType getShapeTileType() {
        return Tile.TileType.TILE_SHAPE_O;
    }

    @Override
    public int getRotationCount() {
        return ShapeO.ROTATE_COUNT;
    }

    @Override
    public int[] getXShapeOffsetsForRotateIndex(int rotateIndex) {
        return ShapeO.xShapeOffsets[ super.rotateIndex % ShapeO.ROTATE_COUNT ];
    }

    @Override
    public int[] getYShapeOffsetsForRotateIndex(int rotateIndex) {
        return ShapeO.yShapeOffsets[ super.rotateIndex % ShapeO.ROTATE_COUNT ];
    }
}
