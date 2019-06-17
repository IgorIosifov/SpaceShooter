package ru.geekbrains.android.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.android.Sprites.Background;
import ru.geekbrains.android.Sprites.ButtonExit;
import ru.geekbrains.android.Sprites.ButtonPlay;
import ru.geekbrains.android.Sprites.Logo;
import ru.geekbrains.android.Sprites.Star;
import ru.geekbrains.android.base.BaseScreen;
import ru.geekbrains.android.math.Rect;

public class MenuScreen extends BaseScreen {

    private static  final int STAR_COUNT = 256;
    private Game game;
    private Texture bg;
    private Texture lg;
    private Background background;
    private Logo logo;
    private TextureAtlas atlas;
    private Star[] starArray;
    private ButtonExit buttonExit;
    private ButtonPlay buttonPlay;

    public MenuScreen(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
        super.show();
        bg = new Texture("textures/background.jpg");
        lg = new Texture("textures/logo.png");
        background = new Background(new TextureRegion(bg));
        logo = new Logo(new TextureRegion(lg));
        atlas = new TextureAtlas("textures/menuAtlas.atlas");
        starArray = new Star [STAR_COUNT];
        for (int i = 0; i < STAR_COUNT; i++) {
            starArray[i] = new Star(atlas);
        }
        buttonExit = new ButtonExit(atlas);
        buttonPlay = new ButtonPlay(atlas, game);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        draw();

    }

    private void update(float delta) {
    for (Star star : starArray){
    star.update(delta);
    }
    logo.update(delta,worldBounds);
    }

    private void draw() {
        Gdx.gl.glClearColor(0, 1, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        for (Star star : starArray){
            star.draw(batch);
        }
        logo.draw(batch);
        buttonExit.draw(batch);
        buttonPlay.draw(batch);
        batch.end();
    }

    @Override
    public void dispose() {

        bg.dispose();
        lg.dispose();
        atlas.dispose();
        super.dispose();
    }

    @Override
    public void resize(Rect worldBounds) {
        background.resize(worldBounds);
        for (Star star : starArray){
            star.resize(worldBounds);
        }
        buttonExit.resize(worldBounds);
        buttonPlay.resize(worldBounds);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        buttonExit.touchDown(touch,pointer);
        buttonPlay.touchDown(touch,pointer);
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        buttonExit.touchUp(touch,pointer);
        buttonPlay.touchUp(touch,pointer);
        return false;
    }
}
