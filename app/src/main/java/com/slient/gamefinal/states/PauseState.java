package com.slient.gamefinal.states;

import android.view.MotionEvent;

import com.slient.gamefinal.main.MainActivity;
import com.slient.gamefinal.ui.UIButton;
import com.slient.gamefinal.utils.Assets;
import com.slient.gamefinal.utils.Painter;

public class PauseState extends State {
    private UIButton continueButton;
    private UIButton restartButton;
    private UIButton mainButton;

    @Override
    public void init() {
        continueButton = new UIButton((int)((MainActivity.GAME_WIDTH/2) - 450),
                                    420,
                                    (int)((MainActivity.GAME_WIDTH/2) + 450),
                                    540,
                                    Assets.continueBackground);

        restartButton = new UIButton((int)((MainActivity.GAME_WIDTH/2) - 450),
                530,
                (int)((MainActivity.GAME_WIDTH/2) + 450),
                650,
                Assets.restartMenuBackground);

        mainButton = new UIButton((int)((MainActivity.GAME_WIDTH/2) - 460),
                640,
                (int)((MainActivity.GAME_WIDTH/2) + 440),
                760,
                Assets.mainMenuBackground);
    }

    @Override
    public void update(float delta) {

    }

    @Override
    public void render(Painter g) {
        g.drawImage(Assets.pauseBackgroundDialog,
                (MainActivity.GAME_WIDTH/2) - 700,
                (MainActivity.GAME_HEIGHT/2)  - 250,
                1400,
                500);

        continueButton.render(g, false);
        restartButton.render(g, false);
        mainButton.render(g, false);
    }

    @Override
    public boolean onTouch(MotionEvent e, int scaledX, int scaledY) {
        if (e.getAction() == MotionEvent.ACTION_DOWN) {
            continueButton.onTouchDown(scaledX, scaledY);
            restartButton.onTouchDown(scaledX, scaledY);
            mainButton.onTouchDown(scaledX, scaledY);
        }

        if (e.getAction() == MotionEvent.ACTION_UP) {
            if (continueButton.isPressed(scaledX, scaledY)) {
                restartButton.cancel();
                setResumeGame();
            }

            if (restartButton.isPressed(scaledX, scaledY)) {
                restartButton.cancel();
                setCurrentState(new PlayState());
            }

            if (mainButton.isPressed(scaledX, scaledY)) {
                mainButton.cancel();
                setCurrentState(new MenuState());
            }
        }
        return true;
    }

    @Override
    public void load() {

    }

    @Override
    public void unload() {

    }

    @Override
    public void onResume() {
        super.onResume();
    }
}








