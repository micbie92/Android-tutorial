package copter.embi.flythecopter.game.thread;

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

import copter.embi.flythecopter.panels.GamePanel;

/**
 * Created by eMBi on 10.02.2018.
 */

public class GameThread extends Thread {

    public static final int MAX_FPS = 30;
    private static final String TAG = "GameThread";

    private double avarageFPSs;
    private SurfaceHolder surfaceHolder;
    private GamePanel gamePanel;
    private boolean running;
    private static Canvas canvas;

    public GameThread(SurfaceHolder surfaceHolder, GamePanel gamePanel){
        super();
        this.surfaceHolder = surfaceHolder;
        this.gamePanel = gamePanel;
    }

    public void setRunning(boolean running){
        this.running = running;
    }

    @Override
    public void run(){
        long startTime;
        long timeMillis = 1000/MAX_FPS;
        long waitTime;
        int frameCount = 0;
        long totalTime = 0;
        long targetTime = 1000/MAX_FPS;

        while(running){
            startTime = System.nanoTime();
            canvas=null;

            try {
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder){
                    this.gamePanel.update();
                    this.gamePanel.draw(canvas);
                }
            } catch (Exception e){
                Log.e(TAG, "Exception: ", e);
            } finally {
                if(canvas != null){
                    try{
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    } catch (Exception e){
                        Log.e(TAG, "Exception: ", e);
                    }
                }
            }
            timeMillis = (System.nanoTime() - startTime)/1000000;
            waitTime = targetTime - timeMillis;
            try {
                if(waitTime > 0){
                    this.sleep(waitTime);
                }
            } catch (Exception e){
                Log.e(TAG, "Exception: ", e);
            }

            totalTime += System.nanoTime() - startTime;
            frameCount++;
            if(frameCount == MAX_FPS){
                avarageFPSs = 1000/((totalTime/frameCount)/1000000);
                frameCount=0;
                totalTime=0;
                Log.d(TAG, "Avarage FPS: "+avarageFPSs);
            }
        }
    }
}
