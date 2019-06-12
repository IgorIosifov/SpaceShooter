package ru.geekbrains.android.Sprites;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.android.Pool.BulletPool;
import ru.geekbrains.android.Pool.ExplosionPool;
import ru.geekbrains.android.math.Rect;

public class Enemy extends Ship {

    private enum State {DESCENT, FIGHT}
    private State state;
    private Vector2 descentV = new Vector2(0,-0.15f);
    private  PlayerShip playerShip;


    public Enemy(BulletPool bulletPool, Sound bulletSound, Rect worldBounds
    , ExplosionPool explosionPool, PlayerShip playerShip) {
        this.bulletPool = bulletPool;
        this.explosionPool = explosionPool;
        this.bulletSound = bulletSound;
        this.worldBounds = worldBounds;
        this.v = new Vector2();
        this.v0 = new Vector2();
        this.bulletV = new Vector2();
        this.playerShip = playerShip;
    }

    @Override
    public void update(float delta) {
        super.update(delta);

        switch (state) {
            case DESCENT:
                if (getTop()<worldBounds.getTop()) {
                    v.set(v0);
                    state = State.FIGHT;
                }
                break;
            case FIGHT:
                reloadTimer += delta;
                if (reloadTimer > reloadInterval) {
                    reloadTimer = 0f;
                    shoot();
                }
                if (getBottom()<worldBounds.getBottom()){
                    destroy();
                    playerShip.damage(damage);
                }
                break;
        }

    }

    public void set(
            TextureRegion[] regions,
            Vector2 v0,
            TextureRegion bulletRegion,
            float bulletHeight,
            float bulletVY,
            int damage,
            float reloadInterval,
            float height,
            int hp
    ) {

        this.regions = regions;
        this.v0.set(v0);
        this.bulletRegion = bulletRegion;
        this.bulletHeight = bulletHeight;
        this.bulletV.set(0, bulletVY);
        this.damage = damage;
        this.reloadInterval = reloadInterval;
        this.reloadTimer = reloadInterval;
        setHeightProportion(height);
        this.hp = hp;
        this.v.set(descentV);
        this.state = State.DESCENT;
    }

    public boolean isBulletCollision(Rect bullet){
        return !(
                bullet.getRight() < getLeft()
                || bullet.getLeft()>getRight()
                || bullet.getBottom() > getTop()
                || bullet.getTop() < pos.y
                );
    }
}
