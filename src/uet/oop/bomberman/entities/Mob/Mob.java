package uet.oop.bomberman.entities.Mob;

import javafx.scene.image.Image;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Brick;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Grass;
import uet.oop.bomberman.entities.Player.Bomb;
import uet.oop.bomberman.entities.Player.Bomber;
import uet.oop.bomberman.entities.Wall;

public abstract class Mob extends Entity {
    public Mob(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }
    protected boolean isAlive = true;

    public void setAlive(boolean alive) {
        this.isAlive = alive;
    }
    public static final int UP = 0;
    public static final int DOWN = 1;
    public static final int RIGHT = 2;
    public static final int LEFT = 3;

    protected int direction_ = -1;
    protected boolean nowMove = false;
    protected int move = 0;
    public void moveUp() {
        y--;
        move--;
    }

    public void moveDown() {
        y++;
        move--;
    }
    public void moveRight() {
        x++;
        move--;
    }
    public void moveLeft() {
        x--;
        move--;
    }

    protected boolean canMove(int direction) {
        switch (direction) {
            case UP:
                if (!(Board.getAt(getX()/32, (getY() - 32)/32) instanceof Brick
                        || Board.getAt(getX()/32, (getY() - 32)/32) instanceof Wall
                            || Board.getAt(getX()/32, (getY() - 32)/32) instanceof Bomb)) {
                    return true;
                }
                break;
            case DOWN:
                    if (!(Board.getAt(getX()/32, (getY() + 32)/32) instanceof Brick
                            || Board.getAt(getX()/32, (getY() + 32)/32) instanceof Wall
                                || Board.getAt(getX()/32, (getY() + 32)/32) instanceof Bomb)) {
                        return true;
                    }
                    break;
            case RIGHT:
                    if (!(Board.getAt((getX() + 32)/32, getY()/32) instanceof Brick
                            || Board.getAt((getX() + 32)/32, getY()/32) instanceof Wall
                                || Board.getAt((getX() + 32)/32, getY()/32) instanceof Bomb)) {
                        return true;
                    }
                    break;
            case LEFT:
                    if (!(Board.getAt((getX() - 32)/32, getY()/32) instanceof Brick
                            || Board.getAt((getX() - 32)/32, getY()/32) instanceof Wall
                                || Board.getAt((getX() - 32)/32, getY()/32) instanceof Bomb)) {
                        return true;
                    }
                    break;
        }
        return false;
    }
    public void move(int direction) {
        switch (direction) {
            case UP:
                if (!nowMove)
                    if (canMove(UP)) {
                        direction_ = UP;
                        move = 32;
                        moveUp();
                        nowMove = true;
                    }
                break;
            case DOWN:
                if (!nowMove)
                    if (canMove(DOWN)) {
                        direction_ = DOWN;
                        move = 32;
                        moveDown();
                        nowMove = true;
                    }
                break;
            case RIGHT:
                if (!nowMove)
                    if (canMove(RIGHT)) {
                        direction_ = RIGHT;
                        move = 32;
                        moveRight();
                        nowMove = true;
                    }
                break;
            case LEFT:
                if (!nowMove) {
                    if (canMove(LEFT)) {
                        direction_ = LEFT;
                        move = 32;
                        moveLeft();
                        nowMove = true;
                    }
                }
                break;
        }
        // maintain move
        if (nowMove) {
            if (direction_ == UP) {
                moveUp();
            }
            if (direction_ == RIGHT) {
                moveRight();
            }
            if (direction_ == DOWN) {
                moveDown();
            }
            if (direction_ == LEFT) {
                moveLeft();
            }
        }
        //update NowMove
        if (move == 0) {
            nowMove = false;
        }
    }

    protected abstract void chooseSprite();
    protected void collideWithPlayer() {
        if (this.collide(BombermanGame.bomberman)) {
            Bomber.isAlive = false;
        }
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
}



