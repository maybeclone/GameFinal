package com.slient.gamefinal.utils;

import java.util.Random;

/**
 * Created by silent on 5/8/2018.
 */
public class RandomNumberGenerator {

    private static Random rand = new Random();

    public static int getRandIntBetween(int lowerBound, int upperBound) {
        return rand.nextInt(upperBound - lowerBound) + lowerBound;
    }

    public static int getRandInt(int upperBound) {
        return rand.nextInt(upperBound);
    }
}
