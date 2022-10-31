package uet.oop.bomberman.Item;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;

public class Item extends Entity {
    public Item(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void update() {}

    @Override
    public boolean collide(Entity e) {
        return false;
    }
}
