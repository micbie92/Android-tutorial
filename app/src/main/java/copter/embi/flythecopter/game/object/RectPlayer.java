package copter.embi.flythecopter.game.object;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
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

    private Animation flightLeft;
    private Animation flightRight;
    private AnimationManager animationManager;

    public RectPlayer(Rect rectangle, int color){
        this.rectangle = rectangle;
        this.color = color;

        Bitmap step1Bitmap = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.helicopter_1);
        Bitmap step2Bitmap = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.helicopter_2);
        Bitmap step3Bitmap = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.helicopter_3);
        Bitmap step4Bitmap = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.helicopter_4);
        Bitmap step5Bitmap = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.helicopter_5);

        flightRight = new Animation(new Bitmap[]{step1Bitmap, step2Bitmap, step3Bitmap, step4Bitmap,
                step5Bitmap, step4Bitmap, step3Bitmap, step2Bitmap}, 0.5f);

        Matrix m = new Matrix();
        m.preScale(-1,1);
        step1Bitmap = Bitmap.createBitmap(step1Bitmap, 0, 0, step1Bitmap.getWidth(), step1Bitmap.getHeight(), m, false);
        step2Bitmap = Bitmap.createBitmap(step2Bitmap, 0, 0, step2Bitmap.getWidth(), step2Bitmap.getHeight(), m, false);
        step3Bitmap = Bitmap.createBitmap(step3Bitmap, 0, 0, step3Bitmap.getWidth(), step3Bitmap.getHeight(), m, false);
        step4Bitmap = Bitmap.createBitmap(step4Bitmap, 0, 0, step4Bitmap.getWidth(), step4Bitmap.getHeight(), m, false);
        step5Bitmap = Bitmap.createBitmap(step5Bitmap, 0, 0, step5Bitmap.getWidth(), step5Bitmap.getHeight(), m, false);

        flightLeft = new Animation(new Bitmap[]{step1Bitmap, step2Bitmap, step3Bitmap, step4Bitmap,
                step5Bitmap, step4Bitmap, step3Bitmap, step2Bitmap, }, 0.5f);

        animationManager = new AnimationManager(new Animation[]{flightRight, flightLeft});
    }


    @Override
    public void draw(Canvas canvas) {
        animationManager.draw(canvas, rectangle);
    }

    @Override
    public void update() {
        animationManager.update();
    }

    public void update(Point point){
        float oldLeft = rectangle.left;

        rectangle.set(point.x - rectangle.width()/2, point.y - rectangle.height()/2,
                point.x+rectangle.width()/2, point.y+rectangle.height()/2);

        int state = 0;
        if(rectangle.left-oldLeft<-5){
            state = 1;
        }

        animationManager.playAnimation(state);
        animationManager.update();
    }

    public Rect getRectangle(){
        return rectangle;
    }
}
