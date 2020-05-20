package com.lush.game.systems;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.lush.game.components.BodyComponent;
import com.lush.game.components.PlayerComponent;
import com.lush.game.screens.NewGameScreen;
import com.lush.game.utils.Mappers;

public class PhysicsSystem extends EntitySystem{
    private World world;
    private NewGameScreen newGameScreen;
    private float accumulator;

    public PhysicsSystem(World world, NewGameScreen newGameScreen){
        this.world = world;
        this.newGameScreen = newGameScreen;
        accumulator = 0;
    }

    private void simulatePlayer(){
        BodyComponent playerBodyComponent = Mappers.bodyComponentComponentMapper.get(newGameScreen.playerEntity);
        Vector2 velocity = playerBodyComponent.body.getLinearVelocity();
        velocity.set(PlayerComponent.velocityX, velocity.y);
        playerBodyComponent.body.setLinearVelocity(velocity);
    }

    private void simulateTime(float timeStep, float deltaTime){
        accumulator += deltaTime;
        while (accumulator >= deltaTime) {
            world.step(timeStep, 6, 2);
            accumulator -= timeStep;
        }
    }

    @Override
    public void update(float deltaTime){
        simulateTime(1f / 60f, deltaTime);
        simulatePlayer();
    }
}
