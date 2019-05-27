package ru.geekbrains.android.Sprites;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.android.base.Sprite;
import ru.geekbrains.android.math.RandomFloat;
import ru.geekbrains.android.math.Rect;

public class Star extends Sprite {

    private Vector2 v;
    private Rect worldbounds;
    private float height;


    public Star(TextureAtlas atlas) {
        super(atlas.findRegion("star"));
        float vX = RandomFloat.nextFloat(-0.005f, 0.005f);
        float vY = RandomFloat.nextFloat(-0.07f, -0.5f);
        v = new Vector2(vX, vY);
        height = RandomFloat.nextFloat(0.05f, 0.1f);
        setHeightProportion(height);
    }

    @Override
    public void update(float delta) {
        if ( height>= 0.1f) {
            height = 0.05f;
        } else {
            height += 0.0001f;
        }
        setHeightProportion(height);

        pos.mulAdd(v, delta);
        if (getRight() < worldbounds.getLeft()) {
            setLeft(worldbounds.getRight());
        }
        if (getLeft() > worldbounds.getRight()) {
            setRight(worldbounds.getLeft());
        }
        if (getTop() < worldbounds.getBottom()) {
            setBottom(worldbounds.getTop());
        }
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldbounds = worldBounds;
        float posX = RandomFloat.nextFloat(worldBounds.getLeft(), worldBounds.getRight());
        float posY = RandomFloat.nextFloat(worldBounds.getBottom(), worldBounds.getTop());
        pos.set(posX, posY);
    }

}
