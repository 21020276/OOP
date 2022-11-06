package uet.oop.bomberman.entities.Mob;

import javafx.scene.image.Image;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Brick;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Grass;
import uet.oop.bomberman.entities.Player.Bomber;
import uet.oop.bomberman.entities.Wall;

public abstract class Mob extends Entity {
    public Mob(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }
    protected boolean isAlive = true;
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

    public void move(int direction) {
        // choose type move
        System.out.println(direction);
        switch (direction) {
            case UP:
                if (!nowMove)
                    if (Board.getAt(getX()/32, (getY() - 32)/32) instanceof Brick
                            || Board.getAt(getX()/32, (getY() - 32)/32) instanceof Wall)
                        System.out.println("cannot move up");
                if (!nowMove)
                    if (!(Board.getAt(getX()/32, (getY() - 32)/32) instanceof Brick
                            || Board.getAt(getX()/32, (getY() - 32)/32) instanceof Wall)) {
                        //test condition
                        int k = getX()/32;
                        int k_ = getY()/32;
                        if (getX()-k*32!=0 || getY()-k_*32!=0)
                            System.out.println("x, y: " + getX() + " " + getY());
                        move = 32;
                        moveUp();
                        System.out.println("moveUP");
                        nowMove = true;
                        direction_ = UP;
                    }
                break;
            case DOWN:
                if (!nowMove)
                    if (Board.getAt(getX()/32, (getY() + 32)/32) instanceof Brick
                            || Board.getAt(getX()/32, (getY() + 32)/32) instanceof Wall)
                        System.out.println("cannot move down");
                if (!nowMove)
                    if (!(Board.getAt(getX()/32, (getY() + 32)/32) instanceof Brick
                            || Board.getAt(getX()/32, (getY() + 32)/32) instanceof Wall)) {
                        //test condition
                        int k = getX()/32;
                        int k_ = getY()/32;
                        if (getX()-k*32!=0 || getY()-k_*32!=0)
                            System.out.println("x, y: " + getX() + " " + getY());

                        move = 32;
                        moveDown();
                        System.out.println("moveDOWN");
                        nowMove = true;
                        direction_ = DOWN;
                    }
                break;
            case RIGHT:
                if (!nowMove)
                    if (Board.getAt((getX() + 32)/32, getY()/32) instanceof Brick
                            || Board.getAt((getX() + 32)/32, getY()/32) instanceof Wall)
                        System.out.println("cannot move right");
                if (!nowMove)
                    if (!(Board.getAt((getX() + 32)/32, getY()/32) instanceof Brick
                            || Board.getAt((getX() + 32)/32, getY()/32) instanceof Wall)) {
                        //test condition
                        int k = getX()/32;
                        int k_ = getY()/32;
                        if (getX()-k*32!=0 || getY()-k_*32!=0)
                            System.out.println("x, y: " + getX() + " " + getY());
                        move = 32;
                        moveRight();
                        System.out.println("moveRIGHT");
                        nowMove = true;
                        direction_ = RIGHT;
                    }
                break;
            case LEFT:
                if (!nowMove)
                    if (Board.getAt((getX() - 32)/32, getY()/32) instanceof Brick
                            || Board.getAt((getX() - 32)/32, getY()/32) instanceof Wall)
                        System.out.println("cannot move left");
                if (!nowMove) {
                    if (!(Board.getAt((getX() - 32)/32, getY()/32) instanceof Brick
                            || Board.getAt((getX() - 32)/32, getY()/32) instanceof Wall)) {
                        //test condition
                        int k = getX()/32;
                        int k_ = getY()/32;
                        if (getX()-k*32!=0 || getY()-k_*32!=0)
                            System.out.println("x, y: " + getX() + " " + getY());
                        move = 32;
                        moveLeft();
                        System.out.println("moveLEFT");
                        nowMove = true;
                        direction_ = LEFT;
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
    public boolean collide(Entity e) {
        return false;
    }
}



