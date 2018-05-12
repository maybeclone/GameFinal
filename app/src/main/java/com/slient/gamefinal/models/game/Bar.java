package com.slient.gamefinal.models;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.util.Log;

import com.slient.gamefinal.main.GameView;
import com.slient.gamefinal.main.MainActivity;
import com.slient.gamefinal.utils.Assets;
import com.slient.gamefinal.utils.Config;
import com.slient.gamefinal.utils.Painter;
import com.slient.gamefinal.utils.RandomNumberGenerator;

import java.util.List;

/**
 * Created by silent on 5/9/2018.
 */
public class Bar {

    private float x, y;
    private int width, height;
    private int speed;
    private Bitmap bar;
    private static int last = MainActivity.GAME_WIDTH;
    private boolean isPassed;
    private int index;

    private Rect rect;

    public Bar(int width, int height, int index) {
        this.index = index;
        isPassed = false;
        speed = 1600;
        this.width = width;
        this.height = height;
        int gameW = MainActivity.GAME_WIDTH;
        int gameH = MainActivity.GAME_HEIGHT;
        this.bar = Assets.barImpedimentBackground;
        this.x = RandomNumberGenerator.getRandIntBetween(0, gameW - width);
        this.y = index * (gameH/3);
        this.rect = new Rect((int)x, (int)y, (int)x + width, (int)y + height);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void update(float delta) {


        y += Config.SPEED / GameView.FPS;

        if (y > MainActivity.GAME_HEIGHT) {
            int gameW = MainActivity.GAME_WIDTH;
            this.x = RandomNumberGenerator.getRandIntBetween(0, gameW - width);
            this.y = 0;
            isPassed = false;
        }

        updateRect();
    }

    public void updateRect() {
        this.rect = new Rect((int)x, (int)y, (int)x + width, (int)y + height);
    }

    public void render(Painter g) {
        g.drawImage(bar, (int)x, (int)y, width, height);
    }

    //GET SET

    public boolean isPassed() {
        return isPassed;
    }

    public void setPassed(boolean passed) {
        isPassed = passed;
    }

    public Rect getRect() {
        return new Rect(rect.left, rect.top - 50, rect.right , rect.bottom);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
