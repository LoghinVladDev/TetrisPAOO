package tetris.window.panel;

import tetris.window.Window;

import javax.swing.*;
import java.awt.*;

public class PlayerInfoPanel extends JPanel {

    public static final int MINIMUM_PANEL_WIDTH     = 200;
    public static final int MINIMUM_PANEL_HEIGHT    = 0;

    private Window parentWindow = null;

    private void configurePanel () {
        super.setMinimumSize( new Dimension( PlayerInfoPanel.MINIMUM_PANEL_WIDTH, PlayerInfoPanel.MINIMUM_PANEL_HEIGHT ) );
        super.setPreferredSize( this.getMinimumSize() );

//        super.setBackground( Color.RED );
    }

    public PlayerInfoPanel( Window parentWindow ) {
        super();
        this.parentWindow = parentWindow;

        this.configurePanel();
    }
}
