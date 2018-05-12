package com.slient.gamefinal.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.slient.gamefinal.main.GameView;

/**
 * Created by silent on 5/12/2018.
 */
public class GameFragment extends Fragment {

    public static GameView sGame;
    public static final int GAME_WIDTH = 1920;
    public static final int GAME_HEIGHT = 1080;

    public static GameFragment newInstance(){
        GameFragment gameFragment = new GameFragment();
        return gameFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        sGame = new GameView(getContext(), GAME_WIDTH, GAME_HEIGHT);
        return sGame;
    }

    @Override
    public void onResume() {
        super.onResume();
        sGame.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        sGame.onPause();
    }

}
