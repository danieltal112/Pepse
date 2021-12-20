package pepse;

import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.*;
import danogl.util.Vector2;
import pepse.world.Sky;
import pepse.world.Terrain;
import pepse.world.daynight.Night;
import pepse.world.daynight.Sun;
import pepse.world.daynight.SunHalo;

public class PepseGameManager extends GameManager {
    private static final float CYCLE_LENGTH = 30;

    @Override
    public void initializeGame(danogl.gui.ImageReader imageReader,
                               SoundReader soundReader,
                               UserInputListener inputListener,
                               WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener,
                windowController);

        //create sky
        Sky.create(gameObjects(), windowController.getWindowDimensions(), Layer.BACKGROUND);

        //create ground
        Terrain terrain = new Terrain(gameObjects(), Layer.STATIC_OBJECTS, windowController.getWindowDimensions(), 3);
        terrain.createInRange(0, (int) windowController.getWindowDimensions().x());
        //create night
        //Night.create(gameObjects(), Layer.FOREGROUND, windowController.getWindowDimensions(), CYCLE_LENGTH);

        //create sun
        Sun.create(gameObjects(),Layer.BACKGROUND,windowController.getWindowDimensions(),30);


    }

    public static void main(String[] args) {
        new PepseGameManager().run();
    }

}
