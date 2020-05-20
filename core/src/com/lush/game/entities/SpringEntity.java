package com.lush.game.entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.lush.game.components.BodyComponent;
import com.lush.game.components.SpringComponent;
import com.lush.game.components.TextureComponent;
import com.lush.game.utils.AssetUtils;
import com.lush.game.utils.Constants;

public class SpringEntity implements BaseEntity{
    private Entity entity;

    public SpringEntity(PooledEngine engine, World world, AssetUtils assetUtils, float xPos, float yPos){
        entity = engine.createEntity();
        TextureComponent textureComponent = engine.createComponent(TextureComponent.class);
        SpringComponent springComponent = engine.createComponent(SpringComponent.class);
        BodyComponent bodyComponent = engine.createComponent(BodyComponent.class);

        textureComponent.texture = assetUtils.getSpring();
        textureComponent.width = 10;
        textureComponent.height = 7.5f;
        textureComponent.x = xPos;
        textureComponent.y = yPos;

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        bodyDef.position.set(textureComponent.x + textureComponent.width / 2,
                textureComponent.y + textureComponent.height / 2);
        bodyComponent.body = world.createBody(bodyDef);
        bodyComponent.body.setUserData(textureComponent.texture);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(textureComponent.width / 2, textureComponent.height / 2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.isSensor = true;
        fixtureDef.filter.categoryBits = Constants.SPRING;
        fixtureDef.filter.maskBits = Constants.PLAYER;
        bodyComponent.fixture = bodyComponent.body.createFixture(fixtureDef);

        shape.dispose();

        entity.add(textureComponent);
        entity.add(springComponent);
        entity.add(bodyComponent);
    }

    @Override
    public Entity getEntity(){
        return entity;
    }
}
