package by.aangurets.musicplayer;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class PlayerActivity extends Activity {

    private boolean isPlay = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        final ImageButton startStopButton = (ImageButton) findViewById(R.id.play_pause);
        startStopButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PlayerActivity.this, PlayerService.class);
                if (isPlay) {
                    intent.setAction(PlayerService.ACTION_PAUSE);
                    startStopButton.setImageResource(R.drawable.play);
                } else {
                    intent.setAction(PlayerService.ACTION_PLAY);
                    startStopButton.setImageResource(R.drawable.pause);
                }
                isPlay = !isPlay;
                startService(intent);
            }
        });

        findViewById(R.id.stop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PlayerActivity.this, PlayerService.class);
                intent.setAction(PlayerService.ACTION_STOP);
                startStopButton.setImageResource(R.drawable.play);
                startService(intent);
                isPlay = false;
            }
        });
    }
}
