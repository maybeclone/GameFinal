package com.slient.gamefinal.animation;

import com.slient.gamefinal.utils.Painter;

import java.util.List;

/**
 * Created by silent on 5/8/2018.
 */
public class LoopingAnimation {

    private List<Frame> frames;
    private double[] frameEndTimes;
    private int currentFrameIndex = 0;
    private double totalDuration = 0;
    private double currentTime = 0;

    private boolean looping = true;

    //this can take any number of frames and they will be displayed in the order they are added
    public LoopingAnimation(Boolean looping, List<Frame> frames) {
        this.looping = looping;
        this.frames = frames;
        frameEndTimes = new double[frames.size()];
        for (int i = 0; i < frames.size(); i++) {
            Frame f = frames.get(i);
            totalDuration += f.getDuration();
            frameEndTimes[i] = totalDuration;
        }
    }

    public synchronized void update(float increment) {
        currentTime += increment;
        if (currentTime > totalDuration && looping) {
            wrapAnimation();
        }
        if (currentFrameIndex < frameEndTimes.length) {
            while (currentTime > frameEndTimes[currentFrameIndex]) {
                currentFrameIndex++;
            }
        }
    }

    //restart the Animation
    private synchronized void wrapAnimation() {
        currentFrameIndex = 0;
        currentTime %= totalDuration;
    }

    public synchronized void render(Painter g, int x, int y) {
        g.drawImage(frames.get(currentFrameIndex).getImage(), x, y);
    }

    public synchronized void render(Painter g, int x, int y, int width, int height) {
        g.drawImage(frames.get(currentFrameIndex).getImage(), x, y, width, height);
    }
}
