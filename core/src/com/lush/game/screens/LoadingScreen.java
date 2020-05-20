package com.lush.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.lush.game._Game;
import com.lush.game.utils.AssetUtils;
import com.lush.game.utils.AudioUtils;
import com.lush.game.utils.Constants;

public class LoadingScreen extends ScreenAdapter{
    private BitmapFont bitmapFont;
    private _Game game;
    private boolean toLoadAssets;
    private boolean startedSettingMenuScreen;


    public LoadingScreen(_Game _game){
        this.game = _game;
        toLoadAssets = true;
        startedSettingMenuScreen = false;

        bitmapFont = new BitmapFont();
        bitmapFont.setColor(0.3f, 0.7f, 0.3f, 0.3f);

        game.audioUtils = AudioUtils.getInstance();
        game.assetUtils = AssetUtils.getInstance();
    }

    private void loadAssets(){
        toLoadAssets = false;
        new Thread(new Runnable(){
            @Override
            public void run() {
                game.audioUtils.finishLoading();
                game.assetUtils.finishLoading();
            }
        }).run();
    }

    private void setMenuScreen(){
        if(!startedSettingMenuScreen){
            startedSettingMenuScreen = true;
            game.setScreen(new MenuScreen(game));
        }
    }

    @Override
    public void render(float delta){
        Gdx.gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(toLoadAssets){
            loadAssets();
        }
        if(AudioUtils.finishedLoading && AssetUtils.finishedLoading){
            setMenuScreen();
        }

        game.spriteBatch.begin();
        bitmapFont.draw(game.spriteBatch,
                "Loading...",
                Constants.WINDOW_WIDTH / 2 - 10,
                Constants.WINDOW_HEIGHT / 2 - bitmapFont.getCapHeight() / 2);
        game.spriteBatch.end();
    }

    @Override
    public void dispose(){
        bitmapFont.dispose();
    }
}
