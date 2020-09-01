package tetris.gameMechanic;

public class FrameTimer {
    private int frameDuration = 1;
    private int remainingTime;
    private boolean isActive = false;

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

    public void update () {
        if ( this.isActive )
            if ( this.remainingTime > 0 ) {
                this.remainingTime--;
            } else
                this.remainingTime = this.frameDuration;
    }

    public boolean isDone () {
        return this.isActive && ( this.remainingTime == 0 );
    }
}
