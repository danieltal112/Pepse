package pepse;

import danogl.GameManager;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.*;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.world.Block;
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
        terrain.createInRange(1,2);


    }

    public static void main(String[] args) {
        new PepseGameManager().run();
    }

}
