package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;


public class Bomb extends Entity {
    private int time_blow = 120;
    private Explosion e;
    private boolean blow = false;
    private boolean goThrough = true;


    public Bomb(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void update() {
        animate();
        if (time_blow > 0) {
            time_blow--;
        } else {
            blow = true;
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

    public void setBlow(boolean blow) {
        this.blow = blow;
    }

    public boolean isBlow() {
        return blow;
    }

    @Override
    public boolean collide(Entity a) {
        return x + 2 >= a.getX() && x + 2 <= a.getX() + 29 && y + 2 <= a.getY() + 29 && y + 2 >= a.getY()
                || (x + 2 >= a.getX() && x + 2 <= a.getX() + 29 && y + 29 >= a.getY() && y + 29 <= a.getY() + 29)
                || (x + 29 >= a.getX() && x + 29 <= a.getX() + 29 && y + 2 >= a.getY() && y + 2 <= a.getY() + 29)
                || (x + 29 >= a.getX() && x + 29 <= a.getX() + 29 && y + 29 >= a.getY() && y + 29 <= a.getY() + 29);
    }

    public boolean canGoThrough() {
        return goThrough;
    }

    public void checkGoThrough(Entity e) {
        if (goThrough) {
            if (!this.collide(e)) {
                goThrough = false;
            }
        }
    }
}
