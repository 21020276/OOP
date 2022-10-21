package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;
import java.util.List;


public class Explosion extends Entity {
    private int timer = 40;
    private boolean stop = false;
    private int radius = 1;
    private List<ExplodeSurround> explodeSurroundList = new ArrayList<>();
    private boolean[] check = new boolean[4];
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
        explodeSurroundList.forEach(Entity::update);
    }

    @Override
    public void render(GraphicsContext gc) {
        sprite = Sprite.movingSprite(Sprite.bomb_exploded, Sprite.bomb_exploded1, Sprite.bomb_exploded2, _animate, 40);
        img = sprite.getFxImage();
        gc.drawImage(img, x, y);
        explodeSurroundList.forEach(g -> g.render(gc));

    }

    public boolean isStop() {
        return stop;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public boolean collide(Entity e) {
        return false;
    }

    public void setExplodeSurrounds() {
        ExplodeSurround explodeSurround;
        for (int i = 0 ; i < 4; i++) {
            check[i] = false;
        }
        System.out.println(x + "   " + y);
        for (int i = 0; i < radius * 4; i++) {
            switch (i % 4) {
                case 0 :
                    if (check[0]) {
                        break;
                    }
                    explodeSurround = new ExplodeSurround(x / 32, y / 32 - (i / 4 + 1), Sprite.movingSprite(Sprite.explosion_vertical_top_last,
                            Sprite.explosion_vertical_top_last1, Sprite.explosion_vertical_top_last2, _animate, 40).getFxImage());
                    if (!explodeSurround.collide(Board.getAt(x / 32, y / 32 - (i / 4 + 1)))) {
                        explodeSurroundList.add(explodeSurround);
                    } else {
                        check[0] = true;
                    }
                    break;
                case 1 :
                    if (check[1]) {
                        break;
                    } else {
                        check[1] = true;
                    }
                    explodeSurround = new ExplodeSurround(x / 32 + (i / 4 + 1), y / 32, Sprite.movingSprite(Sprite.explosion_horizontal_right_last,
                            Sprite.explosion_horizontal_right_last1, Sprite.explosion_horizontal_right_last2, _animate, 40).getFxImage());
                    if (!explodeSurround.collide(Board.getAt(x / 32 + (i / 4 + 1), y / 32))) {
                        explodeSurroundList.add(explodeSurround);
                    }
                    break;
                case 2 :
                    if (check[2]) {
                        break;
                    } else {
                        check[2] = true;
                    }
                    explodeSurround = new ExplodeSurround(x / 32, y / 32 + (i / 4 + 1), Sprite.movingSprite(Sprite.explosion_vertical_down_last, Sprite.explosion_vertical_down_last1,
                            Sprite.explosion_vertical_down_last2, _animate, 40).getFxImage());
                    if (!explodeSurround.collide(Board.getAt(x / 32, y / 32 + (i / 4 + 1)))) {
                        explodeSurroundList.add(explodeSurround);
                    }
                    break;
                case 3 :
                    if (check[3]) {
                        break;
                    } else {
                        check[3] = true;
                    }
                    explodeSurround = new ExplodeSurround(x / 32 - (i / 4 + 1), y / 32, Sprite.movingSprite(Sprite.explosion_horizontal_left_last, Sprite.explosion_horizontal_left_last1,
                            Sprite.explosion_horizontal_left_last2, _animate, 40).getFxImage());
                    if (!explodeSurround.collide(Board.getAt(x / 32 - (i / 4 + 1), y / 32))) {
                        explodeSurroundList.add(explodeSurround);
                    }
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + i % 4);
            }
        }

    }
}
