package com.slient.gamefinal.utils;

import android.annotation.TargetApi;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.graphics.drawable.PictureDrawable;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;

import com.slient.gamefinal.animation.Frame;
import com.slient.gamefinal.animation.LoopingAnimation;
import com.slient.gamefinal.main.MainActivity;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by silent on 5/8/2018.
 */
public class Assets {

    private static MediaPlayer mediaPlayer;

    private static SoundPool soundPool;

    public static List<Bitmap> bitmapCharacterJump;

    public static Bitmap menuBackground;
    public static Bitmap mainGameBackground;
    public static Bitmap barImpedimentBackground;
    public static Bitmap dialogBackground;
    public static Bitmap settingBackground;
    public static Bitmap highscoreBackground;
    public static Bitmap pauseBackground;
    public static Bitmap logoBackground;
    public static Bitmap pauseBackgroundDialog;
    public static Bitmap gameOverBackgroundDialog;
    public static Bitmap continueBackground;
    public static Bitmap mainMenuBackground;
    public static Bitmap restartMenuBackground;

    public static LoopingAnimation loopingAnimation;

    public static Typeface typeface;


    public static Bitmap playBitmap;

    public static void onPause() {
        if(soundPool != null) {
            soundPool.release();
            soundPool = null;
        }

        if(mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    public static void onResume() {

    }

    public static void loadResource(){

        bitmapCharacterJump = splitBitmapFromSpriteMap("character_sprite.png", true, 8);

        Assets.menuBackground = Assets.loadBitmap("background_menu.png", true);
        Assets.playBitmap = Assets.loadBitmap("play_button.png", true);
        Assets.settingBackground = Assets.loadBitmap("settings_button.png", true);
        Assets.highscoreBackground = Assets.loadBitmap("highscore_button.png", true);
        Assets.mainGameBackground = Assets.loadBitmap("maingame_background.png", true);
        Assets.barImpedimentBackground = Assets.loadBitmap("bar.png", true);
        Assets.pauseBackground = Assets.loadBitmap("pause_button.png", true);
        Assets.typeface = Typeface.create( Typeface.createFromAsset(MainActivity.assets, "fonts/font.TTF"), Typeface.BOLD);
        Assets.dialogBackground = Assets.loadBitmap("background_dialog.png", true);
        Assets.pauseBackgroundDialog = Assets.loadBitmap("pause_background_dialog.png", true);
        Assets.gameOverBackgroundDialog = Assets.loadBitmap("gameover_background_dialog.png", true);
        Assets.logoBackground = Assets.loadBitmap("logo_game.png", true);
        Assets.continueBackground = Assets.loadBitmap("continue_button.png", true);
        Assets.restartMenuBackground = Assets.loadBitmap("restart_button.png", true);
        Assets.mainMenuBackground = Assets.loadBitmap("main_button.png", true);

        List<Frame> frames = new ArrayList<>();
        for(Bitmap bitmap : bitmapCharacterJump){
            frames.add(new Frame(bitmap, 0.1));
        }

        loopingAnimation = new LoopingAnimation(true, frames);
    }

    public static List<Bitmap> splitBitmapFromSpriteMap(String filename, boolean transparency, int qty){
        InputStream inputStream = null;
        try {
            inputStream = MainActivity.assets.open(filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        if (transparency) {
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        } else {
            options.inPreferredConfig = Bitmap.Config.RGB_565;
        }
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream, null, options);

        int width = bitmap.getWidth() / qty;
        int height = bitmap.getHeight();

        int x = 0;
        Bitmap cropBitmap;
        List<Bitmap> bitmaps = new ArrayList<>();

        for (int i = 0; i < qty; ++i) {
            cropBitmap = Bitmap.createBitmap(bitmap, x, 0, width, height);
            bitmaps.add(cropBitmap);
            x += width;
        }

        return bitmaps;
    }

    public static Bitmap loadBitmap(String filename, boolean transparency) {
        InputStream inputStream = null;
        try {
            inputStream = MainActivity.assets.open(filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
        BitmapFactory.Options options = new BitmapFactory.Options();

        if (transparency) {
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        } else {
            options.inPreferredConfig = Bitmap.Config.RGB_565;
        }
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream, null,
                options);
        return bitmap;
    }

    //unloads the bitmap and clears up memory
    public static void unloadBitmap(Bitmap bitmap) {
        bitmap.recycle();
        bitmap = null;
    }

    // loads sounds (I think they have to be in .wav format)
    public static int loadSound(String filename) {
        int soundID = 0;
        if (soundPool == null) {
            buildSoundPool();
        }
        try {
            soundID = soundPool.load(MainActivity.assets.openFd(filename), 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return soundID;
    }


    //SoundPool was deprecated as of android 5.0 (lolipop) so
    //we have to check what the version of android is in order
    //to determine which is the best way to loadResource sound
    @SuppressWarnings("deprecation")
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static SoundPool buildSoundPool() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();

            soundPool = new SoundPool.Builder()
                    .setMaxStreams(25)
                    .setAudioAttributes(audioAttributes)
                    .build();
        } else {
            soundPool = new SoundPool(25, AudioManager.STREAM_MUSIC, 0);
        }
        return soundPool;
    }


    //converts a PictureDrawable to a Bitmap because hardware acceleration does
    //not support drawing Drawables or Pictures
    private static Bitmap pictureDrawableToBitmap(PictureDrawable pictureDrawable){
        Bitmap bitmap = Bitmap.createBitmap(pictureDrawable.getIntrinsicWidth(), pictureDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawPicture(pictureDrawable.getPicture());
        return bitmap;
    }


    //play a sound that is already loaded into memory
    public static void playSound(int soundID) {
        if (soundPool != null) {
            soundPool.play(soundID, 1, 1, 1, 0, 1);
        }
    }

    //Used to stream music without loading it into the memory
    public static void playMusic(String fileName, boolean looping) {
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
        }
        try {
            AssetFileDescriptor afd = MainActivity.assets.openFd(fileName);
            mediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.prepare();
            mediaPlayer.setLooping(looping);
            mediaPlayer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
