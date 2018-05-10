package com.slient.gamefinal.models;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.util.Log;

import com.slient.gamefinal.main.MainActivity;
import com.slient.gamefinal.utils.Painter;

import java.util.List;

/**
 * Created by silent on 5/9/2018.
 */
public class Character {

    private List<Bitmap> actionJump;

    private static final int JUMP_VELOCITY = -800;
    private static final int ACCEL_GRAVITY = 1800;

    private float x;
    private float y;

    private int currentBitmapIndex;
    private int velY;

    private Rect rect;
    private Bar nowBar;

    public Character(List<Bitmap> actionJump, float x, float y) {
        this.actionJump = actionJump;
        this.x = x;
        this.y = y;
        currentBitmapIndex = 0;
        rect = new Rect((int) x, (int) y, (int) x + actionJump.get(0).getWidth(),
                (int) y + actionJump.get(0).getWidth());
    }

    private Bar previousBar;

    public CharacterStatus update(float delta, List<Bar> bars) {
        if (checkStandBar(bars)) {
            currentBitmapIndex = 0;
            y -= 70;
            velY = JUMP_VELOCITY;
            updateRect();
            if(!nowBar.isPassed() && !nowBar.equals(previousBar)) {
                previousBar = nowBar;
                return CharacterStatus.INTERSECT;
            }
            nowBar.setPassed(true);
        } else {
            velY += ACCEL_GRAVITY * delta;
            float extra = velY * delta;
            y += velY * delta;
            updateIndexBitmapAction(extra);
            updateRect();

            if (isGrounded()) {
                return CharacterStatus.DIE;
            }
            if (nowBar != null)
                nowBar.setPassed(true);
        }
        return CharacterStatus.INSPACE;
    }

    public boolean checkStandBar(List<Bar> bars) {
        for (Bar bar : bars) {
            if (getRect().intersect(bar.getRect())) {
                nowBar = bar;
                return true;
            }
        }
        return false;
    }


    public void updateX(float delta) {
        this.x += delta * (-10);
        if (this.x < 0) {
            this.x = MainActivity.GAME_WIDTH;
        } else if (this.y > MainActivity.GAME_WIDTH) {
            this.x = 0;
        }
    }


    public void updateIndexBitmapAction(float extra) {
        if (extra > 0 && extra <= 10) {
            currentBitmapIndex = 7;
        } else if (extra <= 20) {
            currentBitmapIndex = 6;
        } else if (extra <= 30) {
            currentBitmapIndex = 5;
        } else if (extra <= 40) {
            currentBitmapIndex = 4;
        } else if (extra > 40 || extra > -30) {
            currentBitmapIndex = 3;
        } else if (extra > -20) {
            currentBitmapIndex = 2;
        } else if (extra > -10) {
            currentBitmapIndex = 1;
        } else {
            currentBitmapIndex = 0;
        }
    }


    public boolean isGrounded() {
        if (rect.top + rect.height() >= MainActivity.GAME_HEIGHT) {
            return true;
        }
        return false;
    }

    public synchronized void render(Painter g) {
        g.drawImage(actionJump.get(currentBitmapIndex), (int) x, (int) y);
    }

    public void updateRect() {
        rect.set((int) x, (int) y, (int) x + actionJump.get(0).getWidth(),
                (int) y + actionJump.get(0).getWidth());
    }

    public Rect getRect() {
        return rect;
    }
}
