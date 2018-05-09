package com.slient.gamefinal.utils;

import android.view.MotionEvent;
import android.view.View;

import com.slient.gamefinal.main.MainActivity;
import com.slient.gamefinal.states.State;

/**
 * Created by silent on 5/8/2018.
 */
public class InputHandler implements View.OnTouchListener{

    private State currentState;

    public void setCurrentState(State currentState) {
        this.currentState = currentState;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        //it uses scaledX and Y instead of event.getX or event.getY
        // so there wont be bugs on different screen sizes
        int scaledX = (int) ((event.getX() / v.getWidth()) * MainActivity.GAME_WIDTH);
        int scaledY = (int) ((event.getY() / v.getHeight()) * MainActivity.GAME_HEIGHT);
        return currentState.onTouch(event, scaledX, scaledY);
    }

}
