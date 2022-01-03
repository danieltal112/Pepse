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

    public int getCounter() {
        return counter.value();
    }




}
