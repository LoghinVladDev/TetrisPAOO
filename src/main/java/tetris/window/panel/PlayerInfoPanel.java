package tetris.window.panel;

import tetris.window.Window;

import javax.swing.*;
import java.awt.*;

public class PlayerInfoPanel extends JPanel {

    public static final int MINIMUM_PANEL_WIDTH     = 200;
    public static final int MINIMUM_PANEL_HEIGHT    = 0;

    private Window parentWindow = null;

    private int score = 0;
    private int level = 1;

    private void configurePanel () {
        super.setMinimumSize( new Dimension( PlayerInfoPanel.MINIMUM_PANEL_WIDTH, PlayerInfoPanel.MINIMUM_PANEL_HEIGHT ) );
        super.setPreferredSize( this.getMinimumSize() );
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void draw(Graphics g) {
        g.setFont( new Font("SansSerif", Font.BOLD, 15) );
        g.drawString("Score : " + this.score, 30, 100);
        g.drawString("Level : " + this.level, 30, 130);
    }

    public PlayerInfoPanel(Window parentWindow ) {
        super();
        this.parentWindow = parentWindow;

        this.configurePanel();
    }
}
