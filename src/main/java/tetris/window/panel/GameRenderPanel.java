package tetris.window.panel;

import tetris.application.Application;
import tetris.board.Board;
import tetris.window.Window;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

public class GameRenderPanel extends JPanel {

    public static final int MINIMUM_PANEL_WIDTH = 450;
    public static final int MINIMUM_PANEL_HEIGHT = 600;

    private static final int RENDER_OFFSET_CORRECTION_LEFT      = 5;
    private static final int RENDER_OFFSET_CORRECTION_RIGHT     = 11;
    private static final int RENDER_OFFSET_CORRECTION_UP        = 37;
    private static final int RENDER_OFFSET_CORRECTION_DOWN      = 12;

    private Window          parentWindow                = null;

    public Rectangle getRenderSize () {
        return new Rectangle(
            ( this.parentWindow.getPlayerInfoPanel().getWidth()) ,
            RENDER_OFFSET_CORRECTION_UP,
            this.getWidth() - RENDER_OFFSET_CORRECTION_RIGHT,
            this.getHeight() - RENDER_OFFSET_CORRECTION_DOWN
        );
    }

    private void configurePanel () {
        super.setMinimumSize( new Dimension( GameRenderPanel.MINIMUM_PANEL_WIDTH, GameRenderPanel.MINIMUM_PANEL_HEIGHT ) );
    }

    public GameRenderPanel( Window parentWindow ) {
        super();
        this.parentWindow = parentWindow;

        this.configurePanel();
    }

}
