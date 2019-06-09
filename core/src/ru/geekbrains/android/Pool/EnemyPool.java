package ru.geekbrains.android.Pool;

import com.badlogic.gdx.audio.Sound;

import ru.geekbrains.android.Sprites.Enemy;
import ru.geekbrains.android.Sprites.PlayerShip;
import ru.geekbrains.android.base.SpritesPool;
import ru.geekbrains.android.math.Rect;

public class EnemyPool extends SpritesPool<Enemy> {
    private BulletPool bulletPool;
    private ExplosionPool explosionPool;
    private Sound bulletSound;
    private Rect worldBounds;
    private PlayerShip playerShip;

    public EnemyPool(BulletPool bulletPool, Sound bulletSound,
                     Rect worldBounds, ExplosionPool explosionPool, PlayerShip playerShip) {
        this.bulletPool = bulletPool;
        this.explosionPool = explosionPool;
        this.bulletSound = bulletSound;
        this.worldBounds = worldBounds;
        this.playerShip = playerShip;
    }

    @Override
    public Enemy newObject() {
        return new Enemy(bulletPool,bulletSound,worldBounds,explosionPool, playerShip);
    }
}
