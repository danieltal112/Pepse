package pepse.world.trees;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.components.CoordinateSpace;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import pepse.world.Block;
import pepse.world.Terrain;

import java.awt.*;
import java.util.Random;

public class AlonTree {
    private GameObjectCollection gameObjects;
    private int xPlant;
    private Terrain terrain;
    private Random random = new Random();


    private final int MIN_ALON_HEIGHT = 250;
    private final int MAX_MORE_ALON_HEIGHT = 150;

    //constructor
    public AlonTree(GameObjectCollection gameObjects, int xPlant, Terrain terrain) {
        this.gameObjects = gameObjects;
        this.xPlant = xPlant;
        this.terrain = terrain;
        createTreeAlon();
    }

    private Vector2 sizeTrunk() {
        int size = MIN_ALON_HEIGHT + random.nextInt(MAX_MORE_ALON_HEIGHT);
        return new Vector2(Block.SIZE, size);
    }

    private Vector2 positionTrunk(int x, float sizeTrunk) {
        float y = terrain.groundHeightAt(x) - sizeTrunk;
        return new Vector2(x, y);
    }

    private void createTreeAlon() {
        Block alon = new Block(Vector2.ZERO, new RectangleRenderable(new Color(100, 50, 20)));
        alon.setDimensions(sizeTrunk());
        alon.setTag("alon");
        alon.setTopLeftCorner(positionTrunk(xPlant, alon.getDimensions().y()));
        alon.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        gameObjects.addGameObject(alon);
        makeLeafAlon(alon.getTopLeftCorner());
    }

    private void makeLeafAlon(Vector2 corner) {
        float xx = corner.x() - 60;
        float yy = corner.y() - 120;
        for (float y = 1; y <= 6; y++) {
            for (float x = 1; x <= 3; x++) {
                if ((y < 5 || x != 2) && random.nextInt(100) > 10) {
                    GameObject learAlon = new GameObject(Vector2.ZERO, Vector2.ONES.mult(Block.SIZE), new RectangleRenderable(new Color(50, 200, 30)));
                    learAlon.setTopLeftCorner(new Vector2(xx + (30 * x), yy + (30 * y)));
                    learAlon.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
                    gameObjects.addGameObject(learAlon);
                }
            }

        }
    }

}
