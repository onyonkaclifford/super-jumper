package com.lush.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.lush.game._Game;

public class TutorialScreen extends ScreenAdapter{
    private _Game game;
    private Skin skin;
    private Stage stage;

    TutorialScreen(_Game _game){
        this.game = _game;
        skin = game.assetUtils.getSkin();
        stage = new Stage();

        Label titleLabel = new Label("Tutorial", skin, "title-plain");
        titleLabel.setAlignment(Align.center);

        Label label1 = new Label("Tap the left side of the screen or tilt device to the left to move the player to the left. Tap the right side of the screen or tilt device to the right to move the player to the right. Tap and hold to increase speed of player.", skin, "default");
        label1.setAlignment(Align.center);
        label1.setWrap(true);

        Label skinCredit = new Label("Music: Geir Tjelta\nSkin by [***Raymond \"Raeleus\" Buckley***](http://www.badlogicgames.com/forum/viewtopic.php?f=22&t=21568). Skin licence: [CC BY 4.0](http://creativecommons.org/licenses/by/4.0/).", skin, "default");
        skinCredit.setAlignment(Align.center);
        skinCredit.setWrap(true);

        Label libGDXCredit = new Label("Credit to LibGDX", skin, "default");
        libGDXCredit.setAlignment(Align.center);
        libGDXCredit.setWrap(true);

        Table table = new Table(skin);
        table.setFillParent(true);
        table.setBackground(skin.getDrawable("window"));
        table.add(titleLabel).width(250f).height(80f).space(16f).colspan(2);
        table.row();
        table.add(label1).width(500f).height(80f).space(16f).colspan(2);
        table.row();
        table.add(skinCredit).width(500f).height(80f).space(16f).colspan(2);
        table.row();
        table.add(libGDXCredit).width(500f).height(80f).space(16f).colspan(2);
        table.row();

        stage.addActor(table);

        InputProcessor inputProcessor = new InputAdapter(){
            @Override
            public boolean keyDown(int keycode){
                if(keycode == Input.Keys.BACK){
                    game.setScreen(new MenuScreen(game));
                }
                return true;
            }
        };

        InputMultiplexer inputMultiplexer = new InputMultiplexer(inputProcessor);

        Gdx.input.setCatchBackKey(true);
        Gdx.input.setInputProcessor(inputMultiplexer);
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
