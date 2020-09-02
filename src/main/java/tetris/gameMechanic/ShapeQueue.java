package tetris.gameMechanic;

import tetris.gameObject.TetrisShape;

import java.util.LinkedList;
import java.util.Queue;

public class ShapeQueue {
    public static final int SHAPES_IN_QUEUE = 3;

    private Queue <TetrisShape>         shapeQueue;
    private TetrisShape.ShapeFactory    shapeFactory;

    private void resetQueue () {
        this.shapeQueue.clear();

        this.shapeQueue.add( this.shapeFactory.createRandomShape() );
        this.shapeQueue.add( this.shapeFactory.createRandomShape() );
        this.shapeQueue.add( this.shapeFactory.createRandomShape() );
    }

    public ShapeQueue () {
        this.shapeQueue     = new LinkedList<>();
        this.shapeFactory   = new TetrisShape.ShapeFactory();
        this.resetQueue();
    }

    public TetrisShape pop () {
        this.shapeQueue.add( this.shapeFactory.createRandomShape() );
        return this.shapeQueue.remove();
    }

    @Override
    public String toString() {
        return "ShapeQueue{" +
                "shapeQueue=" + shapeQueue.toString() +
                '}';
    }
}
