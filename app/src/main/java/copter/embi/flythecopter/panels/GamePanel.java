package copter.embi.flythecopter.panels;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import copter.embi.flythecopter.R;
import copter.embi.flythecopter.common.Constants;
import copter.embi.flythecopter.game.thread.GameThread;
import copter.embi.flythecopter.manager.SceneManager;

/**
 * Created by eMBi on 10.02.2018.
 */

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {

    private static final String TAG = "GamePanel";

    private GameThread thread;
    private SceneManager sceneManager;

    public GamePanel(Context context){
        super(context);

        getHolder().addCallback(this);
        Constants.CURRENT_CONTEXT = context;
        thread = new GameThread(getHolder(), this);

        Bitmap background = BitmapFactory.decodeResource(getResources(), R.drawable.sky_pattern);
        background = Bitmap.createScaledBitmap(background, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT, true);
        sceneManager = new SceneManager(background);
        setFocusable(true);
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread = new GameThread(getHolder(), this);
        Constants.INIT_TIME = System.currentTimeMillis();
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while(retry){
            try {
                thread.setRunning(false);
                thread.join();
            } catch (Exception e){
                Log.e(TAG, "Exception: ", e);
            }
            retry= false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        sceneManager.receiveTouch(event);
        return true;
    }

    public void update(){
        sceneManager.update();
    }

    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);
        sceneManager.draw(canvas);
    }

}
