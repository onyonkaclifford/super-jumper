package com.lush.game.entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.lush.game.components.AnimationComponent;
import com.lush.game.components.BodyComponent;
import com.lush.game.components.CoinComponent;
import com.lush.game.utils.AssetUtils;
import com.lush.game.utils.Constants;

public class CoinEntity implements BaseEntity{
    private Entity entity;

    public CoinEntity(PooledEngine engine, World world, AssetUtils assetUtils, float xPos, float yPos){
        entity = engine.createEntity();
        AnimationComponent animationComponent = engine.createComponent(AnimationComponent.class);
        CoinComponent backgroundComponent = engine.createComponent(CoinComponent.class);
        BodyComponent bodyComponent = engine.createComponent(BodyComponent.class);

        animationComponent.animation = new Animation<Texture>(1 / 5f, assetUtils.getCoin(), assetUtils.getCoinFlipping());
        animationComponent.animationStateTime = 0;
        animationComponent.x = xPos;
        animationComponent.y = yPos;
        animationComponent.height = 5;
        animationComponent.width = 5;
        animationComponent.flipX = false;
        animationComponent.flipY = false;
        animationComponent.srcX = 0;
        animationComponent.srcY = 0;
        animationComponent.srcWidth = assetUtils.getCoin().getWidth();
        animationComponent.srcHeight = assetUtils.getCoin().getHeight();

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(animationComponent.x + animationComponent.width / 2,
                animationComponent.y + animationComponent.height / 2);
        bodyComponent.body = world.createBody(bodyDef);
        bodyComponent.body.setUserData(animationComponent.animation);

        CircleShape shape = new CircleShape();
        shape.setRadius(animationComponent.width / 2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.filter.categoryBits = Constants.COIN;
        fixtureDef.filter.maskBits = Constants.PLAYER;
        fixtureDef.isSensor = true;
        bodyComponent.fixture = bodyComponent.body.createFixture(fixtureDef);

        shape.dispose();

        entity.add(animationComponent);
        entity.add(backgroundComponent);
        entity.add(bodyComponent);
    }

    @Override
    public Entity getEntity(){
        return entity;
    }
}
