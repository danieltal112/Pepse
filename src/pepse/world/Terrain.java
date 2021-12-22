package pepse.world;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.components.CoordinateSpace;
import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.util.ColorSupplier;

import java.awt.*;

/**
 *
 */
public class Terrain {

    private GameObjectCollection gameObjects;
    private int groundLayer;
    private Vector2 windowDimensions;
    private int seed;
    private static final Color BASE_GROUND_COLOR = new Color(112, 62, 28);

    //constructor
    public Terrain(GameObjectCollection gameObjects,
                   int groundLayer,
                   Vector2 windowDimensions,
                   int seed) {
        this.gameObjects = gameObjects;
        this.groundLayer = groundLayer;
        this.windowDimensions = windowDimensions;
        this.seed = seed;

    }

    /**
     * @param x
     * @return
     */
    //todo
    public float groundHeightAt(float x) {
        float c = 2f / 3;
        return (float) windowDimensions.y() * c + x % 100;
    }

    /**
     * @param minX
     * @param maxX
     */
    public void createInRange(int minX, int maxX) {
        int[] fixMinMax = fixRange(minX, maxX);
        minX = fixMinMax[0];
        maxX = fixMinMax[1];

        Renderable renderable = new RectangleRenderable(ColorSupplier.approximateColor(BASE_GROUND_COLOR));
        for (int i = minX; i <= maxX; i += 30) {
            GameObject ground = new Block(Vector2.ZERO, renderable);
            ground.setDimensions(new Vector2(Block.SIZE, windowDimensions.y() - groundHeightAt(i)));
            ground.setTopLeftCorner(new Vector2(i, groundHeightAt(i)));
            ground.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
            ground.setTag("ground");
            gameObjects.addGameObject(ground, groundLayer);
        }
    }

    /**
     * @param minX
     * @param maxX
     * @return
     */
    private int[] fixRange(int minX, int maxX) {
        int[] fixMinMax = new int[2];

        if (minX >= 0) {
            minX -= (minX % 30);
        } else {
            minX -= (30 + minX % 30) % 30;
        }
        fixMinMax[0] = minX;
        int counter = minX;
        while (counter <= maxX) {
            counter += 30;
        }
        fixMinMax[1] = counter;
        return fixMinMax;
    }
}
