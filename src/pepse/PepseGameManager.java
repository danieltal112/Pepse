package pepse;

import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.components.CoordinateSpace;
import danogl.gui.*;
import danogl.gui.rendering.Camera;
import danogl.util.Vector2;
import pepse.world.Avatar;
import pepse.world.Sky;
import pepse.world.Terrain;
import pepse.world.daynight.Night;
import pepse.world.daynight.Sun;
import pepse.world.trees.Tree;

import java.util.Random;

/**
 *
 */
public class PepseGameManager extends GameManager {
    private static final float CYCLE_NIGHT = 30;
    private static final float CYCLE_SUN = 2100;
    private WindowController windowController;

    /**
     * @param imageReader      - ImageReader object, used to render the game objects.
     * @param soundReader      - SoundReader Object, used to supply the sounds in the game.
     * @param inputListener    - InputListener object, used to get input from the user.
     * @param windowController - WindowController object.
     */
    @Override
    public void initializeGame(danogl.gui.ImageReader imageReader,
                               SoundReader soundReader,
                               UserInputListener inputListener,
                               WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener,
                windowController);

        this.windowController = windowController;

        //create sky
        Sky.create(gameObjects(), windowController.getWindowDimensions(), Layer.BACKGROUND);

        //create ground
        Random rand = new Random();
        Terrain terrain = new Terrain(gameObjects(), Layer.STATIC_OBJECTS,
                windowController.getWindowDimensions(), rand.nextInt());
        terrain.createInRange(0, (int) windowController.getWindowDimensions().x());

        //create night
        Night.create(gameObjects(), Layer.FOREGROUND, windowController.getWindowDimensions(), CYCLE_NIGHT);

        //create sun
        Sun.create(gameObjects(), Layer.BACKGROUND, windowController.getWindowDimensions(), CYCLE_SUN);
        //create trees
        Tree tree = new Tree(gameObjects(), terrain);
        tree.createInRange(0, (int) windowController.getWindowDimensions().x());
        //create avatar
        Avatar avatar = Avatar.create(gameObjects(), Layer.DEFAULT, Vector2.ZERO, inputListener, imageReader);
        setCamera(new Camera(avatar, windowController.getWindowDimensions().mult(0.5f),
                windowController.getWindowDimensions(),
                windowController.getWindowDimensions()));
        turnCameraOn(avatar);
    }

    public void turnCameraOn(GameObject object) {
        this.setCamera(
                new Camera(
                        object,            //object to follow
                        Vector2.ZERO,    //follow the center of the object
                        windowController.getWindowDimensions(),  //widen the frame a bit
                        windowController.getWindowDimensions()   //share the window dimensions
                )
        );
    }

    public static void main(String[] args) {
        new PepseGameManager().run();
    }

}
