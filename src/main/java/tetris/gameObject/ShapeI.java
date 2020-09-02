package tetris.gameObject;

import tetris.board.Board;

public class ShapeI extends TetrisShape {
    @Override
    public String toString() {
        return "Shape I";
    }

    @Override
    public void spawn(Board gameBoard) throws ShapeNoSpaceException {

    }

    @Override
    public void update() throws ShapeCollideException {

    }
}
