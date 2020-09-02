package tetris.gameObject;

import tetris.board.Board;

public class ShapeS extends TetrisShape {
    @Override
    public String toString() {
        return "Shape S";
    }

    @Override
    public void spawn(Board gameBoard) throws ShapeNoSpaceException {

    }

    @Override
    public void update() throws ShapeCollideException {

    }
}
