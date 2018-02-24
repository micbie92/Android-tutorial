package copter.embi.flythecopter.animation;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by eMBi on 18.02.2018.
 */

public class Animation {

    private Bitmap[] frames;
    private int frameIndex;
    private boolean isPlaying = false;
    private float frameTime;
    private long lastFrame;

    public Animation(Bitmap[] frames, float animTime){
        this.frames = frames;
        frameIndex=0;
        frameTime = animTime/frames.length;
    }

    public void update(){
        if(!isPlaying)  return;

        if(System.currentTimeMillis()-lastFrame > frameTime*1000){
            frameIndex++;
            frameIndex = frameIndex >= frames.length ? 0 : frameIndex;
            lastFrame = System.currentTimeMillis();
        }
    }

    public void draw(Canvas canvas, Rect destination) {
        if(!isPlaying) return;

//        scaleRect(destination);
        canvas.drawBitmap(frames[frameIndex], null, destination, new Paint());
    }

    private void scaleRect(Rect rect){
        float whRatio = (float)(frames[frameIndex].getWidth())/frames[frameIndex].getHeight();
        if(rect.width() > rect.height()) rect.left = rect.right-(int)(rect.height()*whRatio);
        else rect.top = rect.bottom - (int)(rect.width()*whRatio);
    }

    public boolean isPlaying(){
        return isPlaying;
    }

    public void play(){
        frameIndex = 0;
        isPlaying = true;
        lastFrame = System.currentTimeMillis();
    }

    public void stop(){
        isPlaying = false;
    }
}
