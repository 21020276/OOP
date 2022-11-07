package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import uet.oop.bomberman.Item.BombItem;
import uet.oop.bomberman.Item.Item;
import uet.oop.bomberman.Item.RadiusItem;
import uet.oop.bomberman.Item.SpeedItem;
import uet.oop.bomberman.Score.Score;
import uet.oop.bomberman.Sound.Sound;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.entities.Mob.Balloon;
import uet.oop.bomberman.entities.Mob.Mob;
import uet.oop.bomberman.entities.Mob.Oneal;
import uet.oop.bomberman.entities.Player.Bomb;
import uet.oop.bomberman.entities.Player.Bomber;
import uet.oop.bomberman.graphics.Sprite;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import static uet.oop.bomberman.Sound.Sound.updateSound;

public class BombermanGame extends Application {
    public static final int WIDTH = 32;
    public static final int HEIGHT = 13;
    private int status = 0;
    private GraphicsContext gc;
    public Board board = new Board();
    private Scene scene;
    private Canvas canvas;
    private Pane pane;
    public static Bomber bomberman;
    public static List<Mob> mobList = new ArrayList<>();
    private List<Entity> entities = new ArrayList<>();
    private List<Entity> stillObjects = new ArrayList<>();
    private List<Bomb> bombList = new ArrayList<>();
    public static int LEVEL;
    private int row;
    private int col;
    private int playerX = 1;
    private int playerY = 1;
    protected Sprite sprite;
    public static List<Item> itemList = new ArrayList<>();
    public static Bomber _bomber;

    public static Group root = new Group();

    Score score;

    public static boolean HasWon = false;

    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

    public void initMainMenu(Pane mainMenuRoot) throws IOException {
        Font font = Font.font(48);

        Button startButton = new Button("Start");
        startButton.setFont(font);
        startButton.setOnAction(event ->{
                    status = 1;
            try {
                start(new Stage());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        );

        VBox vBox = new VBox(50, startButton);
        vBox.setTranslateX(400);
        vBox.setTranslateY(200);

        mainMenuRoot.getChildren().addAll(canvas, vBox);
    }

    @Override
    public void start(Stage stage) throws IOException {
        if (status == 0) {
            canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT + 30);
            gc = canvas.getGraphicsContext2D();
            pane = new Pane();
            initMainMenu(pane);
            scene = new Scene(pane);
            stage.setScene(scene);
            stage.show();
        }

        if (status == 1) {
            score = new Score();

            // Tao root container
            root.getChildren().add(canvas);
            root.getChildren().add(score.text);
            // Tao scene
            scene.setRoot(root);

            // Them scene vao stage
            stage.setScene(scene);
            stage.setTitle("BOMBERMAN");
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

            bomberman = new Bomber(playerX, playerY, Sprite.player_right.getFxImage());
            entities.add(bomberman);
            _bomber = bomberman;

            if (Bomber.isAlive) {
            scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(javafx.scene.input.KeyEvent event) {
                    if (Bomber.isAlive) {
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
                                bomberman.plantbomb((bomberman.getX() + 16) / 32, (bomberman.getY() + 21) / 32);
                                new Sound("sound/put_bombs.wav", "putBomb");
                                break;
                            default:
                                break;
                        }
                    }
                }
            });
            scene.setOnKeyReleased(new EventHandler<javafx.scene.input.KeyEvent>() {
                @Override
                public void handle(javafx.scene.input.KeyEvent event) {
                    if (Bomber.isAlive) {
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
                }
            });
            scene.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                }
            });
            }
        }
    }

    public void createMap() throws IOException {
        File file = new File("target/classes/levels/Level2.txt");
        Scanner scanner = new Scanner(file);
        LEVEL = scanner.nextInt();
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
                        object = new Brick(j, i, Sprite.brick.getFxImage());
                        Board.entities.add(object);
                        stillObjects.add(new Portal(j, i, Sprite.portal.getFxImage()));
                        break;
                    case '1' :
                        Balloon balloon = new Balloon(j, i, Sprite.balloom_right1.getFxImage());
                        //entities.add(balloon);
                        Board.entities.add(balloon);
                        mobList.add(balloon);
                        break;
                    case '2' :
                        Oneal oneal = new Oneal(j, i, Sprite.oneal_left1.getFxImage());
                        //stillObjects.add(object);
                        Board.entities.add(oneal);
                        mobList.add(oneal);
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
        //mobList.forEac(Mob::update);
        entities.forEach(Entity::update);
        stillObjects.forEach(Entity::update);
        itemList.forEach(Item::update);
        Board.entities.forEach(Entity::update);
        if (bomberman.isStop()) {
            entities.remove(bomberman);
        }
        updateSound();
        score.updateScore();
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        stillObjects.forEach(g -> g.render(gc));
        itemList.forEach(g -> g.render(gc));
        entities.forEach(g -> g.render(gc));
        Board.entities.forEach(g -> g.render(gc));
        //mobList.forEach(g -> g.render(gc));
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 415, 993, 30);
    }

    public static boolean isMobDead() {
        return Bomber.numberOfMobKilled == mobList.size();
    }
}
