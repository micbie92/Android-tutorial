package copter.embi.flythecopter.panels;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.SurfaceHolder;

import copter.embi.flythecopter.game.objects.RectCopter;
import copter.embi.flythecopter.game.threads.GameThread;

/**
 * Created by eMBi on 10.02.2018.
 */

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {

    private static final String TAG = "GamePanel";

    private GameThread thread;
    private RectCopter copter;
    private Point copterPoint;

    public GamePanel(Context context){
        super(context);

        getHolder().addCallback(this);

        thread = new GameThread(getHolder(), this);

        copter = new RectCopter(new Rect(100, 100, 200, 200), Color.rgb(255,0,0));
        copterPoint = new Point(150, 150);

        setFocusable(true);
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread = new GameThread(getHolder(), this);
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while(true){
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

        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                copterPoint.set((int)event.getX(),(int)event.getY());
        }

        return true;
//        return super.onTouchEvent(event);
    }

    public void update(){
        copter.update(copterPoint);
    }

    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);
        copter.draw(canvas);
    }
}
