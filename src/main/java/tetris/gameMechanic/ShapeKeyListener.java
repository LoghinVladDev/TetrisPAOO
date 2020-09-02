package tetris.gameMechanic;

import javafx.scene.input.KeyCode;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ShapeKeyListener implements KeyListener {
    private boolean leftPressed = false;
    private boolean rightPressed = false;
    private boolean leftRotatePressed = false;
    private boolean rightRotatePressed = false;
    private boolean fastDescendPressed = false;

    private int leftKeyCode = KeyEvent.VK_A;
    private int rightKeyCode = KeyEvent.VK_D;
    private int leftRotateKeyCode = KeyEvent.VK_Q;
    private int rightRotateKeyCode = KeyEvent.VK_E;
    private int fastDescendKeyCode = KeyEvent.VK_S;

    public void setLeftKeyCode(int leftKeyCode) {
        this.leftKeyCode = leftKeyCode;
    }

    public void setRightKeyCode(int rightKeyCode) {
        this.rightKeyCode = rightKeyCode;
    }

    public void setLeftRotateKeyCode(int leftRotateKeyCode) {
        this.leftRotateKeyCode = leftRotateKeyCode;
    }

    public void setRightRotateKeyCode(int rightRotateKeyCode) {
        this.rightRotateKeyCode = rightRotateKeyCode;
    }

    public void setFastDescendKeyCode(int fastDescendKeyCode) {
        this.fastDescendKeyCode = fastDescendKeyCode;
    }

    public boolean isLeftPressed() {
        return leftPressed;
    }

    public boolean isLeftRotatePressed() {
        return leftRotatePressed;
    }

    public boolean isRightPressed() {
        return rightPressed;
    }

    public boolean isRightRotatePressed() {
        return rightRotatePressed;
    }

    public boolean isFastDescendPressed() {
        return fastDescendPressed;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if ( e.getKeyCode() == this.leftKeyCode )
            this.leftPressed = true;
        if ( e.getKeyCode() == this.rightKeyCode )
            this.rightPressed = true;
        if ( e.getKeyCode() == this.leftRotateKeyCode )
            this.leftRotatePressed = true;
        if ( e.getKeyCode() == this.rightRotateKeyCode )
            this.rightRotatePressed = true;
        if ( e.getKeyCode() == this.fastDescendKeyCode )
            this.fastDescendPressed = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if ( e.getKeyCode() == this.leftKeyCode )
            this.leftPressed = false;
        if ( e.getKeyCode() == this.rightKeyCode )
            this.rightPressed = false;
        if ( e.getKeyCode() == this.leftRotateKeyCode )
            this.leftRotatePressed = false;
        if ( e.getKeyCode() == this.rightRotateKeyCode )
            this.rightRotatePressed = false;
        if ( e.getKeyCode() == this.fastDescendKeyCode )
            this.fastDescendPressed = false;
    }
}
