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

public class Avatar extends GameObject {

    private static final float VELOCITY_X = 400;
    private static final float VELOCITY_Y = -650;
    private static final float GRAVITY = 200;
    private static final Color AVATAR_COLOR = Color.DARK_GRAY;


    public Avatar(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable) {
        super(topLeftCorner, dimensions, renderable);
        transform().setAccelerationY(GRAVITY);
        physics().preventIntersectionsFromDirection(Vector2.ZERO);

    }


    public static Avatar create(GameObjectCollection gameObjects,
                                int layer,
                                Vector2 topLeftCorner,
                                UserInputListener inputListener,
                                ImageReader imageReader) {

        // Renderable avatarImage = imageReader.readImage("ball.png", true);
        Avatar avatar = new Avatar(topLeftCorner, Vector2.ONES.mult(50), new OvalRenderable(AVATAR_COLOR));
        avatar.setCenter(new Vector2(450, 450));
        avatar.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        gameObjects.addGameObject(avatar, layer);
        return avatar;
    }

//    @Override
//    public void update(float deltaTime) {
//        super.update(deltaTime);
//        float xVel = 0;
//        if (inputListener.isKeyPressed(KeyEvent.VK_LEFT))
//            xVel -= VELOCITY_X;
//        if (inputListener.isKeyPressed(KeyEvent.VK_RIGHT))
//            xVel += VELOCITY_X;
//        transform().setVelocityX(xVel);
//        if (inputListener.isKeyPressed(KeyEvent.VK_SPACE) && inputListener.isKeyPressed(KeyEvent.VK_DOWN)) {
//            physics().preventIntersectionsFromDirection(null);
//            new ScheduledTask(this, .5f, false,
//                    () -> physics().preventIntersectionsFromDirection(Vector2.ZERO));
//            return;
//        }
//        if (inputListener.isKeyPressed(KeyEvent.VK_SPACE) && getVelocity().y() == 0)
//            transform().setVelocityY(VELOCITY_Y);
//    }

//    @Override
//    public void onCollisionEnter(GameObject other, Collision collision) {
//        super.onCollisionEnter(other, collision);
//        this.setVelocity(new Vector2(0, 0));
//    }
}







