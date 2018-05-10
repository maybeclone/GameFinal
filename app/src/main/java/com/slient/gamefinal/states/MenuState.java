package com.slient.gamefinal.states;

import android.view.MotionEvent;

import com.slient.gamefinal.utils.Assets;
import com.slient.gamefinal.utils.Painter;
import com.slient.gamefinal.ui.UIButton;

/**
 * Created by silent on 5/8/2018.
 */
public class MenuState extends State{

    private UIButton playButton;
    private UIButton highScoreButton;
    private UIButton settingsButton;

    @Override
    public void init() {
        playButton = new UIButton(450, 800, 1483, 930, Assets.playBitmap);

    }

    @Override
    public void update(float delta) {
    }

    @Override
    public void render(Painter g) {
         g.drawImage(Assets.mainGameBackground, 0, 0, 1920, 1080);
         playButton.render(g, false);
    }

    @Override
    public boolean onTouch(MotionEvent e, int scaledX, int scaledY) {

        if (e.getAction() == MotionEvent.ACTION_DOWN) {
            playButton.onTouchDown(scaledX, scaledY);

        }

        if (e.getAction() == MotionEvent.ACTION_UP) {
            if (playButton.isPressed(scaledX, scaledY)) {
                playButton.cancel();
                setCurrentState(new PlayState());
            }
        }
        return true;
    }

}
