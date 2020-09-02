package tetris.gameObject;

import tetris.board.Board;
import tetris.board.Tile;
import tetris.gameMechanic.ShapeKeyListener;

public class ShapeJ extends TetrisShape {

    private static final int SHAPE_TILE_COUNT = 4;
    private static final int ROTATE_COUNT = 4;

    private static final int[][] xShapeOffsets = { {-1, 0, 0, 0}, {-1,-1, 0, 1}, { 1, 0, 0, 0}, {-1, 0, 1, 1} };
    private static final int[][] yShapeOffsets = { { 1, 1, 0,-1}, {-1, 0, 0, 0}, {-1,-1, 0, 1}, { 0, 0, 0, 1} };

    @Override
    public String toString() {
        return "Shape J";
    }

    @Override
    public int getShapeTileCount() {
        return ShapeJ.SHAPE_TILE_COUNT;
    }

    @Override
    public Tile.TileType getShapeTileType() {
        return Tile.TileType.TILE_SHAPE_J;
    }

    @Override
    public int getRotationCount() {
        return ShapeJ.ROTATE_COUNT;
    }

    @Override
    public int[] getXShapeOffsetsForRotateIndex(int rotateIndex) {
        return ShapeJ.xShapeOffsets[ super.rotateIndex % ShapeJ.ROTATE_COUNT ];
    }

    @Override
    public int[] getYShapeOffsetsForRotateIndex(int rotateIndex) {
        return ShapeJ.yShapeOffsets[ super.rotateIndex % ShapeJ.ROTATE_COUNT ];
    }
}
