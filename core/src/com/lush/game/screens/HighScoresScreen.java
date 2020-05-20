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
import com.lush.game.utils.Storage;

public class HighScoresScreen extends ScreenAdapter{
    private _Game game;
    private Skin skin;
    private Stage stage;

    HighScoresScreen(_Game _game){
        this.game = _game;
        skin = game.assetUtils.getSkin();
        stage = new Stage();
        Storage storage = new Storage();

        int[] highScores = storage.getHighScores();

        Label titleLabel = new Label("High Scores", skin, "title");
        titleLabel.setAlignment(Align.center);

        Label score1Label = new Label("Score 1:\t" + highScores[0], skin, "title-plain");
        score1Label.setAlignment(Align.center);

        Label score2Label = new Label("Score 2:\t" + highScores[1], skin, "title-plain");
        score2Label.setAlignment(Align.center);

        Label score3Label = new Label("Score 3:\t" + highScores[2], skin, "title-plain");
        score3Label.setAlignment(Align.center);

        Label score4Label = new Label("Score 4:\t" + highScores[3], skin, "title-plain");
        score4Label.setAlignment(Align.center);

        Label score5Label = new Label("Score 5:\t" + highScores[4], skin, "title-plain");
        score5Label.setAlignment(Align.center);

        Table table = new Table(skin);
        table.setFillParent(true);
        table.setBackground(skin.getDrawable("window"));
        table.add(titleLabel).width(250f).height(80f).space(16f);
        table.row();
        table.add(score1Label).width(250f).height(80f).space(16f);
        table.row();
        table.add(score2Label).width(250f).height(80f).space(16f);
        table.row();
        table.add(score3Label).width(250f).height(80f).space(16f);
        table.row();
        table.add(score4Label).width(250f).height(80f).space(16f);
        table.row();
        table.add(score5Label).width(250f).height(80f).space(16f);

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
