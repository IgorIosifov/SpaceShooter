package ru.geekbrains.android.Sprites;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.android.base.Sprite;
import ru.geekbrains.android.math.Rect;


public class PlayerShip extends Sprite {
    private static final float LEN = 0.01f;
    private Vector2 v;
    private Vector2 buff;
    private Vector2 touch;


    public PlayerShip(TextureAtlas atlas) {
        super(atlas.findRegion("PlayerShipOk"));
        v = new Vector2();
        buff = new Vector2();
        touch = new Vector2();
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.2f);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        buff.set(touch);
        if (buff.sub(pos).len() <= LEN) {
            pos.set(touch);
        } else {
            pos.add(v);
        }
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        this.touch = touch;
        v.set(touch.cpy().sub(pos)).setLength(LEN);
        return false;
    }
}
