package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;


public class Explosion extends Entity {
    private int timer = 40;
    private boolean stop = false;
    private int radius = 1;
    public Explosion(int x, int y, Image img) {
        super(x, y, img);
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
    public void render(GraphicsContext gc) {
        sprite = Sprite.movingSprite(Sprite.bomb_exploded, Sprite.bomb_exploded1, Sprite.bomb_exploded2, _animate, 40);
        img = sprite.getFxImage();
        gc.drawImage(img, x, y);

    }

    public boolean isStop() {
        return stop;
    }


}
