package ru.geekbrains.android.Sprites;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.android.Pool.BulletPool;
import ru.geekbrains.android.Pool.ExplosionPool;
import ru.geekbrains.android.base.Sprite;
import ru.geekbrains.android.math.Rect;

public abstract class Ship extends Sprite {
    private enum State {DESCENT, FIGHT}

    private State state;

    private Vector2 descentV = new Vector2(0, -0.15f);

    protected BulletPool bulletPool;
    protected ExplosionPool explosionPool;
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

    private float damageAnimateInterval = 0.1f;
    private float damageAnimateTimer = damageAnimateInterval;


    public Ship(TextureRegion region, int rows, int cols, int frames) {
        super(region, rows, cols, frames);
    }

    public Ship() {

    }

    @Override
    public void update(float delta) {
        super.update(delta);
        pos.mulAdd(v, delta);
        damageAnimateTimer += delta;
        if (damageAnimateTimer >= damageAnimateInterval) {
            frame = 0;
        }

    }


    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
    }

    @Override
    public void destroy() {
        super.destroy();
        boom();
    }

    public void damage(int damage) {
        hp -= damage;
        if (hp <= 0) {
            hp = 0;
            destroy();
        }
        frame = 1;
        damageAnimateTimer = 0f;
    }

    public int getHp() {
        return hp;
    }

    protected void shoot() {
        bulletSound.play();
        Bullet bullet = bulletPool.obtain();
        bullet.set(this, bulletRegion, pos, bulletV,
                bulletHeight, worldBounds, damage);
    }

    private void boom() {
        Explosion explosion = explosionPool.obtain();
        explosion.set(getHeight(), pos);
    }
}
