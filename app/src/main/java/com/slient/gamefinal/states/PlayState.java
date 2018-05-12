package com.slient.gamefinal.states;

import android.graphics.Rect;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.MotionEvent;

import com.slient.gamefinal.main.MainActivity;
import com.slient.gamefinal.models.game.Bar;
import com.slient.gamefinal.models.game.Character;
import com.slient.gamefinal.models.game.GameBackground;
import com.slient.gamefinal.models.game.Player;
import com.slient.gamefinal.ui.UIButton;
import com.slient.gamefinal.ui.UILabel;
import com.slient.gamefinal.utils.Assets;
import com.slient.gamefinal.utils.Painter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by silent on 5/8/2018.
 */
public class PlayState extends State implements SensorEventListener {

    private int score;
    private List<Bar> bars;
    private Player player;
    private GameBackground gameBackground;
    private UILabel scoreLabel;
    private UIButton pauseButton;

    private Character character;

    private boolean isRegister;

    @Override
    public void init() {
        score = 0;
        bars = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Bar bar = new Bar(500, 50, i);
            bars.add(bar);
        }
        isRegister = false;
        Bar barTemp = bars.get(bars.size() - 1);
        barTemp.setPassed(true);
        character = new Character(Assets.bitmapCharacterJump, barTemp.getX() + barTemp.getWidth() / 2, barTemp.getY() - 300);
        gameBackground = new GameBackground(MainActivity.GAME_WIDTH, MainActivity.GAME_HEIGHT);

        scoreLabel = new UILabel(score + "", MainActivity.GAME_WIDTH / 2 - 6, 100);
        pauseButton = new UIButton(1650, 20, 1900, 120, Assets.pauseBackground);
    }

    @Override
    public void update(float delta) {
        if(!isRegister){
            registerSensor();
            isRegister = true;
        }
        switch (character.update(delta, bars)) {
            case INTERSECT:
                score++;
            case INSPACE:
                for (Bar bar : bars) {
                    bar.update(delta);
                }
                gameBackground.update(delta);
                break;
            case DIE:
                setCurrentState(new GameOverState(score));
                break;
        }
        scoreLabel.setText(score + "");

    }

    @Override
    public void render(Painter g) {
        drawBackground(g);
        for (Bar bar : bars) {
            bar.render(g);
        }
        character.render(g);
        scoreLabel.render(g);
        pauseButton.render(g, false);
    }


    @Override
    public boolean onTouch(MotionEvent e, int scaledX, int scaledY) {
        if (e.getAction() == MotionEvent.ACTION_DOWN) {
            pauseButton.onTouchDown(scaledX, scaledY);
        }

        if (e.getAction() == MotionEvent.ACTION_UP) {
            if (pauseButton.isPressed(scaledX, scaledY)) {
                pauseButton.cancel();
                setPauseGame();
            }
        }
        return true; //This needs to be set to true if there is touch input
    }

    private void drawBackground(Painter g) {

        Rect fromRect1 = new Rect(0, 0, gameBackground.getWidth(), gameBackground.getHeight() - gameBackground.getyClip());
        Rect toRect1 = new Rect(0, gameBackground.getyClip(), MainActivity.GAME_WIDTH, gameBackground.getHeight());

        // For the reversed background
        Rect fromRect2 = new Rect(0, gameBackground.getHeight() - gameBackground.getyClip(), gameBackground.getWidth(), gameBackground.getHeight());
        Rect toRect2 = new Rect(0, 0, MainActivity.GAME_WIDTH, gameBackground.getyClip());

        //draw the two background bitmaps
        if (!gameBackground.isReversedFirst()) {
            g.getCanvas().drawBitmap(gameBackground.getBackground(), fromRect1, toRect1, g.getPaint());
            g.getCanvas().drawBitmap(gameBackground.getBackgroundReversed(), fromRect2, toRect2, g.getPaint());
        } else {
            g.getCanvas().drawBitmap(gameBackground.getBackground(), fromRect2, toRect2, g.getPaint());
            g.getCanvas().drawBitmap(gameBackground.getBackgroundReversed(), fromRect1, toRect1, g.getPaint());
        }
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        character.updateX(event.values[0]);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void setPauseGame() {
        super.setPauseGame();
        unregisterSensor();
        isRegister = false;
    }

    public void registerSensor() {
        MainActivity.mSensorManager.registerListener(this, MainActivity.mSensor, SensorManager.SENSOR_DELAY_GAME);
    }

    public void unregisterSensor() {
        MainActivity.mSensorManager.unregisterListener(this);
    }
}
