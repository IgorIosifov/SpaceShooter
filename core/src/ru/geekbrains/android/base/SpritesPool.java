package ru.geekbrains.android.base;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.List;

public abstract class SpritesPool<T extends Sprite> {

    protected final List<T> activeObjects = new ArrayList<T>();
    protected final List<T> freeObjects = new ArrayList<T>();

    protected abstract T newObject();

    public T obtain() {
        T Object;
        if (freeObjects.isEmpty()) {
            Object = newObject();
        } else {
            Object = freeObjects.remove(freeObjects.size() - 1);
        }
        activeObjects.add(Object);
        System.out.println(getClass().getName()+"active/free:" + activeObjects.size() +
                "/"+freeObjects.size());
        return Object;
    }

    public void updateActiveSprites(float delta) {
        for (Sprite sprite : activeObjects) {
            if (!sprite.isDestroyed()) {
                sprite.update(delta);
            }
        }
    }

    public void drawActiveSprites(SpriteBatch batch) {
        for (Sprite sprite : activeObjects) {
            if (!sprite.isDestroyed()) {
                sprite.draw(batch);
            }
        }
    }

    public void freeAllDestroyedActiveSprites() {
        for (int i = 0; i < activeObjects.size(); i++) {
            T sprite = activeObjects.get(i);
            if (sprite.isDestroyed()) {
                free(sprite);
                i--;
                sprite.flushDestroy();
            }
        }
    }

    public void dispose () {
        activeObjects.clear();
        freeObjects.clear();
    }

    public List<T> getActiveObjects() {
        return activeObjects;
    }

    public void freeAllActiveObjects () {
        freeObjects.addAll(activeObjects);
        activeObjects.clear();
    }

    private void free(T object) {
        if (activeObjects.remove(object)) {
            freeObjects.add(object);
        }
        System.out.println(getClass().getName() + "active/free:" +
                activeObjects.size() +
                "/"+freeObjects.size());
    }
}





