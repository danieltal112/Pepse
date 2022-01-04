package pepse.world.daynight;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.components.CoordinateSpace;
import danogl.gui.rendering.OvalRenderable;
import danogl.util.Vector2;

import java.awt.*;

/**
 * The class responsible to the aura of the sun object in the game.
 */
public class SunHalo {


    /**
     * Creates the aura of the sun
     *
     * @param gameObjects - The Object-collection to which
     *                    the sunHalo is added.
     * @param layer       - The game-layer to which the sun
     *                    will be rendered in.
     * @param sun         - an object to be positioned according to
     * @param color       - the color of sunHalo
     * @return
     */
    public static GameObject create(GameObjectCollection gameObjects,
                                    int layer,
                                    GameObject sun,
                                    Color color) {

        GameObject sunHalo = new GameObject(Vector2.ZERO,
                new Vector2(200,
                        200),
                new OvalRenderable(color));
        sunHalo.setCenter(sun.getCenter());
        sunHalo.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        sunHalo.setTag("sunHalo");
        gameObjects.addGameObject(sunHalo, layer);
        sunHalo.addComponent(num -> sunHalo.setCenter(sun.getCenter()));
        return sunHalo;

    }
}
