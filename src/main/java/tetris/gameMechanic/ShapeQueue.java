package tetris.gameMechanic;

import tetris.gameObject.TetrisShape;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ShapeQueue {

    private final Queue <TetrisShape>         shapeQueue;
    private final TetrisShape.ShapeFactory    shapeFactory;

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

    public List<TetrisShape> getShapes () {
        return new ArrayList<>(this.shapeQueue);
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
