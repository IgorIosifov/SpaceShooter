package ru.geekbrains.android.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import java.util.List;

import ru.geekbrains.android.Pool.BulletPool;
import ru.geekbrains.android.Pool.EnemyPool;
import ru.geekbrains.android.Pool.ExplosionPool;
import ru.geekbrains.android.Sprites.Background;
import ru.geekbrains.android.Sprites.Enemy;
import ru.geekbrains.android.Sprites.Explosion;
import ru.geekbrains.android.Sprites.PlayerShip;
import ru.geekbrains.android.Sprites.Star;
import ru.geekbrains.android.Utils.EnemyGenerator;
import ru.geekbrains.android.base.BaseScreen;
import ru.geekbrains.android.math.Rect;

public class GameScreen extends BaseScreen {
    private static final int STAR_COUNT = 64;
    private Texture bg;
    private Background background;
    private PlayerShip playerShip;
    private TextureAtlas atlas;
    private Star[] starArray;
    private BulletPool bulletPool;
    private ExplosionPool explosionPool;
    private EnemyPool enemyPool;
    private Sound laserSound;
    private Sound explosionSound;
    private Sound bulletSound;
    private Music music;
    private EnemyGenerator enemyGenerator;


    @Override
    public void show() {
        super.show();
        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/music.mp3"));
        music.setLooping(true);
        music.play();
        laserSound = Gdx.audio.newSound
                (Gdx.files.internal("sounds/laser.wav"));
        explosionSound = Gdx.audio.newSound
                (Gdx.files.internal("sounds/explosion.wav"));
        bulletSound = Gdx.audio.newSound
                (Gdx.files.internal("sounds/bullet.mp3"));
        bg = new Texture("textures/background.jpg");
        background = new Background(new TextureRegion(bg));
        atlas = new TextureAtlas("textures/mainAtlas.atlas");
        bulletPool = new BulletPool();
        explosionPool = new ExplosionPool(atlas, explosionSound);
        enemyPool = new EnemyPool(bulletPool, bulletSound, worldBounds);
        playerShip = new PlayerShip(atlas, bulletPool, laserSound);
        enemyGenerator = new EnemyGenerator(worldBounds, enemyPool, atlas);
        starArray = new Star[STAR_COUNT];
        for (int i = 0; i < STAR_COUNT; i++) {
            starArray[i] = new Star(atlas);
        }
    }


    @Override
    public void render(float delta) {
        update(delta);
        freeAllDestroyedActiveObjects();
        draw();
    }

    private void update(float delta) {
        playerShip.update(delta);
        for (Star star : starArray) {
            star.update(delta);
        }
        bulletPool.updateActiveSprites(delta);
        explosionPool.updateActiveSprites(delta);
        enemyPool.updateActiveSprites(delta);
        enemyGenerator.generate(delta);

        //collision checking block
        List<Enemy> activeEnemies = this.enemyPool.getActiveObjects();
        for (int i = 0; i < activeEnemies.size(); i++) {
            if (activeEnemies.get(i).pos.cpy().sub(playerShip.pos).len()<playerShip.getHalfHeight()) { //collision condition
                Explosion explosion = explosionPool.obtain();
                explosion.set(0.15f, activeEnemies.get(i).pos);
                activeEnemies.get(i).destroy();
                // destroying an enemy ship if out of worldBounds
            } else if (activeEnemies.get(i).getTop() < worldBounds.getBottom()) {
                activeEnemies.get(i).destroy();
            }

        }

    }

    private void freeAllDestroyedActiveObjects() {
        bulletPool.freeAllDestroyedActiveSprites();
        explosionPool.freeAllDestroyedActiveSprites();
        enemyPool.freeAllDestroyedActiveSprites();
    }

    private void draw() {
        Gdx.gl.glClearColor(0, 1, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        for (Star star : starArray) {
            star.draw(batch);
        }
        playerShip.draw(batch);
        bulletPool.drawActiveSprites(batch);
        explosionPool.drawActiveSprites(batch);
        enemyPool.drawActiveSprites(batch);
        batch.end();
    }

    @Override
    public void resize(Rect worldBounds) {
        background.resize(worldBounds);
        playerShip.resize(worldBounds);
        for (Star star : starArray) {
            star.resize(worldBounds);
        }
    }

    @Override
    public void dispose() {
        bg.dispose();
        atlas.dispose();
        bulletPool.dispose();
        explosionPool.dispose();
        laserSound.dispose();
        music.dispose();
        explosionSound.dispose();
        enemyPool.dispose();
        super.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        playerShip.keyDown(keycode);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        playerShip.keyUp(keycode);
        return false;
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        playerShip.touchDown(touch, pointer);
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        playerShip.touchUp(touch, pointer);
        return false;
    }


}
