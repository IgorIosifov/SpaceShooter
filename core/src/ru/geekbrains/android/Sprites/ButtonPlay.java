package ru.geekbrains.android.Sprites;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.geekbrains.android.base.ScaledTouchUpButton;
import ru.geekbrains.android.math.Rect;
import ru.geekbrains.android.screen.GameScreen;

public class ButtonPlay extends ScaledTouchUpButton {

    private Game game;
    public ButtonPlay(TextureAtlas atlas, Game game) {
        super(atlas.findRegion("btPlay"));
        this.game = game;
        setHeightProportion(0.06f);
    }

    @Override
    public void resize(Rect worldBounds) {
        setLeft(worldBounds.getLeft() + 0.05f);
        setBottom(worldBounds.getBottom()+0.04f);
    }

    @Override
    public void action() {
        game.setScreen(new GameScreen());
    }
}
