package by.aangurets.musicplayer;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.ImageButton;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class PlayerActivity extends Activity {

    public static final int ID = 1;

    private boolean isPlay = false;

    @InjectView(R.id.play_pause)
    ImageButton mPlayPauseButton;
    @InjectView(R.id.stop)
    ImageButton mStopButton;
    private NotificationManager mManager;
    private NotificationCompat.Builder mBuilder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        ButterKnife.inject(this);
        mManager = (NotificationManager) getApplicationContext().getSystemService(NOTIFICATION_SERVICE);
        mBuilder = new NotificationCompat.Builder(this);

        setTitle(R.string.app_name);

        mPlayPauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PlayerActivity.this, PlayerService.class);
                if (isPlay) {
                    intent.setAction(PlayerService.ACTION_PAUSE);
                    mPlayPauseButton.setImageResource(R.drawable.play);
                } else {
                    intent.setAction(PlayerService.ACTION_PLAY);
                    mPlayPauseButton.setImageResource(R.drawable.pause);
                }
                isPlay = !isPlay;
                startService(intent);
            }
        });

        mStopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PlayerActivity.this, PlayerService.class);
                intent.setAction(PlayerService.ACTION_STOP);
                mPlayPauseButton.setImageResource(R.drawable.play);
                startService(intent);
                isPlay = false;
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        Intent intent = new Intent(getApplicationContext(), PlayerService.class);
        if (isPlay) {
            stopPlaying(intent);
        } else {
            startPlaying(intent);
        }
        isPlay = !isPlay;
    }

    private void startPlaying(Intent intent) {
        intent.setAction(PlayerService.ACTION_PLAY);
        mBuilder
                .setSmallIcon(R.drawable.musicplayer)
                .setContentTitle("Play")
                .setContentText("Start Playing")
                .setContentIntent(PendingIntent.getService(this, ID, intent, PendingIntent.FLAG_CANCEL_CURRENT))
                .setAutoCancel(true);
        mManager.notify(ID, mBuilder.build());
        mPlayPauseButton.setImageResource(R.drawable.pause);
    }

    private void stopPlaying(Intent intent) {
        intent.setAction(PlayerService.ACTION_STOP);
        mBuilder
                .setSmallIcon(R.drawable.musicplayer)
                .setContentTitle("Stop")
                .setContentText("Stop playing")
                .setContentIntent(PendingIntent.getService(this, ID, intent, PendingIntent.FLAG_CANCEL_CURRENT))
                .setAutoCancel(true);
        mManager.notify(ID, mBuilder.build());
        mPlayPauseButton.setImageResource(R.drawable.play);
    }
}
