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

    private int lastState = 0;
    private Animation flightLeft;
    private Animation flightRight;
    private Animation crash;
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
                step5Bitmap, step4Bitmap, step3Bitmap, step2Bitmap}, 0.5f, true);

        Matrix m = new Matrix();
        m.preScale(-1,1);
        step1Bitmap = Bitmap.createBitmap(step1Bitmap, 0, 0, step1Bitmap.getWidth(), step1Bitmap.getHeight(), m, false);
        step2Bitmap = Bitmap.createBitmap(step2Bitmap, 0, 0, step2Bitmap.getWidth(), step2Bitmap.getHeight(), m, false);
        step3Bitmap = Bitmap.createBitmap(step3Bitmap, 0, 0, step3Bitmap.getWidth(), step3Bitmap.getHeight(), m, false);
        step4Bitmap = Bitmap.createBitmap(step4Bitmap, 0, 0, step4Bitmap.getWidth(), step4Bitmap.getHeight(), m, false);
        step5Bitmap = Bitmap.createBitmap(step5Bitmap, 0, 0, step5Bitmap.getWidth(), step5Bitmap.getHeight(), m, false);

        flightLeft = new Animation(new Bitmap[]{step1Bitmap, step2Bitmap, step3Bitmap, step4Bitmap,
                step5Bitmap, step4Bitmap, step3Bitmap, step2Bitmap, }, 0.5f, true);

        Bitmap crash1Bitmap = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.explosion_01);
        Bitmap crash2Bitmap = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.explosion_02);
        Bitmap crash3Bitmap = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.explosion_03);
        Bitmap crash4Bitmap = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.explosion_04);
        Bitmap crash5Bitmap = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.explosion_05);
        Bitmap crash6Bitmap = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.explosion_06);
        Bitmap crash7Bitmap = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.explosion_07);
        Bitmap crash8Bitmap = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.explosion_08);
        Bitmap crash9Bitmap = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.explosion_09);
        Bitmap crash10Bitmap = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.explosion_10);
        Bitmap crash11Bitmap = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.explosion_11);
        Bitmap crash12Bitmap = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.explosion_12);
        crash = new Animation(new Bitmap[]{crash1Bitmap, crash2Bitmap, crash3Bitmap, crash4Bitmap,
                crash5Bitmap, crash6Bitmap, crash7Bitmap, crash8Bitmap, crash9Bitmap, crash10Bitmap,
                crash11Bitmap, crash12Bitmap}, 0.5f, false);

        animationManager = new AnimationManager(new Animation[]{flightRight, flightLeft, crash});
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

        int state = lastState;
        if(rectangle.left-oldLeft<-1) state = 1;
        if(rectangle.left-oldLeft>1) state = 0;
        lastState = state;

        animationManager.playAnimation(state);
        animationManager.update();
    }

    public void playCrash(){
        animationManager.playAnimation(2);
        animationManager.update();
    }

    public Rect getRectangle(){
        return rectangle;
    }
}
