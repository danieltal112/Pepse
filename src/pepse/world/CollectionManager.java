package pepse.world;

import danogl.util.Counter;

public class CollectionManager {
    private final int chunkSize;
    private final Terrain terrain;
    private final Avatar avatar;
    private final Counter chunkCounter = new Counter(1);

    public CollectionManager(float windowDimensionsX, Terrain terrain, Avatar avatar) {
        this.chunkSize = (int) (windowDimensionsX / 2);
        this.terrain = terrain;
        this.avatar = avatar;
    }

    public void updateTerrain() {
        addGround();
    }

    private void addGround() {
        int avatarLocX = (int) avatar.getCenter().x();
        int start, end;
        int currentChunk = (avatarLocX / chunkSize);
        if (currentChunk > chunkCounter.value()) {
            chunkCounter.increment();
            start = (avatarLocX / chunkSize) * chunkSize + chunkSize;
            end = (avatarLocX / chunkSize) * chunkSize + (2 * chunkSize);
            terrain.createInRange(start, end);
        }
        if (currentChunk < chunkCounter.value()) {
            chunkCounter.decrement();
            start = avatarLocX - chunkSize;
            end = avatarLocX - (2 * chunkSize);
            terrain.createInRange(start, end);
        }
    }
}



