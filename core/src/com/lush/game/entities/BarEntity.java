package com.lush.game.entities;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.lush.game.components.BarComponent;
import com.lush.game.components.BodyComponent;
import com.lush.game.components.SpriteComponent;
import com.lush.game.utils.Constants;

public class BarEntity implements BaseEntity{
    private Entity entity;

    public BarEntity(PooledEngine engine, World world, float xPos, float yPos, boolean toMove){
        entity = engine.createEntity();
        SpriteComponent spriteComponent = engine.createComponent(SpriteComponent.class);
        BodyComponent bodyComponent = engine.createComponent(BodyComponent.class);
        BarComponent barComponent = engine.createComponent(BarComponent.class);

        barComponent.toMove = toMove;
        barComponent.toMoveRight = Math.random() >= 0.5;

        spriteComponent.sprite = new Sprite(new Texture(Gdx.files.internal("assets/ground.png")));
        spriteComponent.sprite.setSize(20, 5);
        spriteComponent.sprite.setOrigin(spriteComponent.sprite.getWidth() / 2,
                spriteComponent.sprite.getHeight() / 2);
        spriteComponent.sprite.setPosition(xPos, yPos);

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.KinematicBody;
        bodyDef.position.set(spriteComponent.sprite.getX() + spriteComponent.sprite.getWidth() / 2,
                spriteComponent.sprite.getY() + spriteComponent.sprite.getHeight() / 2);
        bodyComponent.body = world.createBody(bodyDef);
        bodyComponent.body.setUserData(spriteComponent.sprite);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(spriteComponent.sprite.getWidth() / 2,
                spriteComponent.sprite.getHeight() / 2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.5f;
        fixtureDef.filter.categoryBits = Constants.BAR_NOT_SENSOR;
        fixtureDef.filter.maskBits = Constants.PLAYER;
        fixtureDef.isSensor = true;
        bodyComponent.fixture = bodyComponent.body.createFixture(fixtureDef);

        shape.dispose();

        entity.add(spriteComponent);
        entity.add(bodyComponent);
        entity.add(barComponent);
    }

    @Override
    public Entity getEntity(){
        return entity;
    }
}
