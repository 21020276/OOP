package uet.oop.bomberman.entities.Mob;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Player.Bomber;
import uet.oop.bomberman.graphics.Sprite;

import java.util.Random;

public class Balloon extends Mob {
    public Balloon(int x, int y, Image img) {
        super(x, y, img);
        sprite = Sprite.balloom_right1;
    }
    @Override
    public void update() {
        sprite = Sprite.movingSprite(Sprite.balloom_left1, Sprite.balloom_left2, Sprite.balloom_left3, _animate, 20);
        animate();
        collideWithPlayer();
        Random random = new Random();
        int direction = random.nextInt(4);

        switch (direction) {
            case 0:
                move(DOWN);
                break;
            case 1:
                move(UP);
                break;
            case 2:
                move(LEFT);
                break;
            case 3:
                move(RIGHT);
                break;
        }

    }

    protected void chooseSprite() {
        switch(direction_) {
            case 3:
                sprite = Sprite.balloom_left1;
                if(nowMove) {
                    sprite = Sprite.movingSprite(Sprite.balloom_left2, Sprite.balloom_left3, _animate, 20);
                }
                break;
            case 1:
                sprite = Sprite.balloom_right1;
                if(nowMove) {
                    sprite = Sprite.movingSprite(Sprite.balloom_right1, Sprite.balloom_right2, _animate, 20);
                }
                break;
        }
    }


    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(img, x, y);
    }


}