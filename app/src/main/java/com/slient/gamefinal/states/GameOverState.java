package com.slient.gamefinal.states;

import android.view.MotionEvent;

import com.slient.gamefinal.utils.Painter;

/**
 * Created by silent on 5/8/2018.
 */
public class GameOverState extends State {

    private String playerScore;

    public GameOverState(int playerScore) {
        this.playerScore = "" + playerScore;
    }

    @Override
    public void init() {

    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void render(Painter g) {
    }

    @Override
    public boolean onTouch(MotionEvent e, int scaledX, int scaledY) {
        return false;
    }

    @Override
    public void onPause() {

    }

    @Override
    public void onResume() {

    }

}
