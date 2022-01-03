package pepse;

import danogl.GameManager;
import danogl.collisions.Layer;
import danogl.gui.*;
import danogl.gui.rendering.Camera;
import danogl.util.Counter;
import danogl.util.Vector2;
import pepse.world.*;
import pepse.world.daynight.Night;
import pepse.world.daynight.Sun;
import pepse.world.trees.Tree;

import java.util.Random;

/**
 *
 */
public class PepseGameManager extends GameManager {
    private static final float CYCLE_NIGHT = 30;
    private static final float CYCLE_SUN = 5000;
    private static final float TEXT_SIZE = 25;
    public static final int INIT_FLY_COUNTER_VAL = 100;

    private WindowController windowController;
    private Avatar avatar;
    private Terrain terrain;
    private Tree tree;
    private boolean flag = false;
    private int horizontalWindowSize;
    private Vector2 initAvatarPlacement;
    private int terrainCounter = 0;
    private FlyCounter flyCounter;
    private final Counter currentFlightDuration = new Counter(INIT_FLY_COUNTER_VAL);
    private ObjectCollectionManager manager;


    /**
     * @param imageReader      - ImageReader object, used to render the game objects.
     * @param soundReader      - SoundReader Object, used to supply the sounds in the game.
     * @param inputListener    - InputListener object, used to get input from the user.
     * @param windowController - WindowController object.
     */
    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader, UserInputListener inputListener, WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);

        this.windowController = windowController;
        this.horizontalWindowSize = (int) windowController.getWindowDimensions().x();
//          Initialize sky:
        Sky.create(gameObjects(), windowController.getWindowDimensions(), Layer.BACKGROUND);

        //create ground
        createGround();

        //create night
        Night.create(gameObjects(), Layer.FOREGROUND, windowController.getWindowDimensions(), CYCLE_NIGHT);

        //create sun
        Sun.create(gameObjects(), Layer.BACKGROUND, windowController.getWindowDimensions(), CYCLE_SUN);
        //create trees
        this.tree = new Tree(gameObjects(), terrain);
        tree.createInRange((int) initAvatarPlacement.x() - horizontalWindowSize, (int) (initAvatarPlacement.x() + horizontalWindowSize));


        //create avatar
        this.avatar = Avatar.create(gameObjects(), Layer.DEFAULT, initAvatarPlacement, inputListener,
                imageReader);
        turnCameraOn();
        flag = true;

//        Initialize Fly duration counter:
        this.flyCounter = initFlyCounter();


    }

    private FlyCounter initFlyCounter() {
        Vector2 textPlacement = new Vector2(Block.SIZE, Block.SIZE);
        FlyCounter counter = new FlyCounter(currentFlightDuration,
                textPlacement,
                new Vector2(TEXT_SIZE, TEXT_SIZE),
                gameObjects());
        gameObjects().addGameObject(counter, Layer.FOREGROUND);

        return counter;
    }

    private void createGround() {
        Random rand = new Random();
        this.terrain = new Terrain(gameObjects(), Layer.STATIC_OBJECTS, windowController.getWindowDimensions(), rand.nextInt());
        int AvatarXPlacement = fixAvatarAlignment();
        this.initAvatarPlacement = new Vector2(AvatarXPlacement,
                terrain.groundHeightAt(AvatarXPlacement) - Avatar.AVATAR_SIZE);
        terrain.createInRange(AvatarXPlacement - horizontalWindowSize, (AvatarXPlacement + horizontalWindowSize));
    }

    private int fixAvatarAlignment() {
        int start = Block.SIZE;
        while (start < horizontalWindowSize / 2) {
            start += Block.SIZE;
        }
        return start;
    }

    // public void turnCameraOn(GameObject object, Vector2 location) {
    private void turnCameraOn() {
        Vector2 placement = new Vector2(initAvatarPlacement.mult(-1));
        Vector2 start = new Vector2(windowController.getWindowDimensions().mult(0.5f).add(placement));
        setCamera(new Camera(avatar,            //object to follow
                start,    //follow the center of the object
                windowController.getWindowDimensions(),  //widen the frame a bit
                windowController.getWindowDimensions()   //share the window dimensions
        ));
    }


    private void removeObj(int border) {
        float avatarX = avatar.getCenter().x();
        for (var obj : gameObjects()) {
            if (obj.getCenter().x() < avatarX - border) {
                gameObjects().removeGameObject(obj, Layer.STATIC_OBJECTS);
            }
        }
        for (var obj : gameObjects()) {
            if (obj.getCenter().x() > avatarX + border) {
                gameObjects().removeGameObject(obj, Layer.STATIC_OBJECTS);
            }
        }

    }

    private void updateGround() {
        if (avatar.getCenter().x() > initAvatarPlacement.x() + horizontalWindowSize * terrainCounter)
            addGround();
    }

    private void addGround() {
        terrainCounter++;
        int start = (int) (initAvatarPlacement.x() + horizontalWindowSize * terrainCounter);
        int end = (int) (initAvatarPlacement.x() + horizontalWindowSize * (terrainCounter + 1));
        terrain.createInRange(start, end);
    }


    private boolean checkAvatarUnderground() {
        return avatar.getTopLeftCorner().y() > terrain.groundHeightAt(avatar.getTopLeftCorner().x() + (Block.SIZE));
    }

    private void relocateAvatar() {
        if (checkAvatarUnderground()) {
            float yValue = terrain.groundHeightAt(avatar.getTopLeftCorner().x()) - Avatar.AVATAR_SIZE;
            Vector2 reLocation = new Vector2(avatar.getTopLeftCorner().x(), yValue);
            avatar.setTopLeftCorner(reLocation);
        }
    }

    @Override
    public void update(float deltaTime) {
        gameObjects().removeGameObject(flyCounter, Layer.FOREGROUND);
        flyCounter = initFlyCounter();

        super.update(deltaTime);
        if (flag) {
//            updateGround();
            removeObj(2 * horizontalWindowSize);
        }

        if (avatar.getTopLeftCorner().x() < initAvatarPlacement.x() + horizontalWindowSize * terrainCounter) {
            terrainCounter--;
//                int start = startAvatar + xDimension * (counter - 1);
//                int end = startAvatar + xDimension * counter;
//                terrain.createInRange(start, end);
        }

        relocateAvatar();
        if (Avatar.getAvatarFliesFlag()) {
            if (currentFlightDuration.value() == 0)
                Avatar.setAvatarFalls(true);
            else
                currentFlightDuration.decrement();
        } else {
            currentFlightDuration.reset();
            currentFlightDuration.increaseBy(INIT_FLY_COUNTER_VAL);

        }
    }


    public static void main(String[] args) {
        new PepseGameManager().run();
    }

}
