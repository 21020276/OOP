package uet.oop.bomberman.entities.Mob;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Player.Bomber;
import uet.oop.bomberman.graphics.Sprite;

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
        int direction = -1;
        if (x/32 < BombermanGame._bomber.getX() /32)
            direction = 3;
        if (x/32 > BombermanGame._bomber.getX() /32)
            direction = 2;
        if (y/32 < BombermanGame._bomber.getY() /32)
            direction = 0;
        if (y/32 > BombermanGame._bomber.getY() /32)
            direction = 1;


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

    @Override
    protected void chooseSprite() {
        switch(direction_) {
            case 3:
                sprite = Sprite.oneal_left1;
                if(nowMove) {
                    sprite = Sprite.movingSprite(Sprite.oneal_left2, Sprite.oneal_left3, _animate, 20);
                }
                break;
            case 1:
                sprite = Sprite.oneal_right1;
                if(nowMove) {
                    sprite = Sprite.movingSprite(Sprite.oneal_right2, Sprite.oneal_right3, _animate, 20);
                }
                break;
        }
    }


    @Override
    public void render(GraphicsContext gc) {
        gc.drawImage(img, x, y);
    }
}