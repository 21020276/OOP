package uet.oop.bomberman.entities;

import javafx.scene.image.Image;

public class Item extends Entity {
    public Item(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void update() {

    }

    @Override
    public int getX() {
        return 0;
    }

    @Override
    public int getY() {
        return 0;
    }

    @Override
    public boolean collide(Entity e) {
        return false;
    }
}
