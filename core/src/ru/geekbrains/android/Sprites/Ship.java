package ru.geekbrains.android.Sprites;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.android.Pool.BulletPool;
import ru.geekbrains.android.base.Sprite;
import ru.geekbrains.android.math.Rect;

public abstract class Ship extends Sprite {

    protected BulletPool bulletPool;
    protected TextureRegion bulletRegion;

    public Vector2 v;
    protected Vector2 v0;
    protected Vector2 bulletV;
    protected float bulletHeight;

    protected Rect worldBounds;

    protected float reloadInterval;
    protected float reloadTimer;

    protected int damage;
    protected int hp;

    protected Sound bulletSound;

    public Ship(TextureRegion region, int rows, int cols, int frames) {
        super(region, rows, cols, frames);
    }

    public Ship () {

    }

    @Override
    public void update(float delta) {
        pos.mulAdd(v, delta);
        reloadTimer += delta;
        if (reloadTimer > reloadInterval) {
            reloadTimer = 0f;
            shoot();
        }
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
    }

    protected void shoot() {
        bulletSound.play();
        Bullet bullet = bulletPool.obtain();
        bullet.set (this, bulletRegion, pos,bulletV,
                bulletHeight,worldBounds,damage);
    }
}
