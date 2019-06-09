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
import ru.geekbrains.android.Sprites.Bullet;
import ru.geekbrains.android.Sprites.ButtonNewGame;
import ru.geekbrains.android.Sprites.Enemy;
import ru.geekbrains.android.Sprites.MessageGameOver;
import ru.geekbrains.android.Sprites.PlayerShip;
import ru.geekbrains.android.Sprites.Star;
import ru.geekbrains.android.Utils.EnemyGenerator;
import ru.geekbrains.android.base.BaseScreen;
import ru.geekbrains.android.math.Rect;

public class GameScreen extends BaseScreen {

    private enum State {PLAYING, PAUSE, GAME_OVER}

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
    private State state;
    private MessageGameOver messageGameOver;
    private ButtonNewGame buttonNewGame;


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
        playerShip = new PlayerShip(atlas, bulletPool, laserSound, explosionPool);
        enemyPool = new EnemyPool(bulletPool, bulletSound, worldBounds, explosionPool, playerShip);
        enemyGenerator = new EnemyGenerator(worldBounds, enemyPool, atlas);
        starArray = new Star[STAR_COUNT];
        for (int i = 0; i < STAR_COUNT; i++) {
            starArray[i] = new Star(atlas);
        }
        messageGameOver = new MessageGameOver(atlas);
        buttonNewGame = new ButtonNewGame(atlas,this);
        state = State.PLAYING;
    }

    @Override
    public void pause() {
        super.pause();
        if (state == State.GAME_OVER) {
            return;
        }
        state = State.PAUSE;
        music.pause();
    }

    @Override
    public void resume() {
        super.resume();
        if (state == State.GAME_OVER) {
            return;
        }
        state = State.PLAYING;
        music.play();
    }

    @Override
    public void render(float delta) {
        update(delta);
        checkCollisions();
        freeAllDestroyedActiveObjects();
        draw();
    }

    private void update(float delta) {
        for (Star star : starArray) {
            star.update(delta);
        }
        explosionPool.updateActiveSprites(delta);
        if (state == State.PLAYING) {
            playerShip.update(delta);
            bulletPool.updateActiveSprites(delta);
            enemyPool.updateActiveSprites(delta);
            enemyGenerator.generate(delta);
        }
    }

    private void checkCollisions() {
        if (state != State.PLAYING) {
            return;
        }
        List<Enemy> activeEnemies = enemyPool.getActiveObjects();
        List<Bullet> bulletList = bulletPool.getActiveObjects();
        for (Enemy enemy : activeEnemies) {
            if (enemy.isDestroyed()) {
                continue;
            }
            float minDist = enemy.getHalfWidth() + playerShip.getHalfWidth();
            if (enemy.pos.dst(playerShip.pos) < minDist) {
                enemy.destroy();
                playerShip.destroy();
                state = State.GAME_OVER;
            }
            for (Bullet bullet : bulletList) {
                if (bullet.getOwner() != playerShip || bullet.isDestroyed()) {
                    continue;
                }
                if (enemy.isBullerCollsion(bullet)) {
                    enemy.damage(bullet.getDamage());
                    bullet.destroy();
                    if (playerShip.isDestroyed()) {
                        state = State.GAME_OVER;
                    }
                }
            }
        }
        for (Bullet bullet : bulletList) {
            if (bullet.getOwner() == playerShip || bullet.isDestroyed()) {
                continue;
            }
            if (playerShip.isBulletCollision(bullet)) {
                playerShip.damage(bullet.getDamage());
                bullet.destroy();
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
        explosionPool.drawActiveSprites(batch);
        if (state == State.PLAYING) {
            playerShip.draw(batch);
            bulletPool.drawActiveSprites(batch);
            enemyPool.drawActiveSprites(batch);
        } else if (state == State.GAME_OVER) {
            messageGameOver.draw(batch);
            buttonNewGame.draw(batch);
        }
        batch.end();

    }

    @Override
    public void resize(Rect worldBounds) {
        background.resize(worldBounds);
        playerShip.resize(worldBounds);
        for (Star star : starArray) {
            star.resize(worldBounds);
        }
        messageGameOver.resize(worldBounds);
        buttonNewGame.resize(worldBounds);
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
        if (state == State.PLAYING) {
            playerShip.keyDown(keycode);
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (state == State.PLAYING) {
            playerShip.keyUp(keycode);
        }
        return false;
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        if (state == State.PLAYING) {
            playerShip.touchDown(touch, pointer);
        } else if (state == State.GAME_OVER){
            buttonNewGame.touchDown(touch, pointer);
        }
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        if (state == State.PLAYING) {
            playerShip.touchUp(touch, pointer);
        }else if (state == State.GAME_OVER){
            buttonNewGame.touchUp(touch, pointer);
        }
        return false;
    }

    public void newGame() {
        state = State.PLAYING;
        playerShip.newGame();
        bulletPool.freeAllActiveObjects();
        explosionPool.freeAllActiveObjects();
        enemyPool.freeAllActiveObjects();

    }
}
