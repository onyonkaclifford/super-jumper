package com.lush.game.utils;

import com.badlogic.ashley.core.ComponentMapper;
import com.lush.game.components.AnimationComponent;
import com.lush.game.components.BarComponent;
import com.lush.game.components.BodyComponent;
import com.lush.game.components.CoinComponent;
import com.lush.game.components.EnemyComponent;
import com.lush.game.components.PlayerComponent;
import com.lush.game.components.SpringComponent;
import com.lush.game.components.SpriteComponent;
import com.lush.game.components.TextureComponent;
import com.lush.game.components.TextureRegionComponent;

public class Mappers{
    public static ComponentMapper<BodyComponent> bodyComponentComponentMapper = ComponentMapper.getFor(BodyComponent.class);
    public static ComponentMapper<SpriteComponent> spriteComponentComponentMapper = ComponentMapper.getFor(SpriteComponent.class);
    public static ComponentMapper<TextureComponent> textureComponentComponentMapper = ComponentMapper.getFor(TextureComponent.class);
    public static ComponentMapper<TextureRegionComponent> textureRegionComponentComponentMapper = ComponentMapper.getFor(TextureRegionComponent.class);
    public static ComponentMapper<PlayerComponent> playerComponentComponentMapper = ComponentMapper.getFor(PlayerComponent.class);
    public static ComponentMapper<BarComponent> barComponentComponentMapper = ComponentMapper.getFor(BarComponent.class);
    public static ComponentMapper<CoinComponent> coinComponentComponentMapper = ComponentMapper.getFor(CoinComponent.class);
    public static ComponentMapper<SpringComponent> springComponentComponentMapper = ComponentMapper.getFor(SpringComponent.class);
    public static ComponentMapper<EnemyComponent> enemyComponentComponentMapper = ComponentMapper.getFor(EnemyComponent.class);
    public static ComponentMapper<AnimationComponent> animationComponentComponentMapper = ComponentMapper.getFor(AnimationComponent.class);
}
