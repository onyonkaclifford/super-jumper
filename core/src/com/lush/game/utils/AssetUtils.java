package com.lush.game.utils;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class AssetUtils {
    private static AssetUtils instance = null;
    private AssetManager assetManager;

    public static boolean finishedLoading;

    private AssetUtils(){
        assetManager = new AssetManager();
        finishedLoading = false;

        loadSkin();
        loadButtons();
        loadOtherAssets();
    }

    public static AssetUtils getInstance(){
        if(instance == null){
            instance = new AssetUtils();
        }
        return instance;
    }

    public void finishLoading(){
        assetManager.finishLoading();
        finishedLoading = true;
    }

    public Skin getSkin(){
        return assetManager.get("assets/shade/skin/uiskin.json");
    }

    public Texture getMusicOn(){
        return assetManager.get("assets/shade/raw/music.png");
    }

    public Texture getMusicDown(){
        return assetManager.get("assets/shade/raw/music-down.png");
    }

    public Texture getMusicOff(){
        return assetManager.get("assets/shade/raw/music-off.png");
    }

    public Texture getSoundOn(){
        return assetManager.get("assets/shade/raw/sound.png");
    }

    public Texture getSoundOff(){
        return assetManager.get("assets/shade/raw/sound-off.png");
    }

    public Texture getSoundDown(){
        return assetManager.get("assets/shade/raw/sound-down.png");
    }

    public Texture getCoin(){
        return assetManager.get("assets/coin.png");
    }

    public Texture getCoinFlipping(){
        return assetManager.get("assets/coin_flipping.png");
    }

    public Texture getSpring(){
        return assetManager.get("assets/spring.png");
    }

    public Texture getEnemy(){
        return assetManager.get("assets/enemy.png");
    }

    public Texture getEnemyRunning(){
        return assetManager.get("assets/enemy_running.png");
    }

    public void dispose(){
        assetManager.dispose();
    }

    private void loadSkin(){
        SkinLoader.SkinParameter parameter = new SkinLoader.SkinParameter("assets/shade/skin/uiskin.atlas");
        assetManager.load("assets/shade/skin/uiskin.json", Skin.class, parameter);
    }

    private void loadButtons(){
        assetManager.load("assets/shade/raw/music.png", Texture.class);
        assetManager.load("assets/shade/raw/music-down.png", Texture.class);
        assetManager.load("assets/shade/raw/music-off.png", Texture.class);
        assetManager.load("assets/shade/raw/sound.png", Texture.class);
        assetManager.load("assets/shade/raw/sound-off.png", Texture.class);
        assetManager.load("assets/shade/raw/sound-down.png", Texture.class);
    }

    private void loadOtherAssets(){
        assetManager.load("assets/coin.png", Texture.class);
        assetManager.load("assets/coin_flipping.png", Texture.class);
        assetManager.load("assets/spring.png", Texture.class);
        assetManager.load("assets/enemy.png", Texture.class);
        assetManager.load("assets/enemy_running.png", Texture.class);
    }
}
