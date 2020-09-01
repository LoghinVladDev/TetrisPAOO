package tetris.board;

import java.awt.*;

public class Tile {
    public enum TileType {
        EMPTY,
        TILE_SHAPE_O,
        TILE_SHAPE_I,
        TILE_SHAPE_S,
        TILE_SHAPE_Z,
        TILE_SHAPE_L,
        TILE_SHAPE_J,
        TILE_SHAPE_T
    }

    public static final Color TILE_BORDER_COLOR  = Color.BLACK;
    public static final Color TILE_EMPTY_COLOR   = Color.DARK_GRAY;
    public static final Color TILE_SHAPE_O_COLOR = Color.YELLOW;
    public static final Color TILE_SHAPE_I_COLOR = Color.CYAN;
    public static final Color TILE_SHAPE_S_COLOR = Color.RED;
    public static final Color TILE_SHAPE_Z_COLOR = Color.GREEN;
    public static final Color TILE_SHAPE_L_COLOR = Color.ORANGE;
    public static final Color TILE_SHAPE_J_COLOR = Color.PINK;
    public static final Color TILE_SHAPE_T_COLOR = Color.MAGENTA;

    public static Color getTileTypeColor ( TileType type ) {
        switch ( type ) {
            case TILE_SHAPE_I: return TILE_SHAPE_I_COLOR;
            case TILE_SHAPE_J: return TILE_SHAPE_J_COLOR;
            case TILE_SHAPE_L: return TILE_SHAPE_L_COLOR;
            case TILE_SHAPE_O: return TILE_SHAPE_O_COLOR;
            case TILE_SHAPE_S: return TILE_SHAPE_S_COLOR;
            case TILE_SHAPE_T: return TILE_SHAPE_T_COLOR;
            case TILE_SHAPE_Z: return TILE_SHAPE_Z_COLOR;
            default:           return TILE_EMPTY_COLOR;
        }
    }

    private int squareLength = 0;
    private int x;
    private int y;

    private TileType tileType = TileType.EMPTY;

    public Tile ( int x, int y, int squareLength ) {
        this.x = x;
        this.y = y;
        this.squareLength = squareLength;
    }

    public void setTileType(TileType tileType) {
        this.tileType = tileType;
    }

    public void draw ( Graphics graphics ) {
        graphics.setColor( Tile.getTileTypeColor( this.tileType ) );
        graphics.fillRect(
            this.x,
            this.y,
            this.squareLength,
            this.squareLength
        );

        graphics.setColor( Tile.TILE_BORDER_COLOR );
        graphics.drawRect(
                this.x,
                this.y,
                this.squareLength,
                this.squareLength
        );
    }
}
