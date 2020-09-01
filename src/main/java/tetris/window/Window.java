package tetris.window;

import tetris.application.Application;
import tetris.window.panel.GameRenderPanel;
import tetris.window.panel.PlayerInfoPanel;
import tetris.window.panel.TilesPanel;

import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {

    public static final String  DEFAULT_WINDOW_TITLE    = "Tetris";
    public static final int     DEFAULT_WINDOW_WIDTH    = 1000;
    public static final int     DEFAULT_WINDOW_HEIGHT   = 900;

    private Application         parentApplication       = null;

    private GameRenderPanel     gameRenderPanel         = null;
    private PlayerInfoPanel     playerInfoPanel         = null;
    private TilesPanel          tilesPanel              = null;

    private void buildWindowPanels() {
        this.gameRenderPanel    = new GameRenderPanel( this );
        this.playerInfoPanel    = new PlayerInfoPanel( this );
        this.tilesPanel         = new TilesPanel     ( this );
    }

    private void configureWindowSettings () {
        super.setSize( new Dimension( Window.DEFAULT_WINDOW_WIDTH, Window.DEFAULT_WINDOW_HEIGHT ));

        this.buildWindowPanels();

        super.add( this.gameRenderPanel,    BorderLayout.CENTER );
        super.add( this.playerInfoPanel,    BorderLayout.EAST );
        super.add( this.tilesPanel,         BorderLayout.WEST );

        super.setMinimumSize(
            new Dimension(
                (
                    this.gameRenderPanel.getMinimumSize().width +
                    this.playerInfoPanel.getMinimumSize().width +
                    this.tilesPanel.getMinimumSize().width
                ),
                this.gameRenderPanel.getMinimumSize().height
            )
        );

        super.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
    }

    public Window( Application parentApplication ) {
        super( Window.DEFAULT_WINDOW_TITLE );
        this.parentApplication = parentApplication;

        this.configureWindowSettings();
    }

    public Window( Application parentApplication, String title ) {
        super( title );
        this.parentApplication = parentApplication;

        this.configureWindowSettings();
    }

    public GameRenderPanel getGameRenderPanel() {
        return gameRenderPanel;
    }
}
