package uet.oop.bomberman.Sound;
import javax.sound.sampled.*;
import javax.swing.*;

import java.io.IOException;
import java.net.URL;
import static uet.oop.bomberman.BombermanGame.*;

public class Sound extends JFrame{
    public static Clip title_screen;
    public static Clip bomb_explosion;
    public static Clip just_died;
    public static Clip put_bomb;
    public static boolean is_sound_died;
    public static boolean is_sound_title;

    public Sound(String name, String sound) {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        try {
            URL url;
            if (name.equals("title_screen.wav"))
                url = getClass().getClassLoader().getResource("sound/title_screen.wav");
            else if (name.equals("sound/put_bombs.wav"))
                url = getClass().getClassLoader().getResource("sound/put_bombs.wav");
            else if (name.equals("sound/bomb_explosion.wav"))
                url = getClass().getClassLoader().getResource("sound/bomb_explosion.wav");
            else
                url = getClass().getClassLoader().getResource("sound/just_died.wav");
            assert  url != null;
            AudioInputStream audio_input = AudioSystem.getAudioInputStream(url);
            if (sound.equals("title")) {
                title_screen = AudioSystem.getClip();
                title_screen.open(audio_input);
                FloatControl gainControl = (FloatControl) title_screen.getControl(FloatControl.Type.MASTER_GAIN);
                gainControl.setValue(-8.0f);
                title_screen.loop(10);
            }
            if (sound.equals("explosion")) {
                bomb_explosion = AudioSystem.getClip();
                bomb_explosion.open(audio_input);
                FloatControl gainControl = (FloatControl) bomb_explosion.getControl(FloatControl.Type.MASTER_GAIN);
                gainControl.setValue(-8.0f);
                bomb_explosion.start();
            }
            if (sound.equals("just_died")) {
                just_died = AudioSystem.getClip();
                just_died.open(audio_input);
                just_died.start();
            }
            if (sound.equals("putBomb")) {
                put_bomb = AudioSystem.getClip();
                put_bomb.open(audio_input);
                FloatControl gainControl = (FloatControl) put_bomb.getControl(FloatControl.Type.MASTER_GAIN);
                gainControl.setValue(+6.0206f);
                put_bomb.start();
            }
            if (sound.equals("default")) {
                Clip clip = AudioSystem.getClip();
                clip.open(audio_input);
                clip.start();
            }

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public static void updateSound() {
        if (!is_sound_title) {
            new Sound("title_screen.wav", "title");
            is_sound_title = true;
        }
        if (!_bomber.isAlive) {
            title_screen.close();
            bomb_explosion.close();
            if (!is_sound_died) {
                new Sound("just_died.wav", "just_died");
                is_sound_died = true;
            }
        }
    }
}


