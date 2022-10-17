package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;

public class Balloon extends Entity {
    private boolean isMove = false;
    public Balloon(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void update() {
        sprite = Sprite.movingSprite(Sprite.balloom_left1, Sprite.balloom_left2, Sprite.balloom_left3, _animate, 20);
        animate();

    }

    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(img, x, y);
    }
}