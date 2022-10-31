package uet.oop.bomberman.entities.Frame;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

public class ExplodeSurround extends Entity {
    private int timer = 20;
    private boolean stop = false;

    public ExplodeSurround(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void render(GraphicsContext gc) {
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

    public boolean isStop() {
        return stop;
    }

    @Override
    public boolean collide(Entity e) {
        if (e != null) {
            return x / 32 == e.getX() / 32 && y / 32 == e.getY() / 32;
        }
        return false;
    }
}
