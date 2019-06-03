package ru.geekbrains.android.Pool;

import com.badlogic.gdx.audio.Sound;

import ru.geekbrains.android.Sprites.Enemy;
import ru.geekbrains.android.base.SpritesPool;
import ru.geekbrains.android.math.Rect;

public class EnemyPool extends SpritesPool<Enemy> {
    private BulletPool bulletPool;
    private Sound bulletSound;
    private Rect worldBounds;

    public EnemyPool(BulletPool bulletPool, Sound bulletSound, Rect worldBounds) {
        this.bulletPool = bulletPool;
        this.bulletSound = bulletSound;
        this.worldBounds = worldBounds;
    }

    @Override
    public Enemy newObject() {
        return new Enemy(bulletPool,bulletSound,worldBounds);
    }
}
