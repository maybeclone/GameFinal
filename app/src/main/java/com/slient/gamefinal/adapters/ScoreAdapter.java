package com.slient.gamefinal.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.slient.gamefinal.R;
import com.slient.gamefinal.models.game.Score;

import java.util.Collections;
import java.util.List;

/**
 * Created by silent on 5/13/2018.
 */
public class ScoreAdapter extends BaseAdapter {

    List<Score> scores;

    public ScoreAdapter(){
        scores = Collections.emptyList();
    }

    @Override
    public int getCount() {
        return scores.size();
    }

    @Override
    public Object getItem(int position) {
        return scores.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView scoreText;
        TextView dateTimeText;
        if(convertView == null){
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_score, parent, false);
        }
        scoreText = convertView.findViewById(R.id.scoreText);
        dateTimeText = convertView.findViewById(R.id.dateTimeText);

        Score score = scores.get(position);
        scoreText.setText("Score: "+score.score);
        dateTimeText.setText(score.dateTime.toString());

        return convertView;
    }

    public void swap(List<Score> scores){
        this.scores = scores;
        notifyDataSetChanged();
    }
}
