package tetris.gameObject;

import tetris.board.Board;

public class ShapeL extends TetrisShape {
    @Override
    public String toString() {
        return "Shape L";
    }

    @Override
    public void spawn(Board gameBoard) throws ShapeNoSpaceException {

    }

    @Override
    public void update() throws ShapeCollideException {

    }
}
