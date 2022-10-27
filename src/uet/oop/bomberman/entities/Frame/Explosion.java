package uet.oop.bomberman.entities.Frame;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.Sound.Sound;
import uet.oop.bomberman.entities.Mob.Mob;
import uet.oop.bomberman.entities.Player.Bomb;
import uet.oop.bomberman.entities.Brick;
import uet.oop.bomberman.entities.BrickDestroy;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Mob.Balloon;
import uet.oop.bomberman.entities.Mob.Oneal;
import uet.oop.bomberman.entities.Player.Bomber;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;
import java.util.List;


public class Explosion extends Entity {
    private int timer = 20;
    private static final int TIME_EXPLODING = 20;
    private boolean stop = false;
    protected static int RADIUS = 1;
    private List<ExplodeSurround> explodeSurroundList = new ArrayList<>();
    private boolean[] check = new boolean[4];
    private boolean destroy = false;
    private Entity removeEntity;
    private List<BrickDestroy> b = new ArrayList<>();
    private List<Mob> mob = new ArrayList<>();

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
        b.forEach(Entity::update);
        mob.forEach(Entity::update);
    }

    @Override
    public void render(GraphicsContext gc) {
        sprite = Sprite.movingSprite(Sprite.bomb_exploded, Sprite.bomb_exploded1, Sprite.bomb_exploded2, _animate, TIME_EXPLODING);
        img = sprite.getFxImage();
        gc.drawImage(img, x, y);
        explodeSurroundList.forEach(g -> g.render(gc));
        if (explodeSurroundList.size() > 0 && explodeSurroundList.get(0).isStop()) {
            Board.entities.remove(explodeSurroundList.get(0));
            explodeSurroundList.remove(0);
        }
        b.forEach(g -> g.render(gc));
        if (b.size() > 0 && b.get(0).isStop()) {
            b.remove(0);
        }
        mob.forEach(g -> g.render(gc));
        System.out.println(mob.size());
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
        for (int i = 0; i < RADIUS * 4; i++) {
            if (i >= RADIUS * 4 - 4) {
                checkLast = true;
            }
            if (!check[i % 4]) {
                explodeSurround = chooseSprite(i % 4, checkLast, x / 32, y / 32, i / 4 + 1);
                if (!explodeSurround.collide(chooseEntity(i % 4, x / 32, y / 32, i / 4 + 1))) {
                    explodeSurroundList.add(explodeSurround);
                    Board.entities.add(explodeSurround);
                } else {
                    check[i % 4] = true;
                    destroy(chooseEntity(i % 4, x / 32, y / 32, i / 4 + 1));
                }
            }
        }
    }

    public void destroy(Entity e) {
        if (e instanceof Brick) {
             b.add(new BrickDestroy(e.getX() / 32, e.getY() / 32, Sprite.movingSprite(Sprite.brick_exploded,
                    Sprite.brick_exploded1, Sprite.grass, _animate, 40).getFxImage()));
             Board.entities.remove(e);
        }
        if (e instanceof Balloon) {
            e.img = Sprite.balloom_dead.getFxImage();
            Bomber.numberOfMobKilled++;
            mob.add(new Balloon(e.getX() / 32, e.getY() / 32, Sprite.movingSprite(Sprite.mob_dead1,
                    Sprite.mob_dead2, Sprite.mob_dead3, _animate, 40).getFxImage()));
            Board.entities.remove(e);

        }
        if (e instanceof Oneal) {
            e.img = Sprite.oneal_dead.getFxImage();
            Bomber.numberOfMobKilled++;
            mob.add(new Oneal(e.getX() / 32, e.getY() / 32, Sprite.movingSprite(Sprite.mob_dead1,
                    Sprite.mob_dead2, Sprite.mob_dead3, _animate, 40).getFxImage()));
            Board.entities.remove(e);
        }
        if (e instanceof Bomb) {
            ((Bomb) e).setBlow(true);
        }
    }

    public ExplodeSurround chooseSprite(int direction, boolean checkLast, int xa, int ya, int length) {
        if (checkLast) {
            new Sound("sound/bomb_explosion.wav", "explosion");
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

    public static void upRadius() {
        RADIUS++;
    }
}
