package tetris.gameMechanic;

import java.util.Objects;

public class FrameTimer {
    private int frameDuration = 1;
    private int remainingTime = 0;
    private boolean isActive = false;
    private boolean repeat = true;

    public void setRepeat(boolean repeat) {
        this.repeat = repeat;
    }

    @Override
    public int hashCode() {
        return Objects.hash(frameDuration, remainingTime, isActive);
    }

    public FrameTimer ( ) {

    }

    public FrameTimer ( int frameDuration ) {
        this.frameDuration = frameDuration;
    }

    public void start () {
        this.isActive = true;
        this.remainingTime = this.frameDuration;
    }

    public void start ( int frameDuration ) {
        this.frameDuration = frameDuration;
        this.start();
    }

    public boolean isActive() {
        return isActive;
    }

    public void pause () {
        this.isActive = false;
    }

    public void resume () {
        this.isActive = true;
    }

    public void update () {
        if ( this.isActive )
            if ( this.remainingTime > 0 ) {
                this.remainingTime--;
            } else
                if ( this.repeat )
                    this.remainingTime = this.frameDuration;
    }

    public boolean isDone () {
        if ( this.repeat )
            return this.isActive && ( this.remainingTime == 0 );
        else
            return this.remainingTime == 0;
    }
}
