package com.slient.gamefinal.main;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener2;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.slient.gamefinal.R;
import com.slient.gamefinal.callbacks.FragmentCallback;
import com.slient.gamefinal.config.Instance;
import com.slient.gamefinal.fragments.GameFragment;
import com.slient.gamefinal.fragments.HighScoreFragment;
import com.slient.gamefinal.fragments.LoginFragment;
import com.slient.gamefinal.fragments.RegisterFragment;
import com.slient.gamefinal.fragments.SettingsFragment;
import com.slient.gamefinal.models.account.User;
import com.slient.gamefinal.server.ConfigServer;
import com.slient.gamefinal.server.account.LoginPostUserAsyncTask;
import com.slient.gamefinal.server.account.RegisterPostUserAsyncTask;
import com.slient.gamefinal.utils.Assets;

import java.util.List;

public class MainActivity extends AppCompatActivity implements FragmentCallback {

    public static final int GAME_WIDTH = 1920;
    public static final int GAME_HEIGHT = 1080;

    public static AssetManager assets;

    public static SensorManager mSensorManager;
    public static Sensor mSensor;

    private GameFragment gameFragment;
    private LoginFragment loginFragment;
    private RegisterFragment registerFragment;
    private HighScoreFragment highScoreFragment;
    private SettingsFragment settingsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assets = getAssets();
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_main);

        initFragment();
        replaceLoginFragment();

    }

    public void initFragment(){
        gameFragment = GameFragment.newInstance();
        loginFragment = LoginFragment.newInstance();
        registerFragment = RegisterFragment.newInstance();
        highScoreFragment = HighScoreFragment.newInstance();
        settingsFragment = SettingsFragment.newInstance();
    }

    @Override
    public void replaceLoginFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, loginFragment, "Login")
                .commit();
    }

    @Override
    public void replaceRegisterFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, registerFragment, "Register")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void replaceGameFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, gameFragment, "Game")
                .commit();
    }

    @Override
    public void replaceHighScoreFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, highScoreFragment, "HighScore")
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void replaceSettingsFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, settingsFragment, "Settings")
                .addToBackStack(null)
                .commit();
    }
}
