package uet.oop.bomberman.Item;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;

public class BombItem extends Item {

    public BombItem(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(img, x, y);
    }

    @Override
    public void update() {}

    @Override
    public boolean collide(Entity e) {
        return false;
    }
}
