package pepse;

import danogl.GameManager;
import danogl.collisions.Layer;
import danogl.gui.*;
import danogl.util.Vector2;
import pepse.world.Sky;
import pepse.world.Terrain;

public class PepseGameManager extends GameManager {

    @Override
    public void initializeGame(danogl.gui.ImageReader imageReader,
                               SoundReader soundReader,
                               UserInputListener inputListener,
                               WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener,
                windowController);

        //create sky
        Sky.create(gameObjects(),windowController.getWindowDimensions() , Layer.BACKGROUND);

        //create ground
        Terrain terrain=new Terrain(gameObjects(),Layer.STATIC_OBJECTS,windowController.getWindowDimensions(),3);
        terrain.createInRange(0,(int)windowController.getWindowDimensions().x());

    }

    public static void main(String[] args) {
        new PepseGameManager().run();
    }

}
