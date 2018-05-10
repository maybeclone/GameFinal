package com.slient.gamefinal.ui;

import android.graphics.Color;
import android.graphics.Paint;

import com.slient.gamefinal.utils.Assets;
import com.slient.gamefinal.utils.Painter;

public class UILabel {

    private String text;
    private int size = 80;
    private Paint stPaint;
    private float x;
    private float y;

    public UILabel(String text, float x, float y){
        this.x = x;
        this.y = y;
        this.text = text;
        stPaint = new Paint();
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setSize(int size){
        this.size = size;

    }

    public void render(Painter g) {
        stPaint.setTypeface(Assets.typeface);

        stPaint.setTextSize(size);
        stPaint.setColor(Color.WHITE);
        stPaint.setTextAlign(Paint.Align.CENTER);
        stPaint.setStyle( Paint.Style.FILL);
        stPaint.setTextSize(size);
        g.getCanvas().drawText(text, x, y, stPaint);

        stPaint.setTypeface(Assets.typeface);
        stPaint.setTextSize(size);
        stPaint.setColor(Color.BLACK);
        stPaint.setTextAlign(Paint.Align.CENTER);
        stPaint.setStyle(Paint.Style.STROKE);
        stPaint.setStrokeCap(Paint.Cap.ROUND);
        stPaint.setStrokeWidth(3);
        stPaint.setTextSize(size);

        g.getCanvas().drawText(text, x, y, stPaint);
    }

}









