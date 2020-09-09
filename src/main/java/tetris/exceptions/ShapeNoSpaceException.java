package tetris.exceptions;

import tetris.board.Tile;

public class ShapeNoSpaceException extends Exception {

    private final int xPosOccupied;
    private final int yPosOccupied;
    private Tile occupiedTile;

    public ShapeNoSpaceException ( int xPos, int yPos ) {
        this.xPosOccupied = xPos;
        this.yPosOccupied = yPos;
    }

    public ShapeNoSpaceException (int xPos, int yPos, Tile occupiedTile) {
        this.xPosOccupied = xPos;
        this.yPosOccupied = yPos;
        this.occupiedTile = occupiedTile;
    }

    @Override
    public String toString() {
        return "Shape has no space to spawn : occupied : { xPos = " + this.xPosOccupied + ", yPos = " + this.yPosOccupied +
                ( this.occupiedTile == null ? "" : ( ", tile type : " + this.occupiedTile.getTileType() ) )
                + "}" ;
    }
}
