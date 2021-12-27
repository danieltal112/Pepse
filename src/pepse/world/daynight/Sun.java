package pepse.world.daynight;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.components.CoordinateSpace;
import danogl.components.Transition;
import danogl.gui.rendering.OvalRenderable;
import danogl.util.Vector2;

import java.awt.*;

/**
 * The class responsible to everything related to the sun object in the game.
 */
public class Sun {

    private static final String SUN_TAG = "sun";
    private static final float SUN_SIZE = 100;
    private static final Float INIT_ANGLE = 0f;
    private static final Float MAX_ANGLE = 360f;
    private static final Color HALO_COLOR = new Color(255, 255, 0, 20);


    /**
     * Calculates the position of the sun in the sky as a function of its angle.
     *
     * @param windowDimensions - The game window dimensions.
     * @param angleInSky       - The angle of the sun's position.
     * @return - Coordinates for the sun's position.
     */
    private static Vector2 calcSunPosition(Vector2 windowDimensions, float angleInSky) {
        float radius = windowDimensions.y() / 2;
        float x, y;
        x = (windowDimensions.x() / 2) - (radius * (float) Math.cos(angleInSky));
        y = windowDimensions.y() / 2 + radius * (float) Math.sin(angleInSky);
        return new Vector2(x, y);
    }

    /**
     * Creates a new Sun object in the sky.
     *
     * @param gameObjects      - The Object-collection to which the sun is added.
     * @param layer            - The game-layer to which the sun will be rendered in.
     * @param windowDimensions - The dimensions of the game-window.
     * @param cycleLength      - The length of the sun's cycle in the sky (in seconds).
     * @return
     */
    public static GameObject create(GameObjectCollection gameObjects,
                                    int layer,
                                    Vector2 windowDimensions,
                                    float cycleLength) {

        GameObject sun = new GameObject(Vector2.ZERO,
                new Vector2(SUN_SIZE, SUN_SIZE),
                new OvalRenderable(Color.YELLOW)
        );
        sun.setCenter(new Vector2(windowDimensions.x() / 2, SUN_SIZE));
        sun.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        sun.setTag(SUN_TAG);
        new Transition<>(
                sun,
                (Float angle) -> sun.setCenter(calcSunPosition(windowDimensions, angle)),
                INIT_ANGLE,
                MAX_ANGLE,
                Transition.LINEAR_INTERPOLATOR_FLOAT,
                cycleLength,
                Transition.TransitionType.TRANSITION_LOOP,
                null);

        SunHalo.create(gameObjects, layer, sun, HALO_COLOR);
        gameObjects.addGameObject(sun, layer);
        return sun;
    }
}
