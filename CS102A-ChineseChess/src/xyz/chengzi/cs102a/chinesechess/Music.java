package xyz.chengzi.cs102a.chinesechess;


import xyz.chengzi.cs102a.chinesechess.chess.ChessComponent;

import javax.sound.sampled.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;


public class Music {


    public static void bMusic() {

        File sound;
        Clip clip;
        try {

            sound = new File("guzheng.wav");
            clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(sound));
            clip.start();
            clip.loop(clip.LOOP_CONTINUOUSLY);

            boolean playCompleted = false;
            while (!playCompleted) {
                // wait for the playback completes
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }

            clip.close();


        } catch (Exception e) {

        }

    }

    public static void moveM() {
        File sound;
        Clip clip;
        try {

            sound = new File("move.wav");
            clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(sound));
            clip.start();

            Thread.sleep(1000);

        } catch (Exception e) {

        }
    }










    

    public static void main(String[] args){
       //bMusic();
    }
}
