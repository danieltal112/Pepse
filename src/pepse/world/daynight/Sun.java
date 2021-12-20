package pepse.world.daynight;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
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
        float radius = windowDimensions.y() / 2;
        float x = 0;
        float y = 0;

        x = (windowDimensions.x() / 2) - (radius * (float) Math.cos(angleInSky));
        y = windowDimensions.y() / 2 + radius * (float) Math.sin(angleInSky);
        return new Vector2(x, y);
    }

    public static GameObject create(GameObjectCollection gameObjects,
                                    int layer,
                                    Vector2 windowDimensions,
                                    float cycleLength) {

        GameObject sun = new GameObject(Vector2.ZERO, new Vector2(100, 100), new OvalRenderable(Color.YELLOW));
        sun.setCenter(new Vector2(windowDimensions.x() / 2, 100));
        sun.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        sun.setTag("sun");
        new Transition<Float>(
                sun,
                (Float angle) -> sun.setCenter(calcSunPosition(windowDimensions, angle)),
                0f,
                360f,
                Transition.LINEAR_INTERPOLATOR_FLOAT,
                cycleLength,
                Transition.TransitionType.TRANSITION_LOOP,
                null);

        SunHalo.create(gameObjects, layer, sun, new Color(255, 255, 0, 20));
        gameObjects.addGameObject(sun, layer);
        return sun;
    }
}
