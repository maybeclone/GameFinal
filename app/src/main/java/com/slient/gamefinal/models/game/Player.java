package com.slient.gamefinal.models.game;

import android.graphics.Rect;
import android.view.MotionEvent;

public class Player {
    private float x, y;
    private int width, height, velY;
    private Rect rect;
    private float yGround;

    private boolean isAlive;
    private boolean isDucked;

    private static final int JUMP_VELOCITY = -800;
    private static final int ACCEL_GRAVITY = 1800;

    public Player(float x, float y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        isDucked = false;
        isAlive = true;

        rect = new Rect((int) x, (int) y, (int) (x + width), (int) (y + height));
        yGround = y + height;
    }

    public void update(float delta) {
        jump();
        if (isGrounded()) {
            velY = 0;
        } else {
            velY += ACCEL_GRAVITY * delta;
        }

        y += velY * delta;
        updateRect();
    }

    public void jump() {
        if (isGrounded()) {
            y -= 300;
            velY = JUMP_VELOCITY;
        }
    }


    public void onTouch(MotionEvent e, int scaledX, int scaledY) {
        if (e.getAction() == MotionEvent.ACTION_DOWN) {
            jump();
        }
    }

    public void updateRect() {
        rect.set((int) x, (int) y, (int) x + width, (int) y + height);
    }

    public boolean isGrounded() {
        if (rect.top + rect.height() >= yGround) {
            return true;
        }
        return false;
    }

    public boolean isDucked() {
        return isDucked;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public Rect getRect() {
        return new Rect(rect.left + 40, rect.top + 30, rect.right - 35, rect.bottom - 35);
    }

}
















