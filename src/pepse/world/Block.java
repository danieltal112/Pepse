package pepse.world;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.components.GameObjectPhysics;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * Base-object for the entire range of rectangles which will be used in the game.
 */
public class Block extends GameObject {
    public static final int SIZE = 30;

    /**
     * A constructor for a Block class object.
     *
     * @param topLeftCorner - The top-left coordinates of the object at the screen.
     * @param renderable    - Renderable-type object, which provides a visual representation for the block.
     */
    public Block(Vector2 topLeftCorner, Renderable renderable) {
        super(topLeftCorner, Vector2.ONES.mult(SIZE), renderable);

        physics().preventIntersectionsFromDirection(Vector2.ZERO);
        physics().setMass(GameObjectPhysics.IMMOVABLE_MASS);
    }
}



