package com.slient.gamefinal.states;

import android.util.Log;
import android.view.MotionEvent;

import com.slient.gamefinal.fragments.GameFragment;
import com.slient.gamefinal.main.MainActivity;
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
        highScoreButton = new UIButton(50, 950, 300, 1050, Assets.highscoreBackground);
        settingsButton = new UIButton(1650, 950, 1900, 1050, Assets.settingBackground);
    }

    @Override
    public void update(float delta) {
        Assets.loopingAnimation.update(delta);
    }

    @Override
    public void render(Painter g) {
         g.drawImage(Assets.menuBackground, 0, 0, 1920, 1080);
         playButton.render(g, false);
         settingsButton.render(g, false);
         highScoreButton.render(g, false);
         g.drawImage(Assets.logoBackground, 50, 90, 1920 - 70, 1080/4);
         Assets.loopingAnimation.render(g, MainActivity.GAME_WIDTH/2 - Assets.bitmapCharacterJump.get(0).getWidth()/2,
                 MainActivity.GAME_HEIGHT/2 - Assets.bitmapCharacterJump.get(0).getHeight()/2);
    }

    @Override
    public boolean onTouch(MotionEvent e, int scaledX, int scaledY) {

        if (e.getAction() == MotionEvent.ACTION_DOWN) {
            playButton.onTouchDown(scaledX, scaledY);
            highScoreButton.onTouchDown(scaledX, scaledY);
            settingsButton.onTouchDown(scaledX, scaledY);
        }

        if (e.getAction() == MotionEvent.ACTION_UP) {
            if (playButton.isPressed(scaledX, scaledY)) {
                playButton.cancel();
                setCurrentState(new PlayState());
            }
            if(highScoreButton.isPressed(scaledX, scaledY)){
                highScoreButton.cancel();
                GameFragment.sGame.initHighScore();
            }
            if(settingsButton.isPressed(scaledX, scaledY)){
                settingsButton.cancel();
                GameFragment.sGame.initAccount();
            }
        }
        return true;
    }

}
