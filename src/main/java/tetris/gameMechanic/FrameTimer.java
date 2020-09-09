package tetris.gameMechanic;

import java.util.Objects;

public class FrameTimer {
    private int frameDuration = 1;
    private int remainingTime = 0;
    private boolean isActive = false;

    @Override
    public int hashCode() {
        return Objects.hash(frameDuration, remainingTime, isActive);
    }

    public FrameTimer ( int frameDuration ) {
        this.frameDuration = frameDuration;
    }

    public void start () {
        this.isActive = true;
        this.remainingTime = this.frameDuration;
    }

    public int getFrameDuration() {
        return frameDuration;
    }

    public void pause () {
        this.isActive = false;
    }

    public void update () {
        if ( this.isActive )
            if ( this.remainingTime > 0 ) {
                this.remainingTime--;
            } else
                this.remainingTime = this.frameDuration;
    }

    public void changeTimerSetOff ( int newFrameDuration ) {
        this.frameDuration = newFrameDuration;
    }

    public boolean isDone () {
        return this.isActive && ( this.remainingTime == 0 );
    }
}
