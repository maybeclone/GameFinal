package com.slient.gamefinal.models;

import android.graphics.Bitmap;
import android.graphics.Rect;

import com.slient.gamefinal.main.GameView;
import com.slient.gamefinal.main.MainActivity;
import com.slient.gamefinal.utils.Assets;
import com.slient.gamefinal.utils.Painter;
import com.slient.gamefinal.utils.RandomNumberGenerator;

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

    public Bar(float y, int width, int height, int index) {
        this.index = index;
        isPassed = false;
        speed = 1600;
        this.y = y;
        this.width = width;
        this.height = height;
        int gameW = MainActivity.GAME_WIDTH;
        this.x = gameW + RandomNumberGenerator.getRandIntBetween(index * gameW / 3, (index + 1) * gameW / 3);

        this.rect = new Rect((int)x, (int)y, (int)x + width, (int)y + height);
        this.bar = Assets.barImpedimentBackground;
    }

    public void update(float delta) {
        x -= speed / GameView.FPS;

        if (x < -width) {
            int gameW = MainActivity.GAME_WIDTH;
            this.x = gameW + RandomNumberGenerator.getRandIntBetween(index * gameW / 3, (index + 1) * gameW / 3);
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
        return new Rect(rect.left + 35, rect.top + 30, rect.right - 40, rect.bottom);
    }

}
