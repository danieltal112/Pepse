package pepse.world.daynight;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.components.CoordinateSpace;
import danogl.components.Transition;
import danogl.gui.rendering.OvalRenderable;
import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.util.ColorSupplier;

import java.awt.*;

public class Sun extends Object {

    private static Vector2 calcSunPosition(Vector2 windowDimensions, float angleInSky) {

        return Vector2.ZERO;
    }

    public static GameObject create(GameObjectCollection gameObjects,
                                    int layer,
                                    Vector2 windowDimensions,
                                    float cycleLength) {

        GameObject sun = new GameObject(Vector2.ZERO, new Vector2(100, 100), new OvalRenderable(Color.YELLOW));
        sun.setCenter(new Vector2(windowDimensions.x() / 2, 100));
        sun.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        sun.setTag("sun");
        gameObjects.addGameObject(sun, layer);
        new Transition<Float>(
                sun,
                //todo
                sun.renderer()::setOpaqueness,
                0f,
                360f,
                Transition.LINEAR_INTERPOLATOR_FLOAT,
                cycleLength,
                Transition.TransitionType.TRANSITION_LOOP,
                null);

        return sun;
    }
}
