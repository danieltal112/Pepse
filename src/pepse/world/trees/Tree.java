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

    private void makeLeafAlon(Vector2 corner) {
        float xx = corner.x() - 90;
        float yy = corner.y() - 150;
        for (float y = 1; y <= 5; y++) {
            for (float x = 1; x <= 5; x++) {

                GameObject learAlon = new GameObject(Vector2.ZERO, Vector2.ONES.mult(Block.SIZE), new RectangleRenderable(new Color(50, 200, 30)));
                learAlon.setTopLeftCorner(new Vector2(xx+(30*x), yy+(30*y)));
                learAlon.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
                gameObjects.addGameObject(learAlon);
            }

        }
    }

    private void makeLeafEucalyptus(Vector2 corner) {
        float xx = corner.x() - 90;
        float yy = corner.y() - 150;
        for (float y = 1; y <= 5; y++) {
            for (float x = 1; x <= 5; x++) {

                GameObject learAlon = new GameObject(Vector2.ZERO, Vector2.ONES.mult(Block.SIZE), new RectangleRenderable(new Color(50, 200, 30)));
                learAlon.setTopLeftCorner(new Vector2(xx+(30*x), yy+(30*y)));
                learAlon.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
                gameObjects.addGameObject(learAlon);
            }

        }
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

    private void makeLeafToTree(String tag,Vector2 corner) {
        switch (tag) {
            case "alon":
                 makeLeafAlon(corner);
                 break;
            default:
                 makeLeafEucalyptus(corner);
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
                truck.setTopLeftCorner(positionTrunk(x, truck.getDimensions().y()));
                truck.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
                gameObjects.addGameObject(truck);
                makeLeafToTree(truck.getTag(),truck.getTopLeftCorner());

            }
        }

    }
}

