package ru.geekbrains.android.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import ru.geekbrains.android.SpaceShooter;

public class DesktopLauncher {
    public static void main(String[] arg) {
        System.setProperty("user.name", "Public");
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        float aspect = 9f / 16f;
        config.width = 300;
        config.height = (int) (config.width/aspect);
        config.resizable = false;
        new LwjglApplication(new SpaceShooter(), config);
    }
}
