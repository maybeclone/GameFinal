package com.slient.gamefinal.models;

import android.graphics.Bitmap;
import android.graphics.Matrix;

import com.slient.gamefinal.main.GameView;
import com.slient.gamefinal.utils.Assets;
import com.slient.gamefinal.utils.Config;

public class GameBackground {

    private Bitmap background;
    private Bitmap backgroundReversed;

    private int width;
    private int height;
    private boolean reversedFirst;

    private int yClip;

    public GameBackground(int screenWidth, int screenHeight){
        background = Assets.mainGameBackground;
        reversedFirst = false;

        // Create the bitmap
        background = Bitmap.createScaledBitmap(background, screenWidth,
                screenHeight
                , true);

        yClip = background.getHeight();

        // Save the width and height for later use
        width = background.getWidth();
        height = background.getHeight();

        //Create a mirror image of the background (horizontal flip)
        Matrix matrix = new Matrix();
        matrix.setScale(-1, 1);
        backgroundReversed = Bitmap.createBitmap(background, 0, 0, width, height, matrix, true);
    }

    public void update(double delta){
        yClip += Config.SPEED / GameView.FPS;
        if (yClip >= height) {
            yClip = 0;
            reversedFirst = !reversedFirst;
        } else if (yClip <= 0) {
            yClip = height;
            reversedFirst = !reversedFirst;
        }
    }

    //Get

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean isReversedFirst() {
        return reversedFirst;
    }


    public int getyClip() {
        return yClip;
    }

    public Bitmap getBackground() {
        return background;
    }

    public Bitmap getBackgroundReversed() {
        return backgroundReversed;
    }

}












