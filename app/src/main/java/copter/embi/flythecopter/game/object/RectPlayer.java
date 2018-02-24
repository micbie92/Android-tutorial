package copter.embi.flythecopter.game.object;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

import copter.embi.flythecopter.R;
import copter.embi.flythecopter.animation.Animation;
import copter.embi.flythecopter.common.Constants;
import copter.embi.flythecopter.manager.AnimationManager;

/**
 * Created by eMBi on 10.02.2018.
 */

public class RectPlayer implements GameObject {

    private Rect rectangle;
    private int color;

    private Animation idle;
    private Animation flightLeft;
    private Animation flightRight;
    private AnimationManager animationManager;

    public RectPlayer(Rect rectangle, int color){
        this.rectangle = rectangle;
        this.color = color;

        BitmapFactory bf = new BitmapFactory();
        Bitmap step1Bitmap = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.helicopter_1);
        Bitmap step2Bitmap = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.helicopter_2);
        Bitmap step3Bitmap = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.helicopter_3);
        Bitmap step4Bitmap = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.helicopter_4);
        Bitmap step5Bitmap = bf.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.helicopter_5);

        idle = new Animation(new Bitmap[]{step1Bitmap}, 2);
        flightRight = new Animation(new Bitmap[]{step1Bitmap, step2Bitmap, step3Bitmap, step4Bitmap,
                step5Bitmap, step4Bitmap, step3Bitmap, step2Bitmap}, 10.0f);

        Matrix m = new Matrix();
        m.preScale(-1,1);
        step1Bitmap = Bitmap.createBitmap(step1Bitmap, 0, 0, step1Bitmap.getWidth(), step1Bitmap.getHeight(), m, false);
        step2Bitmap = Bitmap.createBitmap(step2Bitmap, 0, 0, step2Bitmap.getWidth(), step2Bitmap.getHeight(), m, false);
        step3Bitmap = Bitmap.createBitmap(step3Bitmap, 0, 0, step3Bitmap.getWidth(), step3Bitmap.getHeight(), m, false);
        step4Bitmap = Bitmap.createBitmap(step4Bitmap, 0, 0, step4Bitmap.getWidth(), step4Bitmap.getHeight(), m, false);
        step5Bitmap = Bitmap.createBitmap(step5Bitmap, 0, 0, step5Bitmap.getWidth(), step5Bitmap.getHeight(), m, false);

        flightLeft = new Animation(new Bitmap[]{step1Bitmap, step2Bitmap, step3Bitmap, step4Bitmap,
                step5Bitmap, step4Bitmap, step3Bitmap, step2Bitmap}, 10.0f);

        animationManager = new AnimationManager(new Animation[]{idle, flightRight, flightLeft});
    }


    @Override
    public void draw(Canvas canvas) {
//        Paint paint = new Paint();
//        paint.setColor(color);
//        canvas.drawRect(rectangle, paint);
        animationManager.draw(canvas, rectangle);
    }

    @Override
    public void update() {
        animationManager.update();
    }

    public void update(Point point){
        float oldLeft = rectangle.left;

        rectangle.set(point.x - rectangle.width()/2, point.y + rectangle.height()/2,
                point.x+rectangle.width()/2, point.y-rectangle.height()/2);

        int state = 0;
        if(rectangle.left-oldLeft > 5){
            state = 1;
        } else if(rectangle.left-oldLeft<-5){
            state = 2;
        }

        animationManager.playAnimation(state);
        animationManager.update();
    }

    public Rect getRectangle(){
        return rectangle;
    }
}
