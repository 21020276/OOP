package uet.oop.bomberman;

import uet.oop.bomberman.entities.Bomber;
import uet.oop.bomberman.entities.Entity;

import java.util.ArrayList;
import java.util.List;

public class Board {
    public static final int WIDTH = 20;
    public static final int HEIGHT = 15;
    public static List<Entity> entities = new ArrayList<>();


    public void addEntity(Entity entity) {
        entities.add(entity);
    }

    public void removeAt(int x, int y) {
        for (Entity e : entities) {
            if (e.getX() == x && e.getY() == y) {
                entities.remove(e);
                break;
            }
        }
    }

    public boolean validate(int x, int y) {
        return true;
    }

    public static Entity getAt(int x, int y) {
        for (Entity e : entities) {
            if (e instanceof Bomber) return null;

            if (e.getX() / 32 == x && e.getY() / 32 == y) {
                return e;
            }
        }
        return null;
    }

    public static Entity findEntity(Entity e) {
        for (Entity b : entities) {
            if (b.equals(e)) {
                return b;
            }
        }
        return null;
    }

    public List<Entity> getEntities() {
        return entities;
    }
}
