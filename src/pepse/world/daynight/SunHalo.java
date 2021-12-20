package pepse.world.daynight;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.components.CoordinateSpace;
import danogl.components.Transition;
import danogl.gui.rendering.OvalRenderable;
import danogl.util.Vector2;

import java.awt.*;

/**
 *
 */
public class SunHalo extends Object {

    /**
     *
     * @param gameObjects
     * @param layer
     * @param sun
     * @param color
     * @return
     */
    public static GameObject create(GameObjectCollection gameObjects,
                                    int layer,
                                    GameObject sun,
                                    Color color) {

        GameObject sunHalo = new GameObject(Vector2.ZERO, new Vector2(200, 200), new OvalRenderable(color));
        sunHalo.setCenter(sun.getCenter());
        sunHalo.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        sunHalo.setTag("sunHalo");
        gameObjects.addGameObject(sunHalo, layer);
        sunHalo.addComponent(num->sunHalo.setCenter(sun.getCenter()));
        return sunHalo;

    }
}
