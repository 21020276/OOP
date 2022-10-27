package uet.oop.bomberman.entities.Mob;

import javafx.scene.image.Image;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Player.Bomber;

public abstract class Mob extends Entity {
    public Mob(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    protected int _direction = -1;
    protected int waitTime = 0;

    protected boolean isAlive = true;

    protected abstract void chooseSprite();

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    protected boolean _moving = false;
    protected boolean moveUp, moveDown, moveLeft, moveRight;
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
    public void update() {
        collideWithPlayer();
    }

    protected void calculateMove() {
        int xa = 0, ya = 0;
        if(isMoveUp()) ya--;
        if(isMoveDown()) ya++;
        if(isMoveLeft()) xa--;
        if(isMoveRight()) xa++;

        if(xa != 0 || ya != 0)  {
            move(xa , ya );
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
            y++;
        }

        if(canMove(xa, 0)) {

            x+=1;
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
            if (Board.getAt(moveX, moveY) == null) {
                if (this.collide(Board.getAt(moveX, moveY + 1))) {
                    y-=31;
                }
                if (this.collide(Board.getAt(moveX, moveY - 1))) {
                    y+=31;
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
            if (Board.getAt(moveX, moveY) == null) {
                if (this.collide(Board.getAt(moveX + 1, moveY))) {
                    x-=31;
                }
                if (this.collide(Board.getAt(moveX - 1, moveY))) {
                    x+=31;
                }
            }
        }
        return false;
    }

    @Override
    public boolean collide(Entity a) {
        if (a != null) {
            return x + 1 >= a.getX() && x + 1 <= a.getX() + 30 && y + 1 <= a.getY() + 30 && y + 1 >= a.getY()
                    || (x + 1 >= a.getX() && x + 1 <= a.getX() + 30 && y + 30 >= a.getY() && y + 30 <= a.getY() + 30)
                    || (x + 30 >= a.getX() && x + 30 <= a.getX() + 30 && y + 1 >= a.getY() && y + 1 <= a.getY() + 30)
                    || (x + 30 >= a.getX() && x + 30 <= a.getX() + 30 && y + 30 >= a.getY() && y + 30 <= a.getY() + 30);
        }
        return false;
    }

    public abstract void collideWithPlayer();
}

