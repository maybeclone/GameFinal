package com.slient.gamefinal.states;

import android.view.MotionEvent;

import com.slient.gamefinal.fragments.GameFragment;
import com.slient.gamefinal.main.MainActivity;
import com.slient.gamefinal.ui.UIButton;
import com.slient.gamefinal.ui.UILabel;
import com.slient.gamefinal.utils.Assets;
import com.slient.gamefinal.utils.Painter;

/**
 * Created by silent on 5/8/2018.
 */
public class GameOverState extends State {

    private String playerScore;

    private UIButton restartButton;
    private UIButton mainButton;
    private UILabel scoreLabel;
    private int score;

    public GameOverState(int playerScore) {
        this.score = playerScore;
        this.playerScore = "" + playerScore;
    }

    @Override
    public void init() {
        GameFragment.sGame.uploadScore(score);
        scoreLabel = new UILabel( playerScore + " score", MainActivity.GAME_WIDTH / 2, 520);
        scoreLabel.setSize(130);
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
        g.drawImage(Assets.gameOverBackgroundDialog,
                (MainActivity.GAME_WIDTH/2) - 700,
                (MainActivity.GAME_HEIGHT/2)  - 250,
                1400,
                500);

        restartButton.render(g, false);
        mainButton.render(g, false);
        scoreLabel.render(g);
    }

    @Override
    public boolean onTouch(MotionEvent e, int scaledX, int scaledY) {
        if (e.getAction() == MotionEvent.ACTION_DOWN) {
            restartButton.onTouchDown(scaledX, scaledY);
            mainButton.onTouchDown(scaledX, scaledY);
        }

        if (e.getAction() == MotionEvent.ACTION_UP) {

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
    public void onPause() {

    }

    @Override
    public void onResume() {

    }

}
