package by.aangurets.musicplayer;

import android.app.IntentService;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;

import java.io.IOException;

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
                mMediaPlayer = new MediaPlayer();
                AssetFileDescriptor afd = getAssets().openFd("music.mp3");
                mMediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
                mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mMediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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

    private void play() {
        if (!mMediaPlayer.isPlaying()) {
            mMediaPlayer.start();
        }
    }

    private void pause() {
        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.pause();
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
