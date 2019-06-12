package ru.geekbrains.android.Sprites;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.geekbrains.android.base.Sprite;
import ru.geekbrains.android.math.Rect;

public class Logo extends Sprite {
    private float interval = 0.01f;
    private float timer = interval;
    private float heightProportion = 0.5f;
    private float deltaY = 0.10f;

    public Logo(TextureRegion region) {
        super(region);
    }




    public void update(float delta, Rect worldBounds) {
        super.update(delta);
            timer += delta;
            if (heightProportion >0f) {
                if (timer > interval) {
                    timer = 0f;
                    heightProportion = heightProportion - 0.0004f;
                    setHeightProportion(heightProportion);
                    pos.set(worldBounds.pos.cpy().set(0.0f,worldBounds.pos.y-0.3f+deltaY));
                    deltaY = deltaY+0.0005f;
                }
            } else {
                heightProportion=0f;
            }


    }
}
