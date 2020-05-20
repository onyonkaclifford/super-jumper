package com.lush.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.lush.game._Game;
import com.lush.game.utils.Constants;

public class MenuScreen extends ScreenAdapter{
    private Skin skin;
    private Stage stage;

    private _Game game;
    private boolean initialisationOver;

    MenuScreen(_Game _game){
        this.game = _game;
        skin = game.assetUtils.getSkin();
        stage = new Stage();
        final Sound clickSoundEffects = game.audioUtils.getClickSoundEffect();
        initialisationOver = false;

        TextButton newGameTextButton = new TextButton("New Game", skin, "round");
        newGameTextButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                if(game.storage.getToPlaySoundEffects() && initialisationOver){
                    clickSoundEffects.play();
                }
                game.setScreen(new NewGameScreen(game));
            }
        });

        TextButton highScoresTextButton = new TextButton("High Scores", skin, "round");
        highScoresTextButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                if(game.storage.getToPlaySoundEffects() && initialisationOver){
                    clickSoundEffects.play();
                }
                game.setScreen(new HighScoresScreen(game));
            }
        });

        TextButton tutorialTextButton = new TextButton("Tutorial", skin, "round");
        tutorialTextButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                if(game.storage.getToPlaySoundEffects() && initialisationOver){
                    clickSoundEffects.play();
                }
                game.setScreen(new TutorialScreen(game));
            }
        });

        TextButton exitTextButton = new TextButton("Exit", skin, "round");
        exitTextButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                if(game.storage.getToPlaySoundEffects() && initialisationOver){
                    clickSoundEffects.play();
                }
                _Game.exit();
            }
        });

        final CheckBox accelerometerCheckBox = new CheckBox("Accelerometer", skin, "switch");
        accelerometerCheckBox.addListener(new ChangeListener(){
            @Override
            public void changed(ChangeEvent event, Actor actor){
                if(game.storage.getToPlaySoundEffects() && initialisationOver){
                    clickSoundEffects.play();
                }
                if(accelerometerCheckBox.isChecked()){
                    game.storage.setController(Constants.ACCELEROMETER);
                }else{
                    game.storage.setController(Constants.TOUCH_SCREEN);
                }
            }
        });
        if(game.storage.getController() == Constants.ACCELEROMETER){
            accelerometerCheckBox.setChecked(true);
        }else{
            accelerometerCheckBox.setChecked(false);
        }

        TextureRegionDrawable musicOnDrawable = new TextureRegionDrawable(game.assetUtils.getMusicOn());
        musicOnDrawable.setMinWidth(80);
        musicOnDrawable.setMinHeight(80);
        TextureRegionDrawable musicDownDrawable = new TextureRegionDrawable(game.assetUtils.getMusicDown());
        musicDownDrawable.setMinWidth(80);
        musicDownDrawable.setMinHeight(80);
        TextureRegionDrawable musicOffDrawable = new TextureRegionDrawable(game.assetUtils.getMusicOff());
        musicOffDrawable.setMinWidth(80);
        musicOffDrawable.setMinHeight(80);
        final ImageButton musicImageButton = new ImageButton(musicOnDrawable, musicDownDrawable, musicOffDrawable);
        musicImageButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                if(game.storage.getToPlaySoundEffects() && initialisationOver){
                    clickSoundEffects.play();
                }
                if(game.storage.getToPlayMusic()){
                    game.stopMusic();
                    musicImageButton.setChecked(true);
                }else{
                    game.playMusic();
                    musicImageButton.setChecked(false);
                }
            }
        });

        TextureRegionDrawable soundOnDrawable = new TextureRegionDrawable(game.assetUtils.getSoundOn());
        soundOnDrawable.setMinWidth(80);
        soundOnDrawable.setMinHeight(80);
        TextureRegionDrawable soundDownDrawable = new TextureRegionDrawable(game.assetUtils.getSoundDown());
        soundDownDrawable.setMinWidth(80);
        soundDownDrawable.setMinHeight(80);
        TextureRegionDrawable soundOffDrawable = new TextureRegionDrawable(game.assetUtils.getSoundOff());
        soundOffDrawable.setMinWidth(80);
        soundOffDrawable.setMinHeight(80);
        final ImageButton soundEffectsImageButton = new ImageButton(soundOnDrawable, soundDownDrawable, soundOffDrawable);
        soundEffectsImageButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                if(game.storage.getToPlaySoundEffects() && initialisationOver){
                    clickSoundEffects.play();
                }
                if(game.storage.getToPlaySoundEffects()){
                    game.stopSoundEffects();
                    soundEffectsImageButton.setChecked(true);
                }else{
                    game.playSoundEffects();
                    soundEffectsImageButton.setChecked(false);
                }
            }
        });


        Table table = new Table(skin);
        table.setFillParent(true);
        table.setBackground(skin.getDrawable("window"));
        table.add(newGameTextButton).width(200).height(80).space(16).colspan(2);
        table.row();
        table.add(highScoresTextButton).width(200).height(80).space(16).colspan(2);
        table.row();
        table.add(tutorialTextButton).width(200).height(80).space(16).colspan(2);
        table.row();
        table.add(exitTextButton).width(200).height(80).space(16).colspan(2);
        table.row();
        table.add(accelerometerCheckBox).width(200).height(80).space(16).colspan(2);
        table.row();
        table.add(musicImageButton).width(80).height(80).space(16);
        table.add(soundEffectsImageButton).width(80).height(80).space(16);

        stage.addActor(table);

        if(game.storage.getToPlayMusic()){
            musicImageButton.setChecked(false);
            game.playMusic();
        }else{
            musicImageButton.setChecked(true);
        }

        if(game.storage.getToPlaySoundEffects()){
            soundEffectsImageButton.setChecked(false);
        }else{
            soundEffectsImageButton.setChecked(true);
        }

        InputProcessor inputProcessor = new InputAdapter(){
            @Override
            public boolean keyDown(int keycode){
                if(keycode == Input.Keys.BACK){
                    _Game.exit();
                }
                return true;
            }
        };

        InputMultiplexer inputMultiplexer = new InputMultiplexer(stage, inputProcessor);

        Gdx.input.setCatchBackKey(true);
        Gdx.input.setInputProcessor(inputMultiplexer);

        initialisationOver = true;
    }

    @Override
    public void render(float delta){
        Gdx.gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.draw();
    }

    @Override
    public void dispose(){
        skin.dispose();
        stage.dispose();
    }
}
