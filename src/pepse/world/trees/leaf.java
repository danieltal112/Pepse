package pepse.world.trees;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.GameObjectCollection;
import danogl.components.ScheduledTask;
import danogl.components.Transition;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.world.Block;
import pepse.world.Terrain;

import java.util.Random;

public class leaf extends GameObject {
    private static final float SPEED_LEAF_FALL = 150;

    private Random random = new Random();
    private final int LIFE_TIME = 60;
    private final int DEAD_TIME = 10;


    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     */
    public leaf(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable) {
        super(topLeftCorner, dimensions, renderable);
        leafTransition(this);
    }

    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        this.setVelocity(Vector2.ZERO);
    }
    private void moveLeaf(GameObject learAlon) {
        new Transition<Float>(
                learAlon,
                learAlon.renderer()::setRenderableAngle,
                0f,
                12f,
                Transition.LINEAR_INTERPOLATOR_FLOAT,
                5,
                Transition.TransitionType.TRANSITION_BACK_AND_FORTH,
                null);
    }


    private void changeSizeLeaf(GameObject learAlon) {
        Transition<Float> transition = new Transition<Float>(
                learAlon,
                (Float width) -> learAlon.setDimensions(new Vector2(width, Block.SIZE)),
                25f,
                35f,
                Transition.CUBIC_INTERPOLATOR_FLOAT,
                5,
                Transition.TransitionType.TRANSITION_BACK_AND_FORTH,
                null);
        //todo
        new ScheduledTask(learAlon, random.nextInt(LIFE_TIME), false, () -> fallLeaf(learAlon, transition));
    }


    private void fallLeaf(GameObject learAlon, Transition<Float> transition) {
        Vector2 leafLocation = new Vector2(learAlon.getTopLeftCorner());

        learAlon.setVelocity(new Vector2(0, SPEED_LEAF_FALL));
        new ScheduledTask(learAlon, 1, false, () -> learAlon.removeComponent(transition));

        new Transition<Float>(
                learAlon,
                learAlon.renderer()::fadeOut,
                1f,
                1f,
                Transition.CUBIC_INTERPOLATOR_FLOAT,
                5,
                Transition.TransitionType.TRANSITION_ONCE,
                () -> new ScheduledTask(learAlon, random.nextInt(DEAD_TIME), false, () -> reBorn(learAlon, leafLocation))
        );
    }

    private void reBorn(GameObject leaf, Vector2 leafLocation) {
        leaf.setTopLeftCorner(leafLocation);
        leaf.renderer().setOpaqueness(1);
        leafTransition(leaf);
    }

    private void leafTransition(GameObject learAlon) {
        //move leaf
        new ScheduledTask(learAlon,  (random.nextInt(150) + 20f) / 100f, false, () -> moveLeaf(learAlon));
        //change size leaf and inner have fall leaf
        new ScheduledTask(learAlon, (random.nextInt(150) + 20f) / 100f, false, () -> changeSizeLeaf(learAlon));
    }
}
