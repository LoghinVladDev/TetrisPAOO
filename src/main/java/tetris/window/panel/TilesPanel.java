package tetris.window.panel;


import tetris.window.Window;

import javax.swing.*;
import java.awt.*;

public class TilesPanel extends JPanel {

    public static final int MINIMUM_PANEL_WIDTH     = 200;
    public static final int MINIMUM_PANEL_HEIGHT    = 0;

    private Window parentWindow = null;

    private void configurePanel () {
        super.setMinimumSize( new Dimension ( TilesPanel.MINIMUM_PANEL_WIDTH, TilesPanel.MINIMUM_PANEL_HEIGHT ) );
        super.setPreferredSize( this.getMinimumSize() );

//        super.setBackground( Color.BLUE );
    }

    public TilesPanel ( Window parentWindow ) {
        super();
        this.parentWindow = parentWindow;

        this.configurePanel();
    }
}
