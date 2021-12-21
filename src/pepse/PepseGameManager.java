package pepse;

import danogl.GameManager;
import danogl.collisions.Layer;
import danogl.gui.*;
import pepse.world.Sky;
import pepse.world.Terrain;
import pepse.world.daynight.Night;
import pepse.world.daynight.Sun;
import pepse.world.trees.Tree;

/**
 *
 */
public class PepseGameManager extends GameManager {
    private static final float CYCLE_NIGHT = 30;
    private static final float CYCLE_SUN = 1500;

    /**
     * @param imageReader
     * @param soundReader
     * @param inputListener
     * @param windowController
     */
    @Override
    public void initializeGame(danogl.gui.ImageReader imageReader,
                               SoundReader soundReader,
                               UserInputListener inputListener,
                               WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener,
                windowController);

        //create sky
        Sky.create(gameObjects(), windowController.getWindowDimensions(), Layer.BACKGROUND);

        //create ground
        Terrain terrain = new Terrain(gameObjects(), Layer.STATIC_OBJECTS, windowController.getWindowDimensions(), 3);
        terrain.createInRange(0, (int) windowController.getWindowDimensions().x());
        //create night
        Night.create(gameObjects(), Layer.FOREGROUND, windowController.getWindowDimensions(), CYCLE_NIGHT);

        //create sun
        Sun.create(gameObjects(), Layer.BACKGROUND, windowController.getWindowDimensions(), CYCLE_SUN);
        //create tree
        Tree tree = new Tree(gameObjects(), terrain);
        tree.createInRange(14, (int) windowController.getWindowDimensions().x());

    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        new PepseGameManager().run();
    }

}
