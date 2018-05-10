package com.slient.gamefinal.states;

import android.graphics.Rect;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.view.MotionEvent;

import com.slient.gamefinal.main.MainActivity;
import com.slient.gamefinal.models.Bar;
import com.slient.gamefinal.models.Character;
import com.slient.gamefinal.models.GameBackground;
import com.slient.gamefinal.models.Player;
import com.slient.gamefinal.ui.UILabel;
import com.slient.gamefinal.utils.Assets;
import com.slient.gamefinal.utils.Painter;

import java.util.ArrayList;
import java.util.List;

import javax.sql.StatementEventListener;

/**
 * Created by silent on 5/8/2018.
 */
public class PlayState extends State implements SensorEventListener {

    private int score;
    private List<Bar> bars;
    private Player player;
    private GameBackground gameBackground;
    private UILabel scoreLabel;

    private Character character;

    @Override
    public void init() {
        score = 0;
        bars = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Bar bar = new Bar(500, 50, i);
            bars.add(bar);
        }
        Bar barTemp = bars.get(bars.size() - 1);
        barTemp.setPassed(true);
        character = new Character(Assets.bitmapCharacterJump, barTemp.getX() + barTemp.getWidth() / 2, barTemp.getY()-300);
        gameBackground = new GameBackground(MainActivity.GAME_WIDTH, MainActivity.GAME_HEIGHT);
        MainActivity.mSensorManager.registerListener(this, MainActivity.mSensor, SensorManager.SENSOR_DELAY_GAME);
        scoreLabel = new UILabel(score+"", MainActivity.GAME_WIDTH / 2 - 6, 100);
    }

    @Override
    public void update(float delta) {
        switch (character.update(delta, bars)){
            case INTERSECT:
                score++;
            case INSPACE:
                for (Bar bar : bars) {
                    bar.update(delta);
                }
                gameBackground.update(delta);
                break;
            case DIE:
                setCurrentState(new GameOverState(0));
                break;
        }
        scoreLabel.setText(score+"");

    }

    @Override
    public void render(Painter g) {
        drawBackground(g);
        for (Bar bar : bars) {
            bar.render(g);
        }
        character.render(g);
        scoreLabel.render(g);

    }


    @Override
    public boolean onTouch(MotionEvent e, int scaledX, int scaledY) {
        return false; //This needs to be set to true if there is touch input
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
}
