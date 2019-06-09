package ru.geekbrains.android.Sprites;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.geekbrains.android.base.Sprite;
import ru.geekbrains.android.math.Rect;

public class MessageGameOver extends Sprite {
    public MessageGameOver(TextureAtlas atlas) {
        super(atlas.findRegion("message_game_over"));

    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setHeightProportion(0.07f);
        setBottom(0.009f);
    }
}
