package uet.oop.bomberman.entities.Mob;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import uet.oop.bomberman.BombermanGame;
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
        calculateMove();
        collideWithPlayer();
        Random random = new Random();
        int direction = random.nextInt(4);
        if (waitTime > 0) waitTime--;

        if (waitTime == 0)
        switch (direction) {
            case 0:
                waitTime = 30;
                setMoveDown(true);
                setMoveUp(false);
                setMoveLeft(false);
                setMoveRight(false);
                break;
            case 1:
                waitTime = 30;
                setMoveDown(false);
                setMoveUp(true);
                setMoveLeft(false);
                setMoveRight(false);
                break;
            case 2:
                waitTime = 30;
                setMoveDown(false);
                setMoveUp(false);
                setMoveLeft(true);
                setMoveRight(false);
                break;
            case 3:
                waitTime = 30;
                setMoveDown(false);
                setMoveUp(false);
                setMoveLeft(false);
                setMoveRight(true);
                break;
        }

    }

    @Override
    public void collideWithPlayer() {
        if (this.collide(BombermanGame.bomberman)) {
            Bomber.isAlive = false;
        }
    }

    protected void chooseSprite() {
        switch(_direction) {
            case 3:
                sprite = Sprite.balloom_left1;
                if(_moving) {
                    sprite = Sprite.movingSprite(Sprite.balloom_left2, Sprite.balloom_left3, _animate, 20);
                }
                break;
            case 1:
                sprite = Sprite.balloom_right1;
                if(_moving) {
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