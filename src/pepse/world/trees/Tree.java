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

/**
 *
 */
public class Tree {

    private GameObjectCollection gameObjects;
    private Terrain terrain;
    private Random random = new Random();
    private final int MIN_ALON_HEIGHT = 200;
    private final int MAX_MORE_ALON_HEIGHT = 100;
    private final int MIN_EUCALYPTUS_HEIGHT = 100;
    private final int MAX_MORE_EUCALYPTUS_HEIGHT = 50;


    public Tree(GameObjectCollection gameObjects, Terrain terrain) {
        this.gameObjects = gameObjects;
        this.terrain = terrain;
    }


    private Vector2 positionTrunk(int x, float sizeTrunk) {
        float y = terrain.groundHeightAt(x) - sizeTrunk;
        return new Vector2(x, y);
    }


    private boolean randomToPlant() {

        int num = random.nextInt(100);
        if (num <= 10) {
            return true;
        }
        return false;
    }

    private Vector2 sizeTrunk(int minHeight, int maxMore) {
        int size = minHeight + random.nextInt(maxMore);
        return new Vector2(Block.SIZE, size);
    }

    private Block makeTruckAlon() {
        Block alon = new Block(Vector2.ZERO, new RectangleRenderable(new Color(100, 50, 20)));
        alon.setDimensions(sizeTrunk(MIN_ALON_HEIGHT, MAX_MORE_ALON_HEIGHT));
        alon.setTag("alon");
        return alon;
    }

    private Block makeTruckEucalyptus() {
        Block eucalyptus = new Block(Vector2.ZERO, new RectangleRenderable(new Color(100, 80, 80)));
        eucalyptus.setDimensions(sizeTrunk(MIN_EUCALYPTUS_HEIGHT, MAX_MORE_EUCALYPTUS_HEIGHT));
        eucalyptus.setTag("eucalyptus");
        return eucalyptus;
    }
    private GameObject makeLeafAlon() {

    }

    private Block treeRandom(int x) {
        int obj = random.nextInt(2);
        switch (obj) {
            case 0:
                return makeTruckAlon();
            default:
                return makeTruckEucalyptus();
        }
    }
    private GameObject makeLeafToTree(String tag) {
        switch (tag) {
            case "alon":
                return makeTruckAlon();
            default:
                return makeTruckEucalyptus();
        }
    }


    /**
     * @param minX
     * @param maxX
     */
    public void createInRange(int minX, int maxX) {

        for (int x = minX; x < maxX; x += Block.SIZE) {
            if (randomToPlant()) {
                GameObject truck = treeRandom(x);
                GameObject leaf = makeLeafToTree(truck.getTag());
                truck.setTopLeftCorner(positionTrunk(x, truck.getDimensions().y()));
                truck.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
                gameObjects.addGameObject(truck);

            }
        }

    }
}

