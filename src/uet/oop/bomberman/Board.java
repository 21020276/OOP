package uet.oop.bomberman;

import uet.oop.bomberman.entities.Player.Bomber;
import uet.oop.bomberman.entities.Entity;

import java.util.ArrayList;
import java.util.List;

public class Board {
    public static List<Entity> entities = new ArrayList<>();

    public static Entity getAt(int x, int y) {
        for (Entity e : entities) {
            if (e instanceof Bomber) return null;

            if (e.getX() / 32 == x && e.getY() / 32 == y) {
                return e;
            }
        }
        return null;
    }

    public List<Entity> getEntities() {
        return entities;
    }
}
