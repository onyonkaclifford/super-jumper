package com.lush.game.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.lush.game.components.AnimationComponent;
import com.lush.game.components.BackgroundComponent;
import com.lush.game.components.BarComponent;
import com.lush.game.components.CoinComponent;
import com.lush.game.components.EnemyComponent;
import com.lush.game.components.PlayerComponent;
import com.lush.game.components.SpringComponent;
import com.lush.game.components.SpriteComponent;
import com.lush.game.components.TextureComponent;
import com.lush.game.components.TextureRegionComponent;
import com.lush.game.utils.Constants;
import com.lush.game.utils.Mappers;

public class RenderingSystem extends EntitySystem {
    private OrthographicCamera camera;
    private SpriteBatch spriteBatch;

    private Family playersFamily;
    private Family barsFamily;
    private Family backgroundsFamily;
    private Family coinsFamily;
    private Family springsFamily;
    private Family enemiesFamily;

    private ImmutableArray<Entity> playerEntities;
    private ImmutableArray<Entity> barEntities;
    private ImmutableArray<Entity> backgroundEntities;
    private ImmutableArray<Entity> coinEntities;
    private ImmutableArray<Entity> springEntities;
    private ImmutableArray<Entity> enemyEntities;

    public RenderingSystem(SpriteBatch spriteBatch, OrthographicCamera camera){
        this.spriteBatch = spriteBatch;

        playersFamily = Family.all(PlayerComponent.class).get();
        barsFamily = Family.all(BarComponent.class).get();
        backgroundsFamily = Family.all(BackgroundComponent.class).get();
        coinsFamily = Family.all(CoinComponent.class).get();
        springsFamily = Family.all(SpringComponent.class).get();
        enemiesFamily = Family.all(EnemyComponent.class).get();

        this.camera = camera;
        this.camera.position.set(Constants.WORLD_WIDTH / 2, Constants.WORLD_HEIGHT / 2, 0);
        this.camera.update();
    }

    private void renderPlayer(){
        for(Entity entity : playerEntities){
            SpriteComponent spriteComponent = Mappers.spriteComponentComponentMapper.get(entity);
            if(spriteComponent.sprite != null){
                spriteBatch.begin();
                spriteComponent.sprite.draw(spriteBatch);
                spriteBatch.end();
            }
        }
    }

    private void renderBars(){
        for(Entity entity : barEntities){
            SpriteComponent spriteComponent =  Mappers.spriteComponentComponentMapper.get(entity);
            if(spriteComponent.sprite != null){
                spriteBatch.begin();
                spriteComponent.sprite.draw(spriteBatch);
                spriteBatch.end();
            }
        }
    }

    private void renderBackgrounds(){
        for(Entity entity : backgroundEntities){
            TextureRegionComponent textureRegionComponent =  Mappers.textureRegionComponentComponentMapper.get(entity);
            if(textureRegionComponent.textureRegion != null){
                spriteBatch.begin();
                spriteBatch.draw(textureRegionComponent.textureRegion,
                        textureRegionComponent.x, textureRegionComponent.y,
                        textureRegionComponent.originX, textureRegionComponent.originY,
                        textureRegionComponent.width, textureRegionComponent.height,
                        textureRegionComponent.scaleX, textureRegionComponent.scaleY,
                        textureRegionComponent.rotation);
                spriteBatch.end();
            }
        }
    }

    private void renderCoins(float delta){
        for(Entity entity : coinEntities){
            AnimationComponent animationComponent =  Mappers.animationComponentComponentMapper.get(entity);
            if(animationComponent.animation != null){
                spriteBatch.begin();
                animationComponent.animationStateTime += delta;
                Texture currentTexture = animationComponent.animation.getKeyFrame(animationComponent.animationStateTime, true);
                spriteBatch.draw(currentTexture,
                        animationComponent.x, animationComponent.y,
                        animationComponent.width, animationComponent.height,
                        animationComponent.srcX, animationComponent.srcY,
                        animationComponent.srcWidth, animationComponent.srcHeight,
                        animationComponent.flipX, animationComponent.flipY);
                spriteBatch.end();
            }
        }
    }

    private void renderSprings(){
        for(Entity entity : springEntities){
            TextureComponent textureComponent =  Mappers.textureComponentComponentMapper.get(entity);
            if(textureComponent.texture != null){
                spriteBatch.begin();
                spriteBatch.draw(new TextureRegion(textureComponent.texture),
                        textureComponent.x, textureComponent.y,
                        textureComponent.originX, textureComponent.originY,
                        textureComponent.width, textureComponent.height,
                        textureComponent.scaleX, textureComponent.scaleY,
                        textureComponent.rotation);
                spriteBatch.end();
            }
        }
    }

    private void renderEnemies(float delta){
        for(Entity entity : enemyEntities){
            AnimationComponent animationComponent = Mappers.animationComponentComponentMapper.get(entity);
            if(animationComponent.animation != null){
                spriteBatch.begin();
                animationComponent.animationStateTime += delta;
                Texture currentTexture = animationComponent.animation.getKeyFrame(animationComponent.animationStateTime, true);
                spriteBatch.draw(currentTexture,
                        animationComponent.x, animationComponent.y,
                        animationComponent.width, animationComponent.height,
                        animationComponent.srcX, animationComponent.srcY,
                        animationComponent.srcWidth, animationComponent.srcHeight,
                        animationComponent.flipX, animationComponent.flipY);
                spriteBatch.end();
            }
        }
    }

    @Override
    public void addedToEngine(Engine engine){
        super.addedToEngine(engine);
        playerEntities = engine.getEntitiesFor(playersFamily);
        barEntities = engine.getEntitiesFor(barsFamily);
        backgroundEntities = engine.getEntitiesFor(backgroundsFamily);
        coinEntities = engine.getEntitiesFor(coinsFamily);
        springEntities = engine.getEntitiesFor(springsFamily);
        enemyEntities = engine.getEntitiesFor(enemiesFamily);
    }

    @Override
    public void removedFromEngine(Engine engine){
        super.removedFromEngine(engine);
        playerEntities = null;
        barEntities = null;
        backgroundEntities = null;
        coinEntities = null;
        springEntities = null;
        enemyEntities = null;
    }

    @Override
    public void update(float deltaTime){
        camera.update();
        spriteBatch.setProjectionMatrix(camera.combined);

        renderBackgrounds();
        renderBars();
        renderCoins(deltaTime);
        renderSprings();
        renderEnemies(deltaTime);
        renderPlayer();
    }
}
