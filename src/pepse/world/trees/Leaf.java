package pepse.world.trees;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.components.ScheduledTask;
import danogl.components.Transition;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.world.Block;

import java.util.Random;

/**
 * this class is responsible for everything related to the features
 * of the leaves in the game.
 */
public class Leaf extends GameObject {
    private static final float SPEED_LEAF_FALL = 120;
    private static final float MIN_TIME = 20;
    private static final int MAX_MORE_TIME = 150;
    private static final float MAX_ANGLE_LEAF = 12f;
    private static final float CYCLE_TIME_ANGLE_AND_SIZE = 5;
    private static final float START_SIZE_LEAF = 25f;
    private static final float FINAL_SIZE_LEAF = 35f;
    private static final float MAX_SIDE_LEAF_SPEED = 40f;

    private Random random = new Random();
    private final int LIFE_TIME = 60;
    private final int DEAD_TIME = 10;

    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window
     *                      coordinates (pixels).
     *                      Note that (0,0) is the top-left corner
     *                      of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object.
     *                      Can be null, in which case
     */
    public Leaf(Vector2 topLeftCorner,
                Vector2 dimensions,
                Renderable renderable) {
        super(topLeftCorner, dimensions, renderable);
        leafTransition(this);
    }

    /**
     * This function is the beginning of a chain of attributes
     * iven to each leaf.
     *
     * @param leaf
     */
    private void leafTransition(GameObject leaf) {
        //move leaf
        new ScheduledTask(leaf, randomTime(),
                false, () -> angleLeaf(leaf));
        //change size leaf and inner have fall leaf
        new ScheduledTask(leaf, randomTime(),
                false, () -> changeSizeLeaf(leaf));
    }

    /**
     * return time random
     *
     * @return float "time"
     */
    private float randomTime() {
        return (MIN_TIME + random.nextInt(MAX_MORE_TIME)) / 100f;
    }

    /**
     * when the leaf collide with another object is stop and
     * don't move more
     *
     * @param other
     * @param collision
     */
    @Override
    public void onCollisionEnter(GameObject other,
                                 Collision collision) {
        super.onCollisionEnter(other, collision);
        this.setVelocity(Vector2.ZERO);
    }

    /**
     * this function change the angle of leaf
     *
     * @param leaf - leaf object
     */
    private void angleLeaf(GameObject leaf) {
        new Transition<Float>(
                leaf,
                leaf.renderer()::setRenderableAngle,
                0f,
                MAX_ANGLE_LEAF,
                Transition.LINEAR_INTERPOLATOR_FLOAT,
                CYCLE_TIME_ANGLE_AND_SIZE,
                Transition.TransitionType.TRANSITION_BACK_AND_FORTH,
                null);
    }

    /**
     * this function expands and contracts the leaf
     *
     * @param leaf - leaf object
     */
    private void changeSizeLeaf(GameObject leaf) {
        Transition<Float> transition = new Transition<Float>(
                leaf,
                (Float width) -> leaf.setDimensions
                        (new Vector2(width, Block.SIZE)),
                START_SIZE_LEAF,
                FINAL_SIZE_LEAF,
                Transition.CUBIC_INTERPOLATOR_FLOAT,
                CYCLE_TIME_ANGLE_AND_SIZE,
                Transition.TransitionType.TRANSITION_BACK_AND_FORTH,
                null);
        //call to function fallLeaf
        new ScheduledTask(leaf, random.nextInt(LIFE_TIME),
                false, () -> fallLeaf(leaf, transition));
    }

    /**
     * helper function to "moveSideLeaf" that random the direction
     * that leaf start to move when he fell
     *
     * @return
     */
    private float directionLeaf() {
        if (random.nextBoolean()) {
            return -1;
        }
        return 1;
    }

    /**
     * this function responsible to move the leaf when he fell right
     * and left.
     *
     * @param leaf
     * @return
     */
    private Transition<Float> moveSideLeaf(GameObject leaf) {
        float direction = directionLeaf();
        return new Transition<Float>(
                leaf,
                (Float speed) -> leaf.setVelocity(new Vector2(speed,
                        SPEED_LEAF_FALL)),
                direction * MAX_SIDE_LEAF_SPEED,
                -direction * MAX_SIDE_LEAF_SPEED,
                Transition.CUBIC_INTERPOLATOR_FLOAT,
                (float) random.nextInt(4),
                Transition.TransitionType.TRANSITION_BACK_AND_FORTH,
                null);
    }

    /**
     * this function Operates "moveSideLeaf" function and fade out the
     * leaf after somewhat time. and finally call to "reBorn" function.
     *
     * @param leaf
     * @param transition
     */
    private void fallLeaf(GameObject leaf,
                          Transition<Float> transition) {
        Vector2 leafLocation = new Vector2(leaf.getTopLeftCorner());
        Transition<Float> moveSide = moveSideLeaf(leaf);
        new ScheduledTask(leaf, 1, false,
                () -> leaf.removeComponent(transition));

        new Transition<Float>(
                leaf,
                leaf.renderer()::fadeOut,
                1f,
                1f,
                Transition.CUBIC_INTERPOLATOR_FLOAT,
                5,
                Transition.TransitionType.TRANSITION_ONCE,
                () -> new ScheduledTask(leaf, random.nextInt(DEAD_TIME),
                        false, () -> reBorn(leaf,
                        leafLocation, moveSide))
        );
    }

    /**
     * fadeIn the color of leaf. And places the leaf in its
     * starting place.
     *
     * @param leaf
     * @param leafLocation
     * @param moveSide
     */
    private void reBorn(GameObject leaf,
                        Vector2 leafLocation,
                        Transition<Float> moveSide) {
        leaf.removeComponent(moveSide);
        leaf.setVelocity(Vector2.ZERO);
        leaf.setTopLeftCorner(leafLocation);
        leaf.renderer().fadeIn(2);
        leafTransition(leaf);
    }
}
