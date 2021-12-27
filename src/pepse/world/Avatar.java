package pepse.world;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.components.CoordinateSpace;
import danogl.components.GameObjectPhysics;
import danogl.components.ScheduledTask;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.OvalRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import danogl.gui.*;
import danogl.gui.rendering.Renderable;
import danogl.gui.rendering.Renderable;

import java.awt.*;
import java.awt.event.KeyEvent;

import danogl.gui.rendering.Renderable;
import pepse.PepseGameManager;

/**
 * The class is responsible for everything related to the user's avatar object.
 */
public class Avatar extends GameObject {

    private static final float VELOCITY_X = 250;
    private static final float VELOCITY_Y = -400;
    private static final float GRAVITY = 300;
    private static final Color AVATAR_COLOR = Color.DARK_GRAY;

    private static UserInputListener inputListener;

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

        Avatar avatar = new Avatar(topLeftCorner, Vector2.ONES.mult(50), new OvalRenderable(AVATAR_COLOR));
        gameObjects.addGameObject(avatar, layer);
        Avatar.inputListener = inputListener;
        return avatar;
    }

    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        float xVel = 0;
        if (inputListener.isKeyPressed(KeyEvent.VK_LEFT))
            xVel -= VELOCITY_X;
        if (inputListener.isKeyPressed(KeyEvent.VK_RIGHT))
            xVel += VELOCITY_X;
        transform().setVelocityX(xVel);
        if (inputListener.isKeyPressed(KeyEvent.VK_SPACE) && inputListener.isKeyPressed(KeyEvent.SHIFT_DOWN_MASK )) {

            physics().preventIntersectionsFromDirection(null);
            new ScheduledTask(this, .5f, false,
                    () -> physics().preventIntersectionsFromDirection(Vector2.ZERO));
            return;
        }
        if (inputListener.isKeyPressed(KeyEvent.VK_SPACE) && getVelocity().y() == 0)
            transform().setVelocityY(VELOCITY_Y);
    }
}
