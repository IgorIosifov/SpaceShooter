package ru.geekbrains.android.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.android.Sprites.Background;
import ru.geekbrains.android.Sprites.PlayerShip;
import ru.geekbrains.android.base.BaseScreen;
import ru.geekbrains.android.math.Rect;

public class MenuScreen extends BaseScreen {

//    private SpriteBatch batch;
    private Texture psh;
    private Texture bg;
    private Background background;
    private PlayerShip playerShip;



    @Override
    public void show() {
        super.show();
//        batch = new SpriteBatch();
        bg = new Texture("background.jpg");
        background = new Background(new TextureRegion(bg));
        psh = new Texture("PlayerShip.png");
        playerShip = new PlayerShip(new TextureRegion(psh));
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        draw();

    }

    private void update(float delta) {
    playerShip.update(delta);
    }

    private void draw() {
        Gdx.gl.glClearColor(0, 1, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        playerShip.draw(batch);
        batch.end();
    }

    @Override
    public void dispose() {

        bg.dispose();
        psh.dispose();
        super.dispose();
    }

    @Override
    public void resize(Rect worldBounds) {
        background.resize(worldBounds);
        playerShip.resize(worldBounds);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        playerShip.touchDown(touch, pointer);
        return false;
    }
}
