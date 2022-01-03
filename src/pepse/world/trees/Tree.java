package pepse.world.trees;

import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import pepse.world.Block;
import pepse.world.Terrain;

import java.util.Random;


/**
 *
 */
public class Tree {

    private GameObjectCollection gameObjects;
    private Terrain terrain;
    private Random random = new Random();

    //constructor
    public Tree(GameObjectCollection gameObjects, Terrain terrain) {
        this.gameObjects = gameObjects;
        this.terrain = terrain;
    }

    /**
     * @return
     */
    private boolean randomToPlant() {
        int num = random.nextInt(100);
        if (num <= 10) {
            return true;
        }
        return false;
    }


    private void treeRandom(int xCoordinate) {
        int obj = random.nextInt(2);
        switch (obj) {
            case 0:
                new EucalyptusTree(gameObjects, xCoordinate, terrain);
                break;
            default:
                new AlonTree(gameObjects, xCoordinate, terrain);
        }
    }


    /**
     * @param minX
     * @param maxX
     */

    public void createInRange(int minX, int maxX) {
        int[] fixMinMax = Block.fixRange(minX, maxX);
        minX = fixMinMax[0];
        maxX = fixMinMax[1];

        for (int x = minX; x < maxX; x += Block.SIZE) {
            if (randomToPlant()) {
                //todo
                treeRandom(x);
                //new AlonTree(gameObjects, x, terrain);
                x += Block.SIZE * 2;
            }
        }
        gameObjects.layers().shouldLayersCollide(Layer.BACKGROUND + 30, Layer.STATIC_OBJECTS, true);
        gameObjects.layers().shouldLayersCollide(Layer.DEFAULT, Layer.STATIC_OBJECTS + 2, true);
    }

}

