package tetris.gameObject;

import tetris.board.Board;
import tetris.board.Tile;
import tetris.gameMechanic.ShapeKeyListener;

public class ShapeT extends TetrisShape {

    private static final int SHAPE_TILE_COUNT = 4;
    private static final int ROTATE_COUNT = 4;

    private static final int[][] xShapeOffsets = { {-1,  0,  1,  0}, { 0,  0,  0, -1}, { 1,  0, -1,  0}, { 0,  0,  0,  1} };
    private static final int[][] yShapeOffsets = { { 0,  0,  0,  1}, { 1,  0, -1,  0}, { 0,  0,  0, -1}, {-1,  0,  1,  0} };

    @Override
    public String toString() {
        return "Shape T";
    }

    @Override
    public int getShapeTileCount() {
        return ShapeT.SHAPE_TILE_COUNT;
    }

    @Override
    public Tile.TileType getShapeTileType() {
        return Tile.TileType.TILE_SHAPE_T;
    }

    @Override
    public int getRotationCount() {
        return ShapeT.ROTATE_COUNT;
    }

    @Override
    public int[] getXShapeOffsetsForRotateIndex(int rotateIndex) {
        return ShapeT.xShapeOffsets[ super.rotateIndex % ShapeT.ROTATE_COUNT ];
    }

    @Override
    public int[] getYShapeOffsetsForRotateIndex(int rotateIndex) {
        return ShapeT.yShapeOffsets[ super.rotateIndex % ShapeT.ROTATE_COUNT ];
    }
}
