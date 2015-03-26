package by.aangurets.musicplayer;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.drawable.StateListDrawable;
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

        mPlayPauseButton.setImageDrawable(getStateListDrawable());

        mPlayPauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PlayerActivity.this, PlayerService.class);
                if (isPlay) {
                    intent.setAction(PlayerService.ACTION_PAUSE);
                } else {
                    intent.setAction(PlayerService.ACTION_PLAY);
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
                startService(intent);
                isPlay = false;
            }
        });
    }

    private StateListDrawable getStateListDrawable() {
        StateListDrawable states = new StateListDrawable();
        states.addState(new int[]{android.R.attr.state_pressed},
                getResources().getDrawable(R.drawable.btn_play_pressed));
        states.addState(new int[]{android.R.attr.state_focused},
                getResources().getDrawable(R.drawable.btn_play_focused));
        states.addState(new int[]{},
                getResources().getDrawable(R.drawable.btn_play_normal));
        return states;
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
    }
}
