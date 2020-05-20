package com.lush.game.extras;

import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class Score{
    private static Score instance = null;
    private static BitmapFont bitmapFont;
    private static int _score;

    private Score(){
        bitmapFont = new BitmapFont();
        bitmapFont.setColor(1, 1, 1, 1);
    }

    public static void initialise(){
        if(instance == null){
            instance = new Score();
        }
        _score = 0;
    }

    public static BitmapFont getBitmapFont(){
        return bitmapFont;
    }

    public static void setScore(int score){
        _score = score;
    }

    public static int getScore(){
        return _score;
    }
}
