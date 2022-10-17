package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;
import java.util.List;

public class Bomber extends Entity {
    protected Sprite sprite;
    protected boolean moveUp, moveDown, moveLeft, moveRight;
    protected int _direction = -1;
    protected boolean _moving = false;
    protected int _animate = 0;
    protected final int MAX_ANIMATE = 7500; //save the animation status and dont let this get too big
    protected boolean isAlive = true;
    protected List<Bomb> bombList = new ArrayList<>();
    protected List<Explosion> explosionList = new ArrayList<>();
    protected int totalBomb = 1;


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
            gc.drawImage(sprite.getFxImage(), x, y);
            bombList.forEach(g -> g.render(gc));
            if (bombList.size() > 0 && bombList.get(0).isBlow()) {
                explode(bombList.get(0).getX() / 32, bombList.get(0).getY() / 32);
                bombList.remove(0);

            }
            explosionList.forEach(g -> g.render(gc));
            if (explosionList.size() > 0 && explosionList.get(0).isStop()) {
                explosionList.remove(0);
            }
        }
        else {
            sprite = Sprite.player_dead1;
        }
    }

    @Override
    public void update() {
        animate();
        calculateMove();
        bombList.forEach(Bomb::update);
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

    public int getX() {
        return x;
    }

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
            move(xa * 1, ya * 1);
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

    public boolean canMove(double x, double y) {
        /*
        for (int c = 0; c < 4; c++) { //colision detection for each corner of the player
            double xt = ((_x + x) + c % 2 * 11) / BombermanGame.TILES_SIZE; //divide with tiles size to pass to tile coordinate
            double yt = ((_y + y) + c / 2 * 12 - 13) / BombermanGame.TILES_SIZE; //these values are the best from multiple tests

            Entity a = _board.getEntity(xt, yt, this);

            if(!a.collide(this))
                return false;
        }
        */
        return true;
    }

    private void chooseSprite() {
        switch(_direction) {
            case 0	:
                sprite = Sprite.player_up;
                if(_moving) {
                    sprite = Sprite.movingSprite(Sprite.player_up_1, Sprite.player_up_2, _animate, 20);
                }
                break;
            case 1:
                sprite = Sprite.player_right;
                if(_moving) {
                    sprite = Sprite.movingSprite(Sprite.player_right_1, Sprite.player_right_2, _animate, 20);
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
            bombList.add(b);
        }
    }

    public void explode(int xa, int ya) {
        sprite = Sprite.bomb_exploded;
        Explosion e = new Explosion(xa, ya, sprite.getFxImage());
        explosionList.add(e);

    }
}
