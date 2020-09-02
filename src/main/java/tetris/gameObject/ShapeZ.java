package tetris.gameObject;

import tetris.board.Board;
import tetris.board.BoardOutOfBoundsException;
import tetris.board.Tile;

import java.util.Arrays;

public class ShapeZ extends TetrisShape {

    private static final int SHAPE_TILE_COUNT = 4;
    private static final int ROTATE_COUNT = 2;

    private static final int[][] xShapeOffsets = { {-1,  0,  0,  1}, { 0,  0,  1,  1} };
    private static final int[][] yShapeOffsets = { { 0,  0,  1,  1}, { 1,  0,  0, -1} };

    public ShapeZ () {
        super();
    }

    @Override
    public String toString() {
        return "Shape Z";
    }

    @Override
    public int[] getXShapeOffsetsForRotateIndex(int rotateIndex) {
        return ShapeZ.xShapeOffsets[ super.rotateIndex % ShapeZ.ROTATE_COUNT ];
    }

    @Override
    public int[] getYShapeOffsetsForRotateIndex(int rotateIndex) {
        return ShapeZ.yShapeOffsets[ super.rotateIndex % ShapeZ.ROTATE_COUNT ];
    }

    @Override
    public int getShapeTileCount() {
        return ShapeZ.SHAPE_TILE_COUNT;
    }

    @Override
    public Tile.TileType getShapeTileType() {
        return Tile.TileType.TILE_SHAPE_Z;
    }

    @Override
    public int getRotationCount() {
        return ShapeZ.ROTATE_COUNT;
    }

}
