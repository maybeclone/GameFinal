package com.slient.gamefinal.states;

import android.view.MotionEvent;

import com.slient.gamefinal.utils.Assets;
import com.slient.gamefinal.utils.Painter;

/**
 * Created by silent on 5/8/2018.
 */
public class LoadMenuState extends State {

    @Override
    public void init() {
        load();
        setCurrentState(new MenuState());
    }

    @Override
    public void update(float delta) {
        setCurrentState(new MenuState());
    }

    @Override
    public void render(Painter g) {
    }

    @Override
    public boolean onTouch(MotionEvent e, int scaledX, int scaledY) {
        return false;
    }

    @Override
    public void load() {
        Assets.loadResource();

    }


}
