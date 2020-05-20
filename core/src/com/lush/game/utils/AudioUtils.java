package com.lush.game.utils;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class AudioUtils{
    private static AudioUtils instance = null;
    private AssetManager assetManager;

    public static boolean finishedLoading;

    private AudioUtils(){
        assetManager = new AssetManager();
        finishedLoading = false;

        loadMusic();
        loadSoundEffects();
    }

    public static AudioUtils getInstance(){
        if(instance == null){
            instance = new AudioUtils();
        }
        return instance;
    }

    public void finishLoading(){
        assetManager.finishLoading();
        finishedLoading = true;
    }

    public Music getMusic(){
        return assetManager.get("assets/music.mp3", Music.class);
    }

    public Sound getJumpSoundEffect(){
        return assetManager.get("assets/jump.wav", Sound.class);
    }

    public Sound getClickSoundEffect(){
        return assetManager.get("assets/click.wav", Sound.class);
    }

    public Sound getCoinSoundEffect(){
        return assetManager.get("assets/coin.wav", Sound.class);
    }

    public Sound getHighJumpSoundEffect(){
        return assetManager.get("assets/highjump.wav", Sound.class);
    }

    public Sound getHitSoundEffect(){
        return assetManager.get("assets/hit.wav", Sound.class);
    }

    public void dispose(){
        assetManager.dispose();
    }

    private void loadMusic(){
        assetManager.load("assets/music.mp3", Music.class);
    }

    private void loadSoundEffects(){
        assetManager.load("assets/jump.wav", Sound.class);
        assetManager.load("assets/click.wav", Sound.class);
        assetManager.load("assets/coin.wav", Sound.class);
        assetManager.load("assets/highjump.wav", Sound.class);
        assetManager.load("assets/hit.wav", Sound.class);
    }
}
