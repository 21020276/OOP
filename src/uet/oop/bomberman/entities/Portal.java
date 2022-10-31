package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Player.Bomber;

public class Portal extends Entity {

    public Portal(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void update() {
        checkLevelUp();
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

    public void checkLevelUp() {
        if (this.collide(BombermanGame.bomberman) && BombermanGame.isMobDead()) {
            BombermanGame.HasWon = true;
        }
    }
}