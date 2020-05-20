package com.lush.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.lush.game.screens.LoadingScreen;
import com.lush.game.utils.AssetUtils;
import com.lush.game.utils.AudioUtils;
import com.lush.game.utils.Storage;

public class _Game extends Game{
    private Music music = null;
	public SpriteBatch spriteBatch;
	public AudioUtils audioUtils;
	public AssetUtils assetUtils;
	public Storage storage;

	@Override
	public void create(){
		spriteBatch = new SpriteBatch();
		storage = new Storage();
		setScreen(new LoadingScreen(this));
	}

	public void playMusic(){
	    if(music == null){
	        music = audioUtils.getMusic();
        }
        if(!music.isPlaying()){
	        music.play();
	        music.setLooping(true);
	        music.setVolume(0.5f);
			storage.setToPlayMusic(true);
        }
    }

	public void stopMusic(){
        if(music == null){
            music = audioUtils.getMusic();
        }
        if(music.isPlaying()){
            music.stop();
			storage.setToPlayMusic(false);
        }
    }

    public void playSoundEffects(){
	    storage.setToPlaySoundEffects(true);
    }

    public void stopSoundEffects(){
        storage.setToPlaySoundEffects(false);
    }

	public static void exit(){
	    Gdx.app.exit();
    }

    @Override
    public void render(){
        super.render();
    }

    @Override
    public void dispose(){
        super.dispose();
        music.dispose();
        spriteBatch.dispose();
        audioUtils.dispose();
        assetUtils.dispose();
    }
}
