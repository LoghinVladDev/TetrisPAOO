package tetris.exceptions;

public class BoardOutOfBoundsException extends Exception {
    private int xOutOfBounds;
    private int yOutOfBounds;

    public BoardOutOfBoundsException (int x, int y) {
        this.xOutOfBounds = x;
        this.yOutOfBounds = y;
    }

    @Override
    public String toString() {
        return "BoardOutOfBoundsException{" +
                "xOutOfBounds=" + xOutOfBounds +
                ", yOutOfBounds=" + yOutOfBounds +
                '}';
    }
}
