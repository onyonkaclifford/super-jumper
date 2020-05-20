package com.lush.game.systems;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

public class DebugSystem extends EntitySystem{
    private Box2DDebugRenderer debugRenderer;
    private World world;
    private SpriteBatch spriteBatch;

    public DebugSystem(World world, SpriteBatch spriteBatch){
        this.world = world;
        this.spriteBatch = spriteBatch;
        debugRenderer = new Box2DDebugRenderer();
    }

    @Override
    public void update (float deltaTime){
        debugRenderer.render(world, spriteBatch.getProjectionMatrix().cpy().scale(1, 1, 0));
    }
}
