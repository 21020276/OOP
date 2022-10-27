package uet.oop.bomberman.entities.Player;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.Item.BombItem;
import uet.oop.bomberman.Item.RadiusItem;
import uet.oop.bomberman.Item.SpeedItem;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Frame.ExplodeSurround;
import uet.oop.bomberman.entities.Frame.Explosion;
import uet.oop.bomberman.entities.Mob.Mob;
import uet.oop.bomberman.graphics.Sprite;
import java.util.ArrayList;
import java.util.List;

public class Bomber extends Entity {
    protected Sprite sprite;
    protected boolean moveUp, moveDown, moveLeft, moveRight;
    private int timer = 40;
    private boolean stop = false;
    private boolean goThroughBomb = true;
    protected boolean _moving = true;
    protected int _animate = 0;
    protected int _direction = -1;
    protected final int MAX_ANIMATE = 7500; //save the animation status and dont let this get too big
    public static boolean isAlive = true;
    protected List<Bomb> bombList = new ArrayList<>();
    protected List<Explosion> explosionList = new ArrayList<>();
    protected static int totalBomb = 2;
    protected static int SPEED = 3;

    protected void animate() {
        if(_animate < MAX_ANIMATE) _animate++;
        else _animate = 0; //reset animation
    }

    public Bomber(int x, int y, Image img) {
        super( x, y, img);
        sprite = Sprite.player_right;
    }

    @Override
    public void render(GraphicsContext gc) {
        if (isAlive) {
            chooseSprite();
            //bombList.forEach(g -> g.render(gc));
        }
        else {
            sprite = Sprite.movingSprite(Sprite.player_dead1, Sprite.player_dead2, Sprite.player_dead3, _animate, 20);
        }
        gc.drawImage(sprite.getFxImage(), x, y);
        if (bombList.size() > 0 && bombList.get(0).isBlow()) {
            explode(bombList.get(0).getX() / 32, bombList.get(0).getY() / 32);
            if ((x + 16) / 32 == bombList.get(0).getX() / 32 && (y + 16) / 32 == bombList.get(0).getY() / 32) {
                isAlive = false;
            }
            Board.entities.remove(bombList.get(0));
            bombList.remove(0);
        }
        explosionList.forEach(g -> g.render(gc));
        if (explosionList.size() > 0 && explosionList.get(0).isStop()) {
            Board.entities.remove(explosionList.get(0));
            explosionList.remove(0);
        }
    }

    @Override
    public void update() {
        animate();
        checkItem();
        checkAlive();
        calculateMove();
        if (!isAlive) {
            if (timer > 0) {
                timer--;
            } else {
                stop = true;
            }
        }
        for (Bomb b : bombList) {
            b.checkGoThrough(this);
        }
        explosionList.forEach(Entity::update);
    }

    public boolean isMoveUp() {
        return moveUp;
    }

    public void setMoveUp(boolean moveUp) {
        this.moveUp = moveUp;
    }

    public boolean isMoveDown() {
        return moveDown;
    }

    public void setMoveDown(boolean moveDown) {
        this.moveDown = moveDown;
    }

    public boolean isMoveLeft() {
        return moveLeft;
    }

    public void setMoveLeft(boolean moveLeft) {
        this.moveLeft = moveLeft;
    }

    public boolean isMoveRight() {
        return moveRight;
    }

    public void setMoveRight(boolean moveRight) {
        this.moveRight = moveRight;
    }
    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    protected void calculateMove() {
        int xa = 0, ya = 0;
        if(isMoveUp()) ya--;
        if(isMoveDown()) ya++;
        if(isMoveLeft()) xa--;
        if(isMoveRight()) xa++;

        if(xa != 0 || ya != 0)  {
            move(xa * SPEED, ya  * SPEED);
            _moving = true;
        } else {
            _moving = false;

        }
    }

    public void move(double xa, double ya) {
        if(xa > 0) _direction = 1;
        if(xa < 0) _direction = 3;
        if(ya > 0) _direction = 2;
        if(ya < 0) _direction = 0;

        if(canMove(0, ya)) { //separate the moves for the player can slide when is colliding
            y += ya;
        }

        if(canMove(xa, 0)) {
            x += xa;
        }
    }

    public boolean canMove(double xa, double ya) {
        int moveX = 0;
        int moveY = 0;
        if (ya == 0 && xa != 0) {
            if (xa < 0) {
                moveX = (x - 1) / 32;
            } else if (xa > 0) {
                moveX = x / 32 + 1;
            }
            if (y % 32 > 32 * 2 / 3) {
                moveY = y / 32 + 1;
            } else {
                moveY = y / 32;
            }
            if (Board.getAt(moveX, moveY) == null || ((Board.getAt(moveX, moveY) instanceof Bomb) &&
                    (((Bomb) Board.getAt(moveX, moveY)).canGoThrough()))) {
                while (this.collide(Board.getAt(moveX, moveY + 1))) {
                    y--;
                }
                while (this.collide(Board.getAt(moveX, moveY - 1))) {
                    y++;
                }
                return true;
            }
        }

        if (xa == 0 && ya != 0) {
            if (ya < 0) {
                moveY = (y - 1) / 32;
            } else if (ya > 0){
                moveY = y / 32 + 1;
            }
            if (x % 32 > 32 * 2 / 3) {
                moveX = x / 32 + 1;
            } else {
                moveX = x / 32;
            }
            if (Board.getAt(moveX, moveY) == null || ((Board.getAt(moveX, moveY) instanceof Bomb) &&
                    (((Bomb) Board.getAt(moveX, moveY)).canGoThrough()))) {
                while (this.collide(Board.getAt(moveX + 1, moveY))) {
                    x--;
                }
                while (this.collide(Board.getAt(moveX - 1, moveY))) {
                    x++;
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean collide(Entity a) {
        if (a != null && !(a instanceof Mob)) {
            return x + 2 >= a.getX() && x + 2 <= a.getX() + 29 && y + 2 <= a.getY() + 29 && y + 2 >= a.getY()
                    || (x + 2 >= a.getX() && x + 2 <= a.getX() + 29 && y + 29 >= a.getY() && y + 29 <= a.getY() + 29)
                    || (x + 29 >= a.getX() && x + 29 <= a.getX() + 29 && y + 2 >= a.getY() && y + 2 <= a.getY() + 29)
                    || (x + 29 >= a.getX() && x + 29 <= a.getX() + 29 && y + 29 >= a.getY() && y + 29 <= a.getY() + 29);
        }
        return false;
    }

    public boolean isStop() {
        return stop;
    }

    protected void chooseSprite() {
        switch(_direction) {
            case 0	:
                sprite = Sprite.player_up;
                if(_moving) {
                    sprite = Sprite.movingSprite(Sprite.player_up_1, Sprite.player_up_2, _animate, 20);
                }
                break;
            case 2:
                sprite = Sprite.player_down;
                if(_moving) {
                    sprite = Sprite.movingSprite(Sprite.player_down_1, Sprite.player_down_2, _animate, 20);
                }
                break;
            case 3:
                sprite = Sprite.player_left;
                if(_moving) {
                    sprite = Sprite.movingSprite(Sprite.player_left_1, Sprite.player_left_2, _animate, 20);
                }
                break;
            default:
                sprite = Sprite.player_right;
                if(_moving) {
                    sprite = Sprite.movingSprite(Sprite.player_right_1, Sprite.player_right_2, _animate, 20);
                }
                break;
        }
    }

    public void plantbomb(int xa, int ya) {
        if (bombList.size() < totalBomb) {
            sprite = Sprite.bomb;
            Bomb b = new Bomb(xa, ya, Sprite.bomb.getFxImage());
            if (checkValidBomb(b)) {
                bombList.add(b);
                Board.entities.add(b);
            }
        }
    }

    public void explode(int xa, int ya) {
        sprite = Sprite.bomb_exploded;
        Explosion e = new Explosion(xa, ya, sprite.getFxImage());
        goThroughBomb = true;
        explosionList.add(e);
        //Board.entities.add(e);
        e.setExplodeSurrounds();
    }

    public boolean checkValidBomb(Bomb bomb) {
        for (Bomb _bomb : bombList) {
            if (_bomb.collide(bomb)) {
                return false;
            }
        }
        return true;
    }

    public void updateSpeed() {
        SPEED++;
    }

    public void updateBomb() {
        totalBomb++;
    }

    public void updateRadius() {
        Explosion.upRadius();
    }

    public void checkItem() {
        for (int i = 0; i < BombermanGame.itemList.size(); i++) {
            if (this.collide(BombermanGame.itemList.get(i))) {
                if (BombermanGame.itemList.get(i) instanceof SpeedItem) {
                    updateSpeed();
                }
                if (BombermanGame.itemList.get(i) instanceof RadiusItem) {
                    updateRadius();
                }
                if (BombermanGame.itemList.get(i) instanceof BombItem) {
                    updateBomb();
                }
                BombermanGame.itemList.remove(i);
            }
        }
    }

    public void checkAlive() {
        Entity entity = Board.getAt((x + 16) / 32, (y + 16) / 32);
        if (this.collide(entity)) {
            if (entity instanceof ExplodeSurround || entity instanceof Explosion
                || entity instanceof Mob) {
                isAlive = false;
            }
        }
    }
}

