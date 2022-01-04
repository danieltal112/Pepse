package pepse.world;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.GameObjectCollection;
import danogl.components.ScheduledTask;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.AnimationRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import danogl.gui.*;

import java.awt.event.KeyEvent;


/**
 * The class is responsible for everything related to the user's avatar object.
 */
public class Avatar extends GameObject {

    private static final float VELOCITY_X = 250;
    private static final float VELOCITY_Y = -400;
    private static final float GRAVITY = 300;
    public static final float AVATAR_SIZE = 30;

    private static UserInputListener inputListener;
    private static boolean avatarFlies = false;
    private static boolean avatarFalls = false;
    private static AnimationRenderable standAnimation;
    private static AnimationRenderable walkAnimation;
    private static final Renderable[] standRenderable = new Renderable[1];
    private static final Renderable[] walkRenderable = new Renderable[3];
    private static Vector2 location;
    private boolean avatarFacingRight = true;


    /**
     * Constructor for an Avatar type object.
     *
     * @param topLeftCorner - The top-left corner to which the object will be rendered.
     * @param dimensions    - The object's dimensions (size).
     * @param renderable    - Renderable object which will be used to render the Avatar's image.
     */
    public Avatar(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable) {
        super(topLeftCorner, dimensions, renderable);
        transform().setAccelerationY(GRAVITY);
        physics().preventIntersectionsFromDirection(Vector2.ZERO);
    }

    /**
     * Creates new Avatar object.
     *
     * @param gameObjects   - The objects-collection to which the avatar will be added.
     * @param layer         - The layer on the game screen to which the object will present.
     * @param topLeftCorner - The top-left corner of the object on the screen.
     * @param inputListener - UI object used to control the object.
     * @param imageReader   - ImageReader object used to render the object on the screen.
     * @return - a pointer to the created object.
     */
    public static Avatar create(GameObjectCollection gameObjects,
                                int layer,
                                Vector2 topLeftCorner,
                                UserInputListener inputListener,
                                ImageReader imageReader) {
        initRenderables(imageReader, "stand");
        initRenderables(imageReader, "walk");
        standAnimation = new AnimationRenderable(initRenderables(imageReader, "walk"), 1);
        initWalkAnimation(imageReader);
        Avatar avatar = new Avatar(topLeftCorner, new Vector2(AVATAR_SIZE, AVATAR_SIZE), standAnimation);
        gameObjects.addGameObject(avatar, layer);
        Avatar.inputListener = inputListener;
        Avatar.location = avatar.getCenter();
        return avatar;
    }

    private static void initWalkAnimation(ImageReader imageReader) {
        walkRenderable[0] = imageReader.readImage("Girl-Melee_LeftFoot.png", true);
        walkRenderable[1] = imageReader.readImage("Girl-Melee_Static.png", true);
        walkRenderable[2] = imageReader.readImage("Girl-Melee_RightFoot.png", true);
        walkAnimation = new AnimationRenderable(walkRenderable, 0.1);
    }

    private static Renderable[] initRenderables(ImageReader imageReader, String typeRender) {
        switch (typeRender) {
            case "stand":
                standRenderable[0] = imageReader.readImage("Girl-Melee_Static.png", true);
                break;
            case "walk":
                walkRenderable[0] = imageReader.readImage("Girl-Melee_LeftFoot.png", true);
                walkRenderable[1] = imageReader.readImage("Girl-Melee_Static.png", true);
                walkRenderable[2] = imageReader.readImage("Girl-Melee_RightFoot.png", true);
                break;
            default:
                break;
        }
        return standRenderable;
    }

    public static boolean getAvatarFliesFlag() {
        return avatarFlies;
    }

    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        float xVel = 0;
        if (inputListener.isKeyPressed(KeyEvent.VK_LEFT)) {
            xVel -= VELOCITY_X;
            avatarFacingRight = false;
            renderer().setIsFlippedHorizontally(true);
            renderer().setRenderable(walkAnimation);
        }
        if (inputListener.isKeyPressed(KeyEvent.VK_RIGHT)) {
            xVel += VELOCITY_X;
            avatarFacingRight = true;
            renderer().setIsFlippedHorizontally(false);
            renderer().setRenderable(walkAnimation);

        }
        transform().setVelocityX(xVel);

        if (!inputListener.isKeyPressed(KeyEvent.VK_RIGHT) && !inputListener.isKeyPressed(KeyEvent.VK_LEFT))
            renderer().setRenderable(standAnimation);

        if (inputListener.isKeyPressed(KeyEvent.VK_SPACE) && inputListener.isKeyPressed(KeyEvent.VK_SHIFT)) {
            avatarFlies = true;
            if (!avatarFalls) {
                transform().setVelocityY(0.45f * VELOCITY_Y);
                new ScheduledTask(this, .5f, false,
                        () -> physics().preventIntersectionsFromDirection(Vector2.ZERO));
                return;
            }
        }
        if (getVelocity().y() == 0) {
            avatarFlies = false;
            avatarFalls = false;
            if (inputListener.isKeyPressed(KeyEvent.VK_SPACE))
                transform().setVelocityY(VELOCITY_Y);
        }
    }

    public static void setAvatarFalls(boolean flag) {
        avatarFalls = flag;
    }

    public static float getAvatarLocX() {
        return location.x();
    }

    public boolean getAvatarOrientatinon() {
        return avatarFacingRight;
    }
}
