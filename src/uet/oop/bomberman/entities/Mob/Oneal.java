package uet.oop.bomberman.entities.Mob;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;

import java.util.Random;
import java.util.Stack;

public class Oneal extends Mob {

    public Oneal(int x, int y, Image img) {
        super(x, y, img);
        sprite = Sprite.oneal_right1;
    }

    @Override
    public void update() {
        animate();
        collideWithPlayer();


        //choose direction with super basic if-else
            int direction = 0;

            if (x/32 < BombermanGame._bomber.getX() /32)
                direction = RIGHT;
            if (x/32 > BombermanGame._bomber.getX() /32)
                direction = LEFT;
            if (y/32 < BombermanGame._bomber.getY() /32)
                direction = DOWN;
            if (y/32 > BombermanGame._bomber.getY() /32)
                direction = UP;

            if (!canMove(direction)) {
                Random random = new Random();
                direction = random.nextInt(4);
                System.out.println("random" + direction);
            }
            if (canMove(direction)) {
                System.out.println("can move, direction: " + direction);
            }

        switch (direction) {
            case DOWN:
                move(DOWN);
                break;
            case UP:
                move(UP);
                break;
            case LEFT:
                move(LEFT);
                break;
            case RIGHT:
                move(RIGHT);
                break;
        }
    }




    @Override
    protected void chooseSprite() {
        switch(direction_) {
            case LEFT:
                sprite = Sprite.oneal_left1;
                if(nowMove) {
                    sprite = Sprite.movingSprite(Sprite.oneal_left1,Sprite.oneal_left2, Sprite.oneal_left3, _animate, 30);
                }
                break;
            case RIGHT:
                sprite = Sprite.oneal_right1;
                if(nowMove) {
                    sprite = Sprite.movingSprite(Sprite.oneal_right1, Sprite.oneal_right2, Sprite.oneal_right3, _animate, 30);
                }
                break;
            default:
                if (nowMove) {
                    sprite = Sprite.movingSprite(Sprite.oneal_right1, Sprite.oneal_right2, Sprite.oneal_right3, _animate, 30);
                }
        }
    }


    @Override
    public void render(GraphicsContext gc) {
        chooseSprite();
        gc.drawImage(sprite.getFxImage(), x, y);
    }
}