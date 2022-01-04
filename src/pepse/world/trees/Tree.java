package pepse.world.trees;

import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import pepse.world.Block;
import pepse.world.Terrain;

import java.util.Random;


/**
 * this class responsible to create a random tree in random location
 * in bound of given to the game
 */
public class Tree {

    private GameObjectCollection gameObjects;
    private Terrain terrain;
    private Random random = new Random();

    /**
     * constructor tree
     *
     * @param gameObjects - game object collection
     * @param terrain     - the object of ground of the game
     */
    public Tree(GameObjectCollection gameObjects, Terrain terrain) {
        this.gameObjects = gameObjects;
        this.terrain = terrain;
    }

    /**
     * this function responsible to create a different trees to the game
     *
     * @param minX - start location to create tree
     * @param maxX - end location to create tree
     */
    public void createInRange(int minX, int maxX) {
        int[] fixMinMax = Block.fixRange(minX, maxX);
        minX = fixMinMax[0];
        maxX = fixMinMax[1];
        for (int x = minX; x < maxX; x += Block.SIZE) {
            if (randomToPlant()) {
                treeRandom(x);
                x += Block.SIZE * 2;
            }
        }
        //collide leaf and tree-truck and ground
        gameObjects.layers().shouldLayersCollide(
                Layer.BACKGROUND + 30,
                Layer.STATIC_OBJECTS, true);
        //collide avatar and ground and tree-turck
        gameObjects.layers().shouldLayersCollide(Layer.DEFAULT,
                Layer.STATIC_OBJECTS, true);
    }

    /**
     * Tree planting is done randomly but with a probability of 0.1
     *
     * @return true if plant or false if not
     */
    private boolean randomToPlant() {
        int num = random.nextInt(100);
        return num <= 10;
    }

    /**
     * this function is a mini factory that return random tree.
     * here have somewhat types of tree.
     *
     * @param xCoordinate - the place to plant tree
     */
    private void treeRandom(int xCoordinate) {
        boolean obj = random.nextBoolean();
        if (obj) {
            new EucalyptusTree(gameObjects, xCoordinate, terrain);
        } else {
            new AlonTree(gameObjects, xCoordinate, terrain);
        }
    }

}

