import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.graphics.Sprite;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Test extends Application {

    public static final int WIDTH = 31;
    public static final int HEIGHT = 13;
    public Canvas canvas;
    public GraphicsContext gc;
    private List<Entity> entities = new ArrayList<>();
    private List<Entity> stillObjects = new ArrayList<>();
    private static File file = new File("C:\\Users\\Tuan Anh\\IdeaProjects\\Project_OOP\\Bomber\\target\\classes\\levels\\Level1.txtp");
    private int level;
    private int row;
    private int col;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws  IOException {
        canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);
        gc = canvas.getGraphicsContext2D();

        Group root = new Group();
        root.getChildren().add(canvas);

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();


        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                render();
                update();
            }
        };
        timer.start();

        createMap();

        Entity bomberman = new Bomber(1, 1, Sprite.player_right.getFxImage());
        entities.add(bomberman);
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
                Entity object;
                switch (s.charAt(j)) {
                    case '#' :
                        object = new Wall(j, i, Sprite.wall.getFxImage());
                        break;
                    case '*' :
                        object = new Brick(j, i, Sprite.brick.getFxImage());
                        break;
                    case 'x' :
                        object = new Portal(j, i, Sprite.portal.getFxImage());
                        break;
                    case '1' :
                        object = new Balloon(j, i, Sprite.balloom_left1.getFxImage());
                        break;
                    case '2' :
                        object = new Oneal(j, i, Sprite.oneal_left1.getFxImage());
                        break;
                    default:
                        object = new Grass(j, i, Sprite.grass.getFxImage());
                        break;
                }
                stillObjects.add(object);
            }
        }
    }

    public void update() {
        entities.forEach(Entity::update);
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        stillObjects.forEach(g -> g.render(gc));
        entities.forEach(g -> g.render(gc));
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getRow() {
        return row;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public int getCol() {
        return col;
    }
}
