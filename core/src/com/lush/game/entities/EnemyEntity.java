package com.lush.game.entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.lush.game.components.AnimationComponent;
import com.lush.game.components.BodyComponent;
import com.lush.game.components.EnemyComponent;
import com.lush.game.utils.AssetUtils;
import com.lush.game.utils.Constants;

public class EnemyEntity implements BaseEntity{
    private Entity entity;

    public EnemyEntity(PooledEngine engine, AssetUtils assetUtils, World world, float xPos, float yPos){
        entity = engine.createEntity();
        BodyComponent bodyComponent = engine.createComponent(BodyComponent.class);
        EnemyComponent enemyComponent = engine.createComponent(EnemyComponent.class);
        AnimationComponent animationComponent = engine.createComponent(AnimationComponent.class);

        animationComponent.animation = new Animation<Texture>(1 / 10f, assetUtils.getEnemy(), assetUtils.getEnemyRunning());
        animationComponent.animationStateTime = 0;
        animationComponent.x = xPos;
        animationComponent.y = yPos;
        animationComponent.height = 7.5f;
        animationComponent.width = 7.5f;
        animationComponent.flipX = false;
        animationComponent.flipY = false;
        animationComponent.srcX = 0;
        animationComponent.srcY = 0;
        animationComponent.srcWidth = assetUtils.getEnemy().getWidth();
        animationComponent.srcHeight = assetUtils.getEnemy().getHeight();

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        bodyDef.position.set(animationComponent.x + animationComponent.width / 2,
                animationComponent.y + animationComponent.height / 2);
        bodyComponent.body = world.createBody(bodyDef);
        bodyComponent.body.setUserData(animationComponent.animation);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(animationComponent.width / 2, animationComponent.height / 2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.isSensor = true;
        fixtureDef.filter.categoryBits = Constants.ENEMY;
        fixtureDef.filter.maskBits = Constants.PLAYER;
        bodyComponent.fixture = bodyComponent.body.createFixture(fixtureDef);

        shape.dispose();

        entity.add(animationComponent);
        entity.add(bodyComponent);
        entity.add(enemyComponent);
    }

    @Override
    public Entity getEntity(){
        return entity;
    }
}
