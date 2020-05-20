package com.lush.game.systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.lush.game.components.BarComponent;
import com.lush.game.components.BodyComponent;
import com.lush.game.components.CoinComponent;
import com.lush.game.components.EnemyComponent;
import com.lush.game.components.SpringComponent;
import com.lush.game.components.SpriteComponent;
import com.lush.game.extras.Score;
import com.lush.game.screens.NewGameScreen;
import com.lush.game.utils.AudioUtils;
import com.lush.game.utils.Constants;
import com.lush.game.utils.Mappers;
import com.lush.game.utils.Storage;

public class CollisionSystem extends EntitySystem{
    private Family barsFamily;
    private Family coinsFamily;
    private Family springsFamily;
    private Family enemiesFamily;
    private ImmutableArray<Entity> barEntities;
    private ImmutableArray<Entity> coinEntities;
    private ImmutableArray<Entity> springEntities;
    private ImmutableArray<Entity> enemyEntities;

    private boolean playerHasBeenHit;

    public CollisionSystem(final World world, final NewGameScreen newGameScreen, final AudioUtils audioUtils){
        barsFamily = Family.all(BarComponent.class).get();
        coinsFamily = Family.all(CoinComponent.class).get();
        springsFamily = Family.all(SpringComponent.class).get();
        enemiesFamily = Family.all(EnemyComponent.class).get();
        playerHasBeenHit = false;
        final Storage storage = new Storage();
        final Sound jumpSoundEffects = audioUtils.getJumpSoundEffect();
        final Sound coinSoundEffects = audioUtils.getCoinSoundEffect();
        final Sound highJumpSoundEffects = audioUtils.getHighJumpSoundEffect();
        final Sound hitSoundEffects = audioUtils.getHitSoundEffect();
        world.setContactListener(new ContactListener(){
            @Override
            public void beginContact(Contact contact){
                final BodyComponent playerBodyComponent = Mappers.bodyComponentComponentMapper.get(newGameScreen.playerEntity);
                boolean toCheckSprings = true;
                boolean toCheckBars = true;
                boolean toCheckCoins = true;
                for(Entity enemyEntity : enemyEntities){
                    final BodyComponent enemyBodyComponent = Mappers.bodyComponentComponentMapper.get(enemyEntity);
                    if(contact.getFixtureA().getBody() == enemyBodyComponent.body && contact.getFixtureB().getBody() == playerBodyComponent.body ||
                            contact.getFixtureA().getBody() == playerBodyComponent.body && contact.getFixtureB().getBody() == enemyBodyComponent.body){
                        toCheckSprings = false;
                        playerHasBeenHit = true;
                        if(storage.getToPlaySoundEffects()){
                            hitSoundEffects.play();
                        }
                        Filter filter = new Filter();
                        filter.categoryBits = Constants.NO_COLLISIONS;
                        filter.maskBits = Constants.NO_COLLISIONS;
                        playerBodyComponent.fixture.setFilterData(filter);

                        if(!playerBodyComponent.fixture.isSensor()){
                            playerBodyComponent.fixture.setSensor(true);
                        }

                        Vector2 velocity = playerBodyComponent.body.getLinearVelocity();
                        velocity.set(velocity.x, -800);
                        playerBodyComponent.body.setLinearVelocity(velocity);
                    }
                }
                if(toCheckSprings) {
                    for (Entity springEntity : springEntities) {
                        final BodyComponent springBodyComponent = Mappers.bodyComponentComponentMapper.get(springEntity);
                        if (contact.getFixtureA().getBody() == springBodyComponent.body && contact.getFixtureB().getBody() == playerBodyComponent.body ||
                                contact.getFixtureA().getBody() == playerBodyComponent.body && contact.getFixtureB().getBody() == springBodyComponent.body) {
                            toCheckBars = false;
                            if (playerBodyComponent.body.getLinearVelocity().y <= 0) {
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (springBodyComponent.fixture.isSensor()) {
                                            springBodyComponent.fixture.setSensor(false);
                                        }

                                        if (storage.getToPlaySoundEffects()) {
                                            highJumpSoundEffects.play();
                                        }

                                        Vector2 velocity = playerBodyComponent.body.getLinearVelocity();
                                        velocity.set(velocity.x, 800);
                                        playerBodyComponent.body.setLinearVelocity(velocity);
                                        //playerBodyComponent.body.applyLinearImpulse(0, 1000, playerBodyComponent.body.getPosition().x, playerBodyComponent.body.getPosition().y, true);
                                        new Thread(new Runnable() {
                                            @Override
                                            public void run() {
                                                for (int i = 0; i < 14; i++) {
                                                    try {
                                                        Thread.sleep(100);
                                                    } catch (InterruptedException e) {
                                                        e.printStackTrace();
                                                    }
                                                    if(!playerHasBeenHit) {
                                                        Vector2 velocity = playerBodyComponent.body.getLinearVelocity();
                                                        velocity.set(velocity.x, 1000);
                                                        playerBodyComponent.body.setLinearVelocity(velocity);
                                                    }else{
                                                        break;
                                                    }
                                                }
                                            }
                                        }).start();
                                    }
                                }).start();
                            } else {
                                if (!springBodyComponent.fixture.isSensor()) {
                                    springBodyComponent.fixture.setSensor(true);
                                }
                            }
                        }
                    }
                }
                if(toCheckBars) {
                    for(Entity barEntity : barEntities){
                        final SpriteComponent barSpriteComponent = Mappers.spriteComponentComponentMapper.get(barEntity);
                        final BodyComponent barBodyComponent = Mappers.bodyComponentComponentMapper.get(barEntity);
                        if (contact.getFixtureA().getBody() == barBodyComponent.body && contact.getFixtureB().getBody() == playerBodyComponent.body ||
                                contact.getFixtureA().getBody() == playerBodyComponent.body && contact.getFixtureB().getBody() == barBodyComponent.body) {
                            toCheckCoins = false;
                            //if(playerSpriteComponent.sprite.getY() >= barSpriteComponent.sprite.getY() + barSpriteComponent.sprite.getHeight() * 1 / 2){
                            if (playerBodyComponent.body.getLinearVelocity().y <= 0) {
                                if (Math.random() >= 0.7f) {
                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (barBodyComponent.fixture.isSensor()) {
                                                barBodyComponent.fixture.setSensor(false);
                                            }

                                            if (storage.getToPlaySoundEffects()) {
                                                jumpSoundEffects.play();
                                            }

                                            Vector2 velocity = playerBodyComponent.body.getLinearVelocity();
                                            velocity.set(velocity.x, 800);
                                            playerBodyComponent.body.setLinearVelocity(velocity);
                                            //playerBodyComponent.body.applyLinearImpulse(0, 1000, playerBodyComponent.body.getPosition().x, playerBodyComponent.body.getPosition().y, true);

                                            new Thread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    for (int i = 0; i < 2; i++) {
                                                        try {
                                                            Thread.sleep(200);
                                                        } catch (InterruptedException e) {
                                                            e.printStackTrace();
                                                        }
                                                        if(!playerHasBeenHit) {
                                                            Vector2 velocity = playerBodyComponent.body.getLinearVelocity();
                                                            velocity.set(velocity.x, 1000);
                                                            playerBodyComponent.body.setLinearVelocity(velocity);
                                                        }else{
                                                            break;
                                                        }
                                                    }
                                                }
                                            }).start();

                                            try {
                                                Thread.sleep(250);
                                            } catch (InterruptedException e) {
                                                e.printStackTrace();
                                            }
                                            Filter filter = new Filter();
                                            filter.categoryBits = Constants.BAR_SENSOR;
                                            filter.maskBits = Constants.NO_COLLISIONS;
                                            barBodyComponent.fixture.setFilterData(filter);
                                            barSpriteComponent.sprite.setAlpha(0);
                                        }
                                    }).start();
                                } else {
                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (barBodyComponent.fixture.isSensor()) {
                                                barBodyComponent.fixture.setSensor(false);
                                            }

                                            if (storage.getToPlaySoundEffects()) {
                                                jumpSoundEffects.play();
                                            }

                                            Vector2 velocity = playerBodyComponent.body.getLinearVelocity();
                                            velocity.set(velocity.x, 800);
                                            playerBodyComponent.body.setLinearVelocity(velocity);
                                            //playerBodyComponent.body.applyLinearImpulse(0, 1000, playerBodyComponent.body.getPosition().x, playerBodyComponent.body.getPosition().y, true);
                                            new Thread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    for (int i = 0; i < 2; i++) {
                                                        try {
                                                            Thread.sleep(200);
                                                        } catch (InterruptedException e) {
                                                            e.printStackTrace();
                                                        }
                                                        if(!playerHasBeenHit) {
                                                            Vector2 velocity = playerBodyComponent.body.getLinearVelocity();
                                                            velocity.set(velocity.x, 1000);
                                                            playerBodyComponent.body.setLinearVelocity(velocity);
                                                        }else{
                                                            break;
                                                        }
                                                    }
                                                }
                                            }).start();
                                        }
                                    }).start();
                                }
                            }else{
                                if(!barBodyComponent.fixture.isSensor()){
                                    barBodyComponent.fixture.setSensor(true);
                                }
                            }
                        }
                    }
                }
                if(toCheckCoins){
                    for(final Entity coinEntity : coinEntities){
                        final BodyComponent coinBodyComponent = Mappers.bodyComponentComponentMapper.get(coinEntity);
                        if(contact.getFixtureA().getBody() == coinBodyComponent.body && contact.getFixtureB().getBody() == playerBodyComponent.body ||
                                contact.getFixtureA().getBody() == playerBodyComponent.body && contact.getFixtureB().getBody() == coinBodyComponent.body){
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    if (storage.getToPlaySoundEffects()) {
                                        coinSoundEffects.play();
                                    }
                                    Score.setScore(Score.getScore() + 10);
                                    try {
                                        Thread.sleep(50);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                    getEngine().removeEntity(coinEntity);
                                }
                            }).start();
                        }
                    }
                }
            }

            @Override
            public void endContact(Contact contact){}

            @Override
            public void preSolve(Contact contact, Manifold oldManifold){}

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse){}
        });
    }

    @Override
    public void addedToEngine(Engine engine){
        super.addedToEngine(engine);
        barEntities = engine.getEntitiesFor(barsFamily);
        coinEntities = engine.getEntitiesFor(coinsFamily);
        springEntities = engine.getEntitiesFor(springsFamily);
        enemyEntities = engine.getEntitiesFor(enemiesFamily);
    }

    @Override
    public void removedFromEngine(Engine engine){
        super.removedFromEngine(engine);
        barEntities = null;
        coinEntities = null;
        springEntities = null;
        enemyEntities = null;
    }

    @Override
    public void update(float deltaTime){}
}
