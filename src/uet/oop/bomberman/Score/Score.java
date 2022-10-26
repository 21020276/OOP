package uet.oop.bomberman.Score;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class Score {

    private String timeLeft;
    private double time = 150;
    public Text text = new Text();
    public Score() {
        text.setX(610);
        text.setY(22);
        text.setUnderline(true);
        text.setTextAlignment(TextAlignment.CENTER);
        text.setWrappingWidth(160);
        text.setFont(Font.font(25));
    }

    public void updateScore() {
        timeLeft = "Time: " + (int)time;
        text.setText(timeLeft);
        time -= 1.0 / 50;
    }
}
