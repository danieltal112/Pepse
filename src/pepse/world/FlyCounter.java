package pepse.world;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.components.CoordinateSpace;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Counter;
import danogl.util.Vector2;

public class FlyCounter extends GameObject {


    private final Counter flyDurationCounter;
    private final GameObjectCollection gameObjects;

    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     */
    public FlyCounter(Counter flyDurationCounter,
                      Vector2 topLeftCorner,
                      Vector2 dimensions,
                      GameObjectCollection gameObjectCollection
    ) {
        super(topLeftCorner, dimensions, new TextRenderable(String.format("Flight power: %d",
                flyDurationCounter.value())));
        this.flyDurationCounter = flyDurationCounter;
        this.gameObjects = gameObjectCollection;
        this.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
    }

    public int getCounterVal() {
        return flyDurationCounter.value();
    }


}
