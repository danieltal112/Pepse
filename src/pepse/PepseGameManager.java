package pepse;

import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.components.CoordinateSpace;
import danogl.gui.*;
import danogl.gui.rendering.Camera;
import danogl.util.Vector2;
import pepse.world.Avatar;
import pepse.world.Block;
import pepse.world.Sky;
import pepse.world.Terrain;
import pepse.world.daynight.Night;
import pepse.world.daynight.Sun;
import pepse.world.trees.Tree;

import java.util.Objects;
import java.util.Random;

/**
 *
 */
public class PepseGameManager extends GameManager {
    private static final float CYCLE_NIGHT = 30;
    private static final float CYCLE_SUN = 2100;
    private static final float AVATAR_SIZE = 50;
    private WindowController windowController;
    private Avatar avatar;
    private Terrain terrain;
    private Tree tree;
    private boolean flag = false;
    private int xDimension;
    private Vector2 startLocation;
    private int counter = 0;
    private int startAvatar;


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
        this.xDimension = (int) windowController.getWindowDimensions().x();

        //create sky
        Sky.create(gameObjects(), windowController.getWindowDimensions(), Layer.BACKGROUND);

        //create ground
        Random rand = new Random();
        this.terrain = new Terrain(gameObjects(), Layer.STATIC_OBJECTS,
                windowController.getWindowDimensions(), rand.nextInt());
        //  terrain.createInRange(-2 * xDimension, 2 * xDimension);
        startAvatar=fix();
        this.startLocation = new Vector2(startAvatar, terrain.groundHeightAt(startAvatar) - AVATAR_SIZE);
        terrain.createInRange((int) startLocation.x() - xDimension, (int) (startLocation.x() + xDimension));

        //create night
        Night.create(gameObjects(), Layer.FOREGROUND, windowController.getWindowDimensions(), CYCLE_NIGHT);

        //create sun
        Sun.create(gameObjects(), Layer.BACKGROUND, windowController.getWindowDimensions(), CYCLE_SUN);
        //create trees
        this.tree = new Tree(gameObjects(), terrain);
        tree.createInRange((int) startLocation.x() - xDimension, (int) (startLocation.x() + xDimension));


        //create avatar
        this.avatar = Avatar.create(gameObjects(), Layer.DEFAULT, startLocation, inputListener, imageReader);
        turnCameraOn(startLocation);
        flag = true;
    }

    private int fix() {
        int start = Block.SIZE;
        while (start < xDimension / 2) {
            start += Block.SIZE;
        }
        return start;
    }

    // public void turnCameraOn(GameObject object, Vector2 location) {
    public void turnCameraOn(Vector2 location) {
        Vector2 start = new Vector2(windowController.getWindowDimensions().mult(0.5f).add(location.mult(-1)));
        setCamera(new Camera(
                avatar,            //object to follow
                start,    //follow the center of the object
                windowController.getWindowDimensions(),  //widen the frame a bit
                windowController.getWindowDimensions()   //share the window dimensions
        ));
    }


    private void removeObj(int border, int flagRemove) {

        if (flagRemove == 1) {
            for (var obj : gameObjects()) {
                if (obj.getTopLeftCorner().x() < border) {
                    gameObjects().removeGameObject(obj, Layer.STATIC_OBJECTS);
                }
            }
        } else {
            for (var obj : gameObjects()) {
                if (obj.getTopLeftCorner().x() > border) {
                    gameObjects().removeGameObject(obj, Layer.STATIC_OBJECTS);
                }
            }
        }
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if (flag) {
            if (avatar.getTopLeftCorner().x() > startAvatar + xDimension * counter) {
                counter++;
                int start = startAvatar + xDimension * counter;
                int end = startAvatar + xDimension * (counter + 1);
                terrain.createInRange(start, end);
                //todo
             //   tree.createInRange(start, end);
                removeObj(start - xDimension * 2, 1);
            }

            if (avatar.getTopLeftCorner().x() < startAvatar + xDimension * counter) {
                counter--;
                int start = startAvatar + xDimension * (counter - 1);
                int end = startAvatar + xDimension * counter;
                terrain.createInRange(start, end);
                //todo
               // tree.createInRange(start, end);
                removeObj(end + xDimension * 2, 2);
            }
            //if the ball down more from the ground
            if (avatar.getTopLeftCorner().y() > terrain.groundHeightAt(avatar.getTopLeftCorner().x())) {
                Vector2 reLocation = new Vector2(avatar.getTopLeftCorner().x(), terrain.groundHeightAt(avatar.getTopLeftCorner().x()) - AVATAR_SIZE - 3);
                avatar.setTopLeftCorner(reLocation);
            }

        }
    }


    public static void main(String[] args) {
        new PepseGameManager().run();
    }

}
