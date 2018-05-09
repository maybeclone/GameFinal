package com.slient.gamefinal.states;

import android.graphics.Rect;
import android.view.MotionEvent;

import com.slient.gamefinal.main.MainActivity;
import com.slient.gamefinal.models.Bar;
import com.slient.gamefinal.models.GameBackground;
import com.slient.gamefinal.models.Player;
import com.slient.gamefinal.utils.Assets;
import com.slient.gamefinal.utils.Painter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by silent on 5/8/2018.
 */
public class PlayState extends State {

    private int score;
    private List<Bar> bars;
    private Player player;
    private GameBackground gameBackground;

    @Override
    public void init() {
        score = 0;
        bars = new ArrayList<>();
        player = new Player(500, 500, 700, 500);
        for(int i=0; i<10; i++){
            Bar bar = new Bar(760, 200, 200, i);
            bars.add(bar);
        }
        gameBackground = new GameBackground(MainActivity.GAME_WIDTH, MainActivity.GAME_HEIGHT);
    }

    @Override
    public void update(float delta) {
        gameBackground.update(delta);
        Assets.characterJumpAnimation.update(delta);
        player.update(delta);

        for (Bar bar : bars) {
            bar.update(delta);
        }

    }

    @Override
    public void render(Painter g) {
        drawBackground(g);

        for (Bar bar : bars) {
            bar.render(g);
        }

//        Assets.characterJumpAnimation.render(g, (int)player.getX(), (int) player.getY());

    }


    @Override
    public boolean onTouch(MotionEvent e, int scaledX, int scaledY) {
        player.onTouch(e, scaledX, scaledY);
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

}
