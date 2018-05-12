package com.slient.gamefinal.models.game;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by silent on 5/12/2018.
 */
public class Score {

    public int score;
    public Date dateTime;

    public Score(int score) {
        this.score = score;
        dateTime = Calendar.getInstance().getTime();
    }

    public Score(int score, String timeDate) {
        this.score = score;
        Log.d("TRUNG", timeDate);
        String str = timeDate.replace("T", " ");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
        try {
            dateTime = simpleDateFormat.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
