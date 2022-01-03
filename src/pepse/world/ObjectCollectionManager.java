package pepse.world;

import danogl.collisions.GameObjectCollection;
import danogl.util.Counter;

public class ObjectCollectionManager {
    private Counter counter;
    private GameObjectCollection collection;

    public ObjectCollectionManager(Counter counter, GameObjectCollection collection) {
        this.counter = counter;
        this.collection = collection;
    }

    private void addGround() {
        if (Avatar.getAvatarOrientatinon()) {
//            int start = (int) (initAvatarPlacement.x() + horizontalWindowSize * terrainCounter);
//            int end = (int) (initAvatarPlacement.x() + horizontalWindowSize * (terrainCounter + 1));
//            terrain.createInRange(start, end);
        }
    }

    public int getCounter() {
        return counter.value();
    }


}
