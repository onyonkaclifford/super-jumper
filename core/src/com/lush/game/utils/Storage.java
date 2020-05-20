package com.lush.game.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class Storage{
    private Preferences preferences;

    public Storage(){
        preferences = Gdx.app.getPreferences("MyPreferences");
    }

    public void setToPlayMusic(Boolean toPlayMusic){
        preferences.putBoolean("playMusic", toPlayMusic);
        preferences.flush();
    }

    public void setToPlaySoundEffects(Boolean toPlaySoundEffects){
        preferences.putBoolean("playSoundEffects", toPlaySoundEffects);
        preferences.flush();
    }

    public void setHighScore(int highScore){
        int[] highScores = getHighScores();

        for(int i = 0; i < highScores.length; i++){
            if(highScore > highScores[i]){
                for(int j = highScores.length - 1; j > i; j--){
                    highScores[j] = highScores[j - 1];
                }
                highScores[i] = highScore;
                break;
            }
        }

        preferences.putInteger("score0", highScores[0]);
        preferences.putInteger("score1", highScores[1]);
        preferences.putInteger("score2", highScores[2]);
        preferences.putInteger("score3", highScores[3]);
        preferences.putInteger("score4", highScores[4]);
        preferences.flush();
    }

    public void setController(int controller){
        preferences.putInteger("controller", controller);
        preferences.flush();
    }

    public boolean getToPlayMusic(){
        return preferences.getBoolean("playMusic", true);
    }

    public boolean getToPlaySoundEffects(){
        return preferences.getBoolean("playSoundEffects", true);
    }

    public int getController(){
        return preferences.getInteger("controller", Constants.ACCELEROMETER);
    }

    public int[] getHighScores(){
        int[] highScores = new int[5];
        highScores[0] = preferences.getInteger("score0", 0);
        highScores[1] = preferences.getInteger("score1", 0);
        highScores[2] = preferences.getInteger("score2", 0);
        highScores[3] = preferences.getInteger("score3", 0);
        highScores[4] = preferences.getInteger("score4", 0);
        return highScores;
    }
}
