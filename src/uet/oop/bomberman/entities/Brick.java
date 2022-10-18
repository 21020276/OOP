package uet.oop.bomberman.entities;

import javafx.scene.image.Image;

public class Brick extends Entity {
    public Brick(int x, int y, Image img) {
        super( x, y, img);
    }

    @Override
    public void update() {

    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public boolean collide(Entity e) {
        return false;
    }
}
