package tetris.window.panel;

import tetris.window.Window;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

public class GameRenderPanel extends JPanel {

    public static final int MINIMUM_PANEL_WIDTH = 450;
    public static final int MINIMUM_PANEL_HEIGHT = 600;

    private static final int RENDER_OFFSET_CORRECTION_LEFT      = 5;
    private static final int RENDER_OFFSET_CORRECTION_RIGHT     = 11;
    private static final int RENDER_OFFSET_CORRECTION_UP        = 0;
    private static final int RENDER_OFFSET_CORRECTION_DOWN      = 12;

    private Window          parentWindow                = null;
    private Canvas          renderCanvas                = null;
    private Graphics        currentGraphics             = null;
    private BufferStrategy  currentBufferStrategy       = null;



    private void buildCanvas () {
        this.renderCanvas = new Canvas();
        this.renderCanvas.setFocusable(false);
        this.add( this.renderCanvas );
        this.renderCanvas.setMinimumSize( super.getSize() );
        this.renderCanvas.setPreferredSize( super.getSize() );
//        this.renderCanvas.setMaximumSize( super.getMaximumSize() );

    }

    public Rectangle getRenderSize () {
        return new Rectangle(
            RENDER_OFFSET_CORRECTION_LEFT,
            RENDER_OFFSET_CORRECTION_UP,
            this.getWidth() - RENDER_OFFSET_CORRECTION_RIGHT,
            this.getHeight() - RENDER_OFFSET_CORRECTION_DOWN
        );
    }

    private void configurePanel () {
        super.setMinimumSize( new Dimension( GameRenderPanel.MINIMUM_PANEL_WIDTH, GameRenderPanel.MINIMUM_PANEL_HEIGHT ) );

        super.setBackground( Color.GREEN );
    }

    public GameRenderPanel( Window parentWindow ) {
        super();
        this.parentWindow = parentWindow;

        this.configurePanel();
    }

    public Graphics getDrawGraphics () {
        if ( this.renderCanvas == null ) {
            this.buildCanvas();
        }

        if ( this.currentGraphics == null ) {
            if ( this.currentBufferStrategy == null ) {
                this.currentBufferStrategy = this.renderCanvas.getBufferStrategy();

                if ( this.currentBufferStrategy == null ) {
                    this.renderCanvas.createBufferStrategy( 3 );
                    this.currentBufferStrategy = this.renderCanvas.getBufferStrategy();
                }
            }

            this.currentGraphics = this.currentBufferStrategy.getDrawGraphics();
        }

        return this.currentGraphics;
    }

    public void draw () {
        if ( this.currentBufferStrategy == null )
            return;

        this.currentBufferStrategy.show();
        this.currentGraphics.dispose();

        this.currentGraphics = null;
        this.currentBufferStrategy = null;
    }
}
