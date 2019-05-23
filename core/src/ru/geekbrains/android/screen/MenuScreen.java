package ru.geekbrains.android.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import ru.geekbrains.android.base.BaseScreen;

public class MenuScreen extends BaseScreen {

    private static final float LEN = 3f;
    private SpriteBatch batch;
    private Texture img;
    private Texture playerShip;
    private Vector2 touch;
    private Vector2 v;
    private Vector2 pos;
    private  Vector2 buff;

    @Override
    public void show() {
        super.show();
        batch = new SpriteBatch();
        img = new Texture("background.jpg");
        playerShip = new Texture("PlayerShip.png");
        touch = new Vector2();
        v = new Vector2();
        pos = new Vector2();
        buff = new Vector2();



    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(img, 0, 0, 350, 700);
        batch.draw(playerShip, pos.x,pos.y,100,100);
        batch.end();
        buff.set (touch);
        if (buff.sub(pos).len() <= LEN) {
            pos.set(touch);
        }
        else {
            pos.add(v);
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        img.dispose();
        super.dispose();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        super.touchDown(screenX, screenY, pointer, button);
        touch.set(screenX, Gdx.graphics.getHeight() - screenY);
        v.set(touch.cpy().sub(pos)).setLength(LEN);
        System.out.println("touch.x = " + touch.x + "; touch.y" + touch.y);
        return false;
    }

}
