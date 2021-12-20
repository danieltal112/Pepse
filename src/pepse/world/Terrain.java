package pepse.world;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.components.CoordinateSpace;
import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.util.ColorSupplier;

import java.awt.*;

public class Terrain extends Object{

    private GameObjectCollection gameObjects;
    private int groundLayer;
    private Vector2 windowDimensions;
    private int seed;
    private static final Color BASE_GROUND_COLOR = new Color(212, 123, 74);
    private static final int TERRAIN_DEPTH = 20;

    //constructor
    public Terrain (GameObjectCollection gameObjects,
                    int groundLayer,
                    Vector2 windowDimensions,
                    int seed){
        this.gameObjects = gameObjects;
        this.groundLayer = groundLayer;
        this.windowDimensions = windowDimensions;
        this.seed = seed;

    }


    public float groundHeightAt(float x){
        return 700;
    }

    public void createInRange(int minX, int maxX){
        Renderable renderable=new RectangleRenderable(ColorSupplier.approximateColor(BASE_GROUND_COLOR));
        for (int i = minX; i <maxX ; i++) {
            GameObject ground = new Block(new Vector2(i,groundHeightAt(i)),renderable);
            ground.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
            gameObjects.addGameObject(ground, groundLayer);
            ground.setTag("ground");
        }


    }
}
