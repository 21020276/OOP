package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;


public class Bomb extends Entity {
    private int time_blow = 120;
    private Explosion e;
    private boolean blow = false;


    public Bomb(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void update() {
        animate();
        if (time_blow > 0) {
            time_blow--;
        } else {
            if (!blow) {
                blow();
            }
            else {
                time_blow = 120;
            }

        }
    }

    @Override
    public void render(GraphicsContext gc) {
        sprite = Sprite.movingSprite(Sprite.bomb, Sprite.bomb_1, Sprite.bomb_2, _animate, 20);
        img = sprite.getFxImage();
        gc.drawImage(img, x, y);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void blow() {
        blow = true;
    }

    public boolean isBlow() {
        return blow;
    }

    @Override
    public boolean collide(Entity e) {
        return this.y == e.getY() && this.x == e.getX();
    }
}
