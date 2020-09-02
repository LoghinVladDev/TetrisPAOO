package tetris.gameObject;

import tetris.board.Tile;

public class ShapeCollideException extends Exception {
    @Override
    public String toString() {
        return "Shape Collided With GameBoard / Other Shape";
    }
}
