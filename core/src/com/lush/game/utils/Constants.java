package com.lush.game.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public class Constants{
    public static final int WORLD_WIDTH = 100;
    public static final int WORLD_HEIGHT = 200;
    public static final int WINDOW_WIDTH = Gdx.graphics.getWidth();
    public static final int WINDOW_HEIGHT = Gdx.graphics.getHeight();
    public static final float PIXELS_TO_METRES_WIDTH = (float) WORLD_WIDTH / (float) WINDOW_WIDTH;
    public static final float PIXELS_TO_METRES_HEIGHT = (float) WORLD_HEIGHT / (float) WINDOW_HEIGHT;
    public static final float METRES_TO_PIXELS_WIDTH = (float) WINDOW_WIDTH / (float) WORLD_WIDTH;
    public static final float METRES_TO_PIXELS_HEIGHT = (float) WINDOW_HEIGHT / (float) WORLD_HEIGHT;
    public static final Vector2 GRAVITY = new Vector2(0, -10);

    public static final int TOUCH_SCREEN = 1;
    public static final int ACCELEROMETER = 1 << 1;

    public static final int PLAYER = 1;
    public static final int BAR_NOT_SENSOR = 1 << 1;
    public static final int BAR_SENSOR = 1 << 2;
    public static final int COIN = 1 << 3;
    public static final int SPRING = 1 << 4;
    public static final int ENEMY = 1 << 5;
    public static final int NO_COLLISIONS = 1 << 6;
}
