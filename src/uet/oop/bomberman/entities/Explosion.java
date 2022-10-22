package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;
import java.util.List;


public class Explosion extends Entity {
    private int timer = 20;
    private static final int TIME_EXPLODING = 20;
    private boolean stop = false;
    private int radius = 3;
    private List<ExplodeSurround> explodeSurroundList = new ArrayList<>();
    private boolean[] check = new boolean[4];
    public Explosion(int x, int y, Image img) {
        super(x, y, img);
    }
    private boolean destroy = false;

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
        sprite = Sprite.movingSprite(Sprite.bomb_exploded, Sprite.bomb_exploded1, Sprite.bomb_exploded2, _animate, TIME_EXPLODING);
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
        boolean checkLast = false;
        for (int i = 0 ; i < 4; i++) {
            check[i] = false;
        }
        for (int i = 0; i < radius * 4; i++) {
            if (i >= radius * 4 - 4) {
                checkLast = true;
            }
            if (!check[i % 4]) {
                explodeSurround = chooseSprite(i % 4, checkLast, x / 32, y / 32, i / 4 + 1);
                if (!explodeSurround.collide(chooseEntity(i % 4, x / 32, y / 32, i / 4 + 1))) {
                    explodeSurroundList.add(explodeSurround);
                } else {
                    check[i % 4] = true;
                    destroy(chooseEntity(i % 4, x / 32, y / 32, i / 4 + 1));
                }
            }
        }
    }

    public void destroy(Entity e) {
        if (e instanceof Brick) {
            Brick b = (Brick) e;
            b.setDestroy(true);
            Board.entities.remove(b);
        }
    }

    public ExplodeSurround chooseSprite(int direction, boolean checkLast, int xa, int ya, int length) {
        if (checkLast) {
            if (direction == 0) {
                return new ExplodeSurround(xa, ya - length, Sprite.movingSprite(Sprite.explosion_vertical_top_last,
                        Sprite.explosion_vertical_top_last1, Sprite.explosion_vertical_top_last2, _animate, TIME_EXPLODING).getFxImage());
            }
            if (direction == 1) {
                return new ExplodeSurround(xa + length, ya, Sprite.movingSprite(Sprite.explosion_horizontal_right_last,
                        Sprite.explosion_horizontal_right_last1, Sprite.explosion_horizontal_right_last2, _animate, TIME_EXPLODING).getFxImage());
            }
            if (direction == 2) {
                return new ExplodeSurround(xa, ya + length, Sprite.movingSprite(Sprite.explosion_vertical_down_last, Sprite.explosion_vertical_down_last1,
                        Sprite.explosion_vertical_down_last2, _animate, TIME_EXPLODING).getFxImage());
            }
            if (direction == 3) {
                return new ExplodeSurround(xa - length, ya, Sprite.movingSprite(Sprite.explosion_horizontal_left_last, Sprite.explosion_horizontal_left_last1,
                        Sprite.explosion_horizontal_left_last2, _animate, TIME_EXPLODING).getFxImage());
            }
        } else {
            if (direction == 0) {
                return new ExplodeSurround(xa, ya - length, Sprite.movingSprite(Sprite.explosion_vertical,
                        Sprite.explosion_vertical1, Sprite.explosion_vertical2, _animate, TIME_EXPLODING).getFxImage());
            }
            if (direction == 1) {
                return new ExplodeSurround(xa + length, ya, Sprite.movingSprite(Sprite.explosion_horizontal,
                        Sprite.explosion_horizontal1, Sprite.explosion_horizontal2, _animate, TIME_EXPLODING).getFxImage());
            }
            if (direction == 2) {
                return new ExplodeSurround(xa, ya + length, Sprite.movingSprite(Sprite.explosion_vertical, Sprite.explosion_vertical1,
                        Sprite.explosion_vertical2, _animate, TIME_EXPLODING).getFxImage());
            }
            if (direction == 3) {
                return new ExplodeSurround(xa - length, ya, Sprite.movingSprite(Sprite.explosion_horizontal, Sprite.explosion_horizontal1,
                        Sprite.explosion_horizontal2, _animate, TIME_EXPLODING).getFxImage());
            }
        }
        return null;
    }

    public Entity chooseEntity(int direction, int xa, int ya, int length) {
        if (direction == 0) {
            return Board.getAt(xa, ya - length);
        } else if (direction == 1) {
            return Board.getAt(xa + length, ya);
        } else if (direction == 2) {
            return Board.getAt(xa, ya + length);
        } else {
            return Board.getAt(xa - length, ya);
        }
    }

}
