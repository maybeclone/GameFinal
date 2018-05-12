package com.slient.gamefinal.states;

import android.content.Context;
import android.view.MotionEvent;

import com.slient.gamefinal.fragments.GameFragment;
import com.slient.gamefinal.main.MainActivity;
import com.slient.gamefinal.utils.Painter;

/**
 * Created by silent on 5/8/2018.
 */
public abstract class State {

    public void setCurrentState(State newState) {
        GameFragment.sGame.setCurrentState(newState);
    }
    public void setPauseGame() {
        GameFragment.sGame.setPause();
    }

    public void setResumeGame(){
        GameFragment.sGame.setResume();
    }

    public abstract void init();

    public abstract void update(float delta);

    public abstract void render(Painter g);

    public abstract boolean onTouch(MotionEvent e, int scaledX, int scaledY);

    public void onPause(){}

    public void onResume() {}

    public void load() {}

    public void unload() {}

}
