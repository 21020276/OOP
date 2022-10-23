package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.graphics.Sprite;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BombermanGame extends Application {
    
    public static final int WIDTH = 32;
    public static final int HEIGHT = 13;
    public static final int TILES_SIZE = 16;
    private GraphicsContext gc;
    public Board board = new Board();
    private Canvas canvas;
    private List<Entity> entities = new ArrayList<>();
    private List<Entity> stillObjects = new ArrayList<>();
    private List<Bomb> bombList = new ArrayList<>();
    private int level;
    private int row;
    private int col;
    private int playerX = 1;
    private int playerY = 1;
    protected Sprite sprite;
    public static List<Item> itemList = new ArrayList<>();

    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

    @Override
    public void start(Stage stage) throws IOException {
        // Tao Canvas
        canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);
        gc = canvas.getGraphicsContext2D();

        // Tao root container
        Group root = new Group();
        root.getChildren().add(canvas);

        // Tao scene
        Scene scene = new Scene(root);

        // Them scene vao stage
        stage.setScene(scene);
        stage.show();

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                render();
                update();
            }
        };
        timer.start();

        createMap();

        Bomber bomberman = new Bomber(playerX, playerY, Sprite.player_right.getFxImage());
        entities.add(bomberman);

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(javafx.scene.input.KeyEvent event) {
                KeyCode keyCode = event.getCode();
                switch (keyCode) {
                    case A:
                        bomberman.setMoveLeft(true);

                        break;
                    case D:
                        bomberman.setMoveRight(true);
                        break;
                    case W:
                        bomberman.setMoveUp(true);
                        break;
                    case S:
                        bomberman.setMoveDown(true);
                        break;
                    case SPACE:
                        bomberman.plantbomb((bomberman.getX() + 16 )/ 32, (bomberman.getY() + 21 )/ 32);
                        break;
                    default:
                        break;
                }
            }
        });
        scene.setOnKeyReleased(new EventHandler<javafx.scene.input.KeyEvent>() {
            @Override
            public void handle(javafx.scene.input.KeyEvent event) {
                KeyCode keyCode = event.getCode();
                switch (keyCode) {
                    case A:
                        bomberman.setMoveLeft(false);
                        break;
                    case D:
                        bomberman.setMoveRight(false);
                        break;
                    case W:
                        bomberman.setMoveUp(false);
                        break;
                    case S:
                        bomberman.setMoveDown(false);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    public void createMap() throws IOException {
        File file = new File("target/classes/levels/Level1.txt");
        Scanner scanner = new Scanner(file);
        level = scanner.nextInt();
        row = scanner.nextInt();
        col = scanner.nextInt();
        scanner.nextLine();

        for (int i = 0; i < row; i++) {
            String s = scanner.nextLine();
            for (int j = 0; j < s.length(); j++) {
                Entity object = new Grass(j, i, Sprite.grass.getFxImage());
                stillObjects.add(object);
                switch (s.charAt(j)) {
                    case '#' :
                        object = new Wall(j, i, Sprite.wall.getFxImage());
                        Board.entities.add(object);
                        break;
                    case '*' :
                        object = new Brick(j, i, Sprite.brick.getFxImage());
                        Board.entities.add(object);
                        break;
                    case 'x' :
                        object = new Portal(j, i, Sprite.portal.getFxImage());
                        stillObjects.add(object);
                        break;
                    case '1' :
                        Balloon balloon = new Balloon(j, i, Sprite.balloom_right1.getFxImage());
                        entities.add(balloon);
                        break;
                    case '2' :
                        object = new Oneal(j, i, Sprite.oneal_left1.getFxImage());
                        stillObjects.add(object);
                        break;
                    case 's' :
                        object = new Brick(j, i, Sprite.brick.getFxImage());
                        Board.entities.add(object);
                        itemList.add(new SpeedItem(j, i, Sprite.powerup_speed.getFxImage()));
                        break;
                    case 'f' :
                        object = new Brick(j, i, Sprite.brick.getFxImage());
                        Board.entities.add(object);
                        itemList.add(new RadiusItem(j, i, Sprite.powerup_flames.getFxImage()));
                        break;
                    case 'b' :
                        object = new Brick(j, i, Sprite.brick.getFxImage());
                        Board.entities.add(object);
                        itemList.add(new BombItem(j, i, Sprite.powerup_bombs.getFxImage()));
                        break;
                    default:
                        break;
                }
            }
        }
    }

    public void update() {
        entities.forEach(Entity::update);
        stillObjects.forEach(Entity::update);
        itemList.forEach(Item::update);
        Board.entities.forEach(Entity::update);
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        stillObjects.forEach(g -> g.render(gc));
        itemList.forEach(g -> g.render(gc));
        entities.forEach(g -> g.render(gc));
        Board.entities.forEach(g -> g.render(gc));
    }

    public int getWidth() {
        return WIDTH;
    }

    public int getHeight() {
        return HEIGHT;
    }

    public int getTilesSize() {
        return TILES_SIZE;
    }

    public void addBomb(Bomb bomb) {
        bombList.add(bomb);
    }
}
