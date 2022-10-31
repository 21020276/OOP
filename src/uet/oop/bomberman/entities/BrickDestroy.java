package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;

public class BrickDestroy extends Entity {
    private int timer = 20;
    private boolean stop = false;
    public BrickDestroy(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void render(GraphicsContext gc) {
        img = Sprite.movingSprite(Sprite.brick_exploded, Sprite.brick_exploded1, Sprite.grass, _animate, 40).getFxImage();
        gc.drawImage(img, x, y);
    }

    @Override
    public void update() {
        animate();
        if (timer > 0) {
            timer--;
        } else {
            stop = true;
        }
    }

    @Override
    public boolean collide(Entity e) {
        return false;
    }

    public boolean isStop() {
        return stop;
    }

    public void setStop(boolean stop) {
        this.stop = stop;
    }
}
