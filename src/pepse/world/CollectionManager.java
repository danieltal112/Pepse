package pepse.world;

import danogl.collisions.GameObjectCollection;
import danogl.util.Vector2;

public class CollectionManager {
    private GameObjectCollection collection;
    private int horizontalWindowSize;
    private Terrain terrain;
    private Avatar avatar;

    public CollectionManager(float windowDimensionsX, Terrain terrain, Avatar avatar) {
        this.horizontalWindowSize = (int) windowDimensionsX;
        this.terrain = terrain;
        this.avatar = avatar;
    }

    public void updateTerrain() {
        addGround();
    }

    private void addGround() {
        boolean orientation = avatar.getAvatarOrientatinon();
        int halfScreenSize = horizontalWindowSize / 2;
        int avatarLocX = (int) avatar.getCenter().x();
        if (avatarLocX % halfScreenSize != 0)
            return;
        int start, end;
        if (orientation) {
            start = avatarLocX + horizontalWindowSize;
            end = avatarLocX + (2 * horizontalWindowSize);
        } else {
            start = avatarLocX - horizontalWindowSize;
            end = avatarLocX - (2 * horizontalWindowSize);
        }
        terrain.createInRange(start, end);
    }


}
