package ru.geekbrains.android.Sprites;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import ru.geekbrains.android.base.ScaledTouchUpButton;
import ru.geekbrains.android.math.Rect;
import ru.geekbrains.android.screen.GameScreen;

public class ButtonNewGame extends ScaledTouchUpButton {
    private GameScreen gameScreen;
    public ButtonNewGame(TextureAtlas atlas, GameScreen gameScreen) {
        super(atlas.findRegion("button_new_game"));
        this.gameScreen = gameScreen;

    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setHeightProportion(0.05f);
        setBottom(-0.15f);
    }

    @Override
    public void action() {
    gameScreen.newGame();
    }
}
