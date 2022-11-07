package uet.oop.bomberman.entities.Mob;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Player.Bomber;
import uet.oop.bomberman.graphics.Sprite;

import java.util.Random;

public class Balloon extends Mob {
    public Balloon(int x, int y, Image img) {
        super(x, y, img);
        sprite = Sprite.balloom_left1;
    }
    @Override
    public void update() {
        //sprite = Sprite.movingSprite(Sprite.balloom_left1, Sprite.balloom_left2, Sprite.balloom_left3, _animate, 20);
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
            case LEFT:
                sprite = Sprite.balloom_left1;
                if(nowMove) {
                    sprite = Sprite.movingSprite(Sprite.balloom_left2, Sprite.balloom_left3, _animate, 20);
                }
                break;
            case RIGHT:
                sprite = Sprite.balloom_right1;
                if(nowMove) {
                    sprite = Sprite.movingSprite(Sprite.balloom_right1, Sprite.balloom_right2, _animate, 20);
                }
                break;
        }
    }

    private int count = 60;
    @Override
    public void render(GraphicsContext gc) {
        if (isAlive) {
            chooseSprite();
            gc.drawImage(sprite.getFxImage(), x, y);
        } else {
            if (count == 0) {
                Board.entities.remove(this);
            }
            count--;
            gc.drawImage(sprite.getFxImage(), x, y);
            if (count < 41) {
                sprite = Sprite.movingSprite(Sprite.mob_dead1,Sprite.mob_dead2,Sprite.mob_dead3,_animate,40);
            }
        }
    }
}