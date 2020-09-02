package tetris.gameObject;

import tetris.board.Board;

public class ShapeT extends TetrisShape {
    @Override
    public String toString() {
        return "Shape T";
    }

    @Override
    public void spawn(Board gameBoard) throws ShapeNoSpaceException {

    }

    @Override
    public void update() throws ShapeCollideException {

    }
}
