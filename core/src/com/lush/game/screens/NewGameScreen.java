package com.lush.game.screens;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.lush.game._Game;
import com.lush.game.components.PlayerComponent;
import com.lush.game.entities.BackgroundEntity;
import com.lush.game.entities.BarEntity;
import com.lush.game.entities.CoinEntity;
import com.lush.game.entities.EnemyEntity;
import com.lush.game.entities.PlayerEntity;
import com.lush.game.entities.SpringEntity;
import com.lush.game.extras.Score;
import com.lush.game.systems.BarsSystem;
import com.lush.game.systems.CoinsSystem;
import com.lush.game.systems.CollisionSystem;
import com.lush.game.systems.EnemySystem;
import com.lush.game.systems.PhysicsSystem;
import com.lush.game.systems.PlayerSystem;
import com.lush.game.systems.RenderingSystem;
import com.lush.game.systems.SpringSystem;
import com.lush.game.utils.Constants;
import com.lush.game.utils.Storage;

public class NewGameScreen extends ScreenAdapter{
    private World world;
    private PooledEngine engine;
    private Stage pauseStage;
    private Stage gameEndedStage;
    private SpriteBatch scoreSpriteBatch;
    private Storage storage;

    private _Game game;

    private float barYPos;
    private boolean accelerometerAvailable;
    private boolean leftScreenTouched;
    private boolean rightScreenTouched;
    private boolean isGamePaused;
    public Entity playerEntity;
    public static boolean gameEnded;
    private boolean gameEndedStageActing;

    NewGameScreen(_Game _game){
        this.game = _game;
        world = new World(Constants.GRAVITY, true);
        engine = new PooledEngine();
        barYPos = 0;
        leftScreenTouched = false;
        rightScreenTouched = false;
        isGamePaused = false;
        gameEnded = false;
        scoreSpriteBatch = new SpriteBatch();
        storage = new Storage();
        gameEndedStageActing = false;
        OrthographicCamera camera = new OrthographicCamera(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT);

        createBackground();
        createBarAndPlayer();

        engine.addSystem(new RenderingSystem(game.spriteBatch, camera));
        engine.addSystem(new PhysicsSystem(world, this));
        //engine.addSystem(new BackgroundSystem(camera));
        engine.addSystem(new PlayerSystem(camera));
        engine.addSystem(new BarsSystem(camera, this));
        engine.addSystem(new CollisionSystem(world, this, game.audioUtils));
        engine.addSystem(new CoinsSystem(camera));
        engine.addSystem(new SpringSystem(camera));
        engine.addSystem(new EnemySystem(camera));
        //engine.addSystem(new DebugSystem(world, game.spriteBatch));

        Label titleLabel = new Label("Game Paused", game.assetUtils.getSkin(), "title-plain");
        titleLabel.setAlignment(Align.center);

        TextButton resumeTextButton = new TextButton("Resume", game.assetUtils.getSkin(), "round");
        resumeTextButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                resumeGame();
            }
        });

        TextButton endGameTextButton = new TextButton("End Game", game.assetUtils.getSkin(), "round");
        endGameTextButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                endGame();
            }
        });

        Table pauseTable = new Table(game.assetUtils.getSkin());
        pauseTable.setFillParent(true);
        pauseTable.setBackground(game.assetUtils.getSkin().getDrawable("window"));
        pauseTable.add(titleLabel).width(250f).height(80f).space(16f);
        pauseTable.row();
        pauseTable.add(resumeTextButton).width(250f).height(80f).space(16f);
        pauseTable.row();
        pauseTable.add(endGameTextButton).width(250f).height(80f).space(16f);

        pauseStage = new Stage(){
            @Override
            public void act(){}
        };
        pauseStage.addActor(pauseTable);

        final Label endGameScoreLabel = new Label("Game over. Score: " + String.valueOf(Score.getScore()), game.assetUtils.getSkin(), "title-plain");
        endGameScoreLabel.setAlignment(Align.center);

        Table endGameTable = new Table(game.assetUtils.getSkin());
        endGameTable.setFillParent(true);
        endGameTable.setBackground(game.assetUtils.getSkin().getDrawable("window"));
        endGameTable.add(endGameScoreLabel).width(250f).height(80f).space(16f);

        gameEndedStage = new Stage(){
            private int countToEnd = 0;
            @Override
            public void act(){
                endGameScoreLabel.setText("Game over. Score: " + String.valueOf(Score.getScore()));
                countToEnd++;
                if(countToEnd >= 100){
                    endGame();
                }
            }
        };
        gameEndedStage.addActor(endGameTable);

        InputProcessor inputProcessor = new InputAdapter(){
            @Override
            public boolean keyDown(int keycode){
                if(keycode == Input.Keys.BACK){
                    pauseGame();
                }
                return true;
            }

            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button){
                if(game.storage.getController() == Constants.TOUCH_SCREEN){
                    if(screenX < (Constants.WORLD_WIDTH * Constants.METRES_TO_PIXELS_WIDTH) / 2){
                        leftScreenTouched = true;
                        rightScreenTouched = false;
                    } else {
                        rightScreenTouched = true;
                        leftScreenTouched = false;
                    }
                    return true;
                }else{
                    return false;
                }
            }

            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button){
                leftScreenTouched = false;
                rightScreenTouched = false;
                return true;
            }
        };
        InputMultiplexer inputMultiplexer = new InputMultiplexer(pauseStage, inputProcessor);

        Gdx.input.setCatchBackKey(true);
        Gdx.input.setInputProcessor(inputMultiplexer);

        accelerometerAvailable = Gdx.input.isPeripheralAvailable(Input.Peripheral.Accelerometer);
    }

    private void pauseGame(){
        isGamePaused = true;
    }

    private void resumeGame(){
        isGamePaused = false;
    }

    private void createBackground(){
        BackgroundEntity backgroundEntity1 = new BackgroundEntity(engine, 0, 0, BackgroundEntity.Upside.UPSIDE_UP);
        engine.addEntity(backgroundEntity1.getEntity());

        //BackgroundEntity backgroundEntity2 = new BackgroundEntity(engine, 0, Constants.WORLD_HEIGHT, BackgroundEntity.Upside.UPSIDE_DOWN);
        //engine.addEntity(backgroundEntity2.getEntity());
    }

    public void createBar(){
        float xPos = ((float)(Math.random() * Constants.WORLD_WIDTH)) - 20;
        while(xPos < 0) xPos = ((float)(Math.random() * Constants.WORLD_WIDTH)) - 20;

        boolean toMove = false;

        if(Math.random() >= 0.7) toMove = true;

        if(Math.random() >= 0.5) createCoin();

        BarEntity barEntity = new BarEntity(engine, world, xPos, barYPos, toMove);
        engine.addEntity(barEntity.getEntity());

        if(!toMove && Math.random() >= 0.7) createSpring(xPos + 5, barYPos + 5);

        if(barYPos >= 1200){
            if(Math.random() >= 0.5) createEnemy();
        }

        barYPos += 60;
    }

    private void createPlayer(float xPos, float yPos){
        PlayerEntity _playerEntity = new PlayerEntity(engine, world, xPos, yPos);
        playerEntity = _playerEntity.getEntity();
        engine.addEntity(playerEntity);
    }

    private void createBarAndPlayer(){
        float xPos = 0;

        BarEntity barEntity = new BarEntity(engine, world, xPos, barYPos, false);
        engine.addEntity(barEntity.getEntity());

        createPlayer(xPos + 2.5f, barYPos + 5);

        barYPos += 60;

        for(int i = 0; i < 4; i++) {
            createBar();
        }

        createScore();
    }

    private void createCoin(){
        float xPos = ((float)(Math.random() * Constants.WORLD_WIDTH)) - 5;
        while(xPos < 0) xPos = ((float)(Math.random() * Constants.WORLD_WIDTH)) - 5;

        CoinEntity coinEntity = new CoinEntity(engine, world, game.assetUtils,  xPos, barYPos + 30);
        engine.addEntity(coinEntity.getEntity());
    }

    private void createSpring(float xPos, float yPos){
        SpringEntity springEntity = new SpringEntity(engine, world, game.assetUtils,  xPos, yPos);
        engine.addEntity(springEntity.getEntity());
    }

    private void createEnemy(){
        float xPos = ((float)(Math.random() * Constants.WORLD_WIDTH)) - 7.5f;
        while(xPos < 0) xPos = ((float)(Math.random() * Constants.WORLD_WIDTH)) - 7.5f;

        float plusY = (float)(Math.random() * 60);

        EnemyEntity enemyEntity = new EnemyEntity(engine, game.assetUtils, world,  xPos, barYPos + plusY);
        engine.addEntity(enemyEntity.getEntity());
    }

    private void createScore(){
        //ScoreEntity scoreEntity = ScoreEntity.getInstance(engine, game.assetUtils.getSkin(), Constants.WORLD_WIDTH - 10, Constants.WORLD_HEIGHT - 10);
        Score.initialise();
    }

    private void endGame(){
        game.setScreen(new MenuScreen(game));
    }

    @Override
    public void render(float delta){
        Gdx.gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(accelerometerAvailable && game.storage.getController() == Constants.ACCELEROMETER){
            PlayerComponent.velocityX = -Gdx.input.getAccelerometerX() * 25;
        }else{
            if(leftScreenTouched){
                PlayerComponent.velocityX -= 10;
            }else if(rightScreenTouched){
                PlayerComponent.velocityX += 10;
            }else{
                if(PlayerComponent.velocityX >= 10) {
                    PlayerComponent.velocityX -= 10;
                }else{
                    PlayerComponent.velocityX = 0;
                }
            }
        }

        if(gameEnded){
            if(!gameEndedStageActing) storage.setHighScore(Score.getScore());
            gameEndedStageActing = true;
            gameEndedStage.act();
            gameEndedStage.draw();
        }else if(isGamePaused){
            pauseStage.act();
            pauseStage.draw();
        }else {
            engine.update(delta);
        }

        scoreSpriteBatch.begin();
        Score.getBitmapFont().draw(scoreSpriteBatch, "Score: " + String.valueOf(Score.getScore()),
                (Constants.WORLD_WIDTH - 11) * Constants.METRES_TO_PIXELS_WIDTH,
                Constants.WORLD_HEIGHT * Constants.METRES_TO_PIXELS_HEIGHT - Score.getBitmapFont().getCapHeight());
        scoreSpriteBatch.end();
    }

    @Override
    public void dispose(){
        pauseStage.dispose();
        gameEndedStage.dispose();
        world.dispose();
    }
}
