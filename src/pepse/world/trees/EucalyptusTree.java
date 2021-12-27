package pepse.world.trees;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.components.CoordinateSpace;
import danogl.components.ScheduledTask;
import danogl.components.Transition;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import pepse.world.Block;
import pepse.world.Terrain;

import java.awt.*;
import java.util.Random;

public class EucalyptusTree {
    private GameObjectCollection gameObjects;
    private int xPlant;
    private Terrain terrain;
    private Random random = new Random();

    private final int MIN_EUCALYPTUS_HEIGHT = 150;
    private final int MAX_MORE_EUCALYPTUS_HEIGHT = 150;

    //constructor
    public EucalyptusTree(GameObjectCollection gameObjects, int xPlant, Terrain terrain) {
        this.gameObjects = gameObjects;
        this.xPlant = xPlant;
        this.terrain = terrain;
        createTreeEucalyptus();
    }

    private Vector2 sizeTrunk() {
        int size = MIN_EUCALYPTUS_HEIGHT + random.nextInt(MAX_MORE_EUCALYPTUS_HEIGHT);
        return new Vector2(Block.SIZE, size);
    }

    private Vector2 positionTrunk(int x, float sizeTrunk) {
        float y = terrain.groundHeightAt(x) - sizeTrunk;
        return new Vector2(x, y);
    }

    private void createTreeEucalyptus() {
        Block eucalyptus = new Block(Vector2.ZERO, new RectangleRenderable(new Color(173, 94, 3)));
        eucalyptus.setDimensions(sizeTrunk());
        eucalyptus.setTag("eucalyptus");
        eucalyptus.setTopLeftCorner(positionTrunk(xPlant, eucalyptus.getDimensions().y()));
//        eucalyptus.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        gameObjects.addGameObject(eucalyptus, Layer.STATIC_OBJECTS + 2);

        makeLeafEucalyptus(eucalyptus.getTopLeftCorner());
    }

    private void moveLeaf(GameObject learEucalyptus) {
        new Transition<Float>(
                learEucalyptus,
                learEucalyptus.renderer()::setRenderableAngle,
                0f,
                15f,
                Transition.LINEAR_INTERPOLATOR_FLOAT,
                3,
                Transition.TransitionType.TRANSITION_BACK_AND_FORTH, null);
    }

    private void changeSizeLeaf(GameObject learEucalyptus) {
        new Transition<Float>(
                learEucalyptus,
                (Float width) -> learEucalyptus.setDimensions(new Vector2(width, Block.SIZE)),
                30f,
                40f,
                Transition.CUBIC_INTERPOLATOR_FLOAT,
                10,
                Transition.TransitionType.TRANSITION_BACK_AND_FORTH, null);
    }

    private void makeLeafEucalyptus(Vector2 corner) {
        float xx = corner.x() - 90;
        float yy = corner.y() - 90;
        for (float y = 1; y <= 3; y++) {
            for (float x = 1; x <= 5; x++) {
                if ((y < 3 || x != 3) && random.nextInt(100) > 10) {
                    GameObject learEucalyptus = new leaf(Vector2.ZERO, Vector2.ONES.mult(Block.SIZE), new RectangleRenderable(new Color(48, 144, 48)));
                    learEucalyptus.setTopLeftCorner(new Vector2(xx + (30 * x), yy + (30 * y)));
//                    learEucalyptus.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
                    gameObjects.addGameObject(learEucalyptus, Layer.BACKGROUND + 30);
                }
            }

        }
    }

}
