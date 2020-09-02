package tetris.gameObject;

import tetris.board.Board;

public class ShapeO extends TetrisShape {
    @Override
    public String toString() {
        return "Shape O";
    }

    @Override
    public void spawn(Board gameBoard) throws ShapeNoSpaceException {

    }

    @Override
    public void update() throws ShapeCollideException {

    }
}
