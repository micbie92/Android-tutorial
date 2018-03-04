package copter.embi.flythecopter;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

//    private View view;
    private static MediaPlayer song;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        song = MediaPlayer.create(MainActivity.this, R.raw.music_main_menu);
        song.setLooping(true);
        song.start();
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onPause(){
        super.onPause();
        song.pause();
    }

    @Override
    protected void onResume(){
        super.onResume();
        song.start();
    }

    public void startGame(View view){
        Log.d(TAG, "Button pressed");
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
        Log.d(TAG, "Button pressed END");

    }

}
