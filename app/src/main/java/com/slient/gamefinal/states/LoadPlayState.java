package com.slient.gamefinal.states;

import android.view.MotionEvent;

import com.slient.gamefinal.utils.Assets;
import com.slient.gamefinal.utils.Painter;

/**
 * Created by silent on 5/8/2018.
 */
public class LoadPlayState extends State {

    @Override
    public void init() {
        load();
    }

    @Override
    public void update(float delta) {
        unload();
        setCurrentState(new PlayState());
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

    }

    @Override
    public void unload() {
        Assets.unloadBitmap(Assets.menuBackground);
    }

}
