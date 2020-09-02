package tetris.gameObject;

import tetris.board.Board;
import tetris.board.Tile;
import tetris.gameMechanic.ShapeKeyListener;

public class ShapeL extends TetrisShape {
    private static final int SHAPE_TILE_COUNT = 4;
    private static final int ROTATE_COUNT = 4;

    private static final int[][] xShapeOffsets = { { 1, 0, 0, 0}, {-1,-1, 0, 1}, {-1, 0, 0, 0}, {-1, 0, 1, 1} };
    private static final int[][] yShapeOffsets = { { 1, 1, 0,-1}, { 1, 0, 0, 0}, {-1,-1, 0, 1}, { 0, 0, 0,-1} };

    @Override
    public String toString() {
        return "Shape L";
    }

    @Override
    public int getShapeTileCount() {
        return ShapeL.SHAPE_TILE_COUNT;
    }

    @Override
    public Tile.TileType getShapeTileType() {
        return Tile.TileType.TILE_SHAPE_L;
    }

    @Override
    public int getRotationCount() {
        return ShapeL.ROTATE_COUNT;
    }

    @Override
    public int[] getXShapeOffsetsForRotateIndex(int rotateIndex) {
        return ShapeL.xShapeOffsets[ super.rotateIndex % ShapeL.ROTATE_COUNT ];
    }

    @Override
    public int[] getYShapeOffsetsForRotateIndex(int rotateIndex) {
        return ShapeL.yShapeOffsets[ super.rotateIndex % ShapeL.ROTATE_COUNT ];
    }
}
