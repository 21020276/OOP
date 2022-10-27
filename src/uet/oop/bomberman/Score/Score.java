package uet.oop.bomberman.Score;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Player.Bomber;

public class Score {

    private boolean thisGameOver = false;
    private String timeLeft;
    private double time = 150;
    public Text text = new Text();
    public Text gameOver = new Text();
    public Score() {
        //text.setSelectionFill(Color.WHITE);
        text.setFill(Color.WHITE);
        text.setX(450);
        text.setY(437);
        text.setTextAlignment(TextAlignment.CENTER);
        text.setWrappingWidth(160);
        text.setFont(Font.font(25));

        gameOver.setFill(Color.BLACK);
        gameOver.setX(350);
        gameOver.setY(170);
        gameOver.setTextAlignment(TextAlignment.CENTER);
        gameOver.setWrappingWidth(400);
        gameOver.setFont(Font.font(140));
        gameOver.setText("Game Over!!!!");
    }

    public void updateScore() {
        if (!Bomber.isAlive && !thisGameOver) {
            System.out.println("die");
            if (!Bomber.isAlive) BombermanGame.root.getChildren().add(gameOver);
            thisGameOver = true;
        } else if (!thisGameOver){
            timeLeft = "Time: " + (int)time;
            text.setText(timeLeft);
            if (time > 0)
                time -= 1.0 / 50;
            if (time <= 0)
                Bomber.isAlive = false;
        }
    }
}
