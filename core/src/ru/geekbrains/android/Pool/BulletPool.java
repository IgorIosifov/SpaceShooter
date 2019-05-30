package ru.geekbrains.android.Pool;

import ru.geekbrains.android.Sprites.Bullet;
import ru.geekbrains.android.base.SpritesPool;

public class BulletPool extends SpritesPool <Bullet> {

    @Override
    protected Bullet newObject() {
        return new Bullet();
    }
}
