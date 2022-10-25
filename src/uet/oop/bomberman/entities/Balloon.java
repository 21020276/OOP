package uet.oop.bomberman.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.graphics.Sprite;

import java.util.Random;

public class Balloon extends Entity {
    protected int _direction = -1;
    private Sprite sprite;
    private boolean isAlive = true;
    private boolean isMove = false;
    public Balloon(int x, int y, Image img) {
        super(x, y, img);
        sprite = Sprite.balloom_right1;
    }


    public int waitTime = 0;
    @Override
    public void update() {
        //sprite = Sprite.movingSprite(Sprite.balloom_left1, Sprite.balloom_left2, Sprite.balloom_left3, _animate, 20);
        animate();
        calculateMove();
        Random random = new Random();
        int direction = random.nextInt(4);
        if (waitTime > 0) waitTime--;
        if (waitTime == 0)
        switch (direction) {
            case 0:
                waitTime = 30;
                setMoveDown(true);
                setMoveUp(false);
                setMoveLeft(false);
                setMoveRight(false);
                break;
            case 1:
                waitTime = 30;
                setMoveDown(false);
                setMoveUp(true);
                setMoveLeft(false);
                setMoveRight(false);
                break;
            case 2:
                waitTime = 30;
                setMoveDown(false);
                setMoveUp(false);
                setMoveLeft(true);
                setMoveRight(false);
                break;
            case 3:
                waitTime = 30;
                setMoveDown(false);
                setMoveUp(false);
                setMoveLeft(false);
                setMoveRight(true);
                break;
        }

    }

    private void chooseSprite() {
        switch(_direction) {
            case 3:
                sprite = Sprite.balloom_left1;
                if(_moving) {
                    sprite = Sprite.movingSprite(Sprite.balloom_left2, Sprite.balloom_left3, _animate, 20);
                }
                break;
            case 1:
                sprite = Sprite.balloom_right1;
                if(_moving) {
                    sprite = Sprite.movingSprite(Sprite.balloom_right1, Sprite.balloom_right2, _animate, 20);
                }
                break;
        }
    }
    @Override
    public void render(GraphicsContext gc) {
        if (isAlive) {
            chooseSprite();
            gc.drawImage(sprite.getFxImage(), x, y);
        } else {
            sprite = Sprite.balloom_dead;
        }
    }

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
            if (Board.getAt(moveX, moveY) == null) {
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
            if (Board.getAt(moveX, moveY) == null) {
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
        if (a != null) {
            return x + 2 >= a.getX() && x + 2 <= a.getX() + 29 && y + 2 <= a.getY() + 29 && y + 2 >= a.getY()
                    || (x + 2 >= a.getX() && x + 2 <= a.getX() + 29 && y + 29 >= a.getY() && y + 29 <= a.getY() + 29)
                    || (x + 29 >= a.getX() && x + 29 <= a.getX() + 29 && y + 2 >= a.getY() && y + 2 <= a.getY() + 29)
                    || (x + 29 >= a.getX() && x + 29 <= a.getX() + 29 && y + 29 >= a.getY() && y + 29 <= a.getY() + 29);
        }
        return false;
    }
}