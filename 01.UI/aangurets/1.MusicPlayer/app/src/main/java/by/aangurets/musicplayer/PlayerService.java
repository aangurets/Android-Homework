package by.aangurets.musicplayer;

import android.annotation.TargetApi;
import android.app.IntentService;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.widget.Toast;

import java.io.IOException;

/**
 * Created by User on 23.02.2015.
 */
public class PlayerService extends IntentService {

    public final static String ACTION_PLAY = "by.aangurets.musicplayer.ACTION_PLAY";
    public final static String ACTION_PAUSE = "by.aangurets.musicplayer.ACTION_PAUSE";
    public final static String ACTION_STOP = "by.aangurets.musicplayer.ACTION_STOP";

    private static MediaPlayer mMediaPlayer;

    public PlayerService() {
        super(PlayerService.class.getSimpleName());
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (mMediaPlayer == null) {
            try {
                initialization();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void initialization() throws IOException {
        mMediaPlayer = new MediaPlayer();
        try (AssetFileDescriptor afd = getAssets().openFd("tiesto.mp3");) {
            mMediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            afd.close();
        }
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mMediaPlayer.prepare();
        mMediaPlayer.setVolume(1f, 1f);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        switch (intent.getAction()) {
            case ACTION_PLAY:
                play();
                break;

            case ACTION_STOP:
                stop();
                break;

            case ACTION_PAUSE:
                pause();
                break;

            default:
                throw new IllegalArgumentException(
                        "Unknown action '" + intent.getAction() + "'.");
        }
    }

    private void pause() {
        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.pause();
        }
    }

    private void play() {
        try {
            initialization();
            mMediaPlayer.start();
        } catch (IOException e) {
            Toast.makeText(this, getString(R.string.ioexception) + e, Toast.LENGTH_SHORT).show();
        }
    }

    private void stop() {
        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }
}
