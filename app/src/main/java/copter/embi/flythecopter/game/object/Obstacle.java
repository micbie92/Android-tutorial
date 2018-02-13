package copter.embi.flythecopter.game.object;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import copter.embi.flythecopter.common.Constants;

/**
 * Created by eMBi on 12.02.2018.
 */

public class Obstacle implements GameObject{

    private Rect rectangle;
    private Rect rectangle2;
    private int color;

    public Obstacle(int rectHeight, int color, int startX, int startY, int playerGap) {
        this.color = color;
        rectangle = new Rect(0,startY, startX, startY+rectHeight);
        rectangle2 = new Rect(startX+playerGap,startY, Constants.SCREEN_WIDTH, startY+rectHeight);
    }

    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(color);
        canvas.drawRect(rectangle, paint);
        canvas.drawRect(rectangle2, paint);
    }

    @Override
    public void update() {}

    public boolean copterCollide(RectCopter copter){
        return rectangle.contains(copter.getRectangle().left, copter.getRectangle().top)
                || rectangle.contains(copter.getRectangle().right, copter.getRectangle().top)
                || rectangle.contains(copter.getRectangle().left, copter.getRectangle().bottom)
                || rectangle.contains(copter.getRectangle().right, copter.getRectangle().bottom);
    }

    public Rect getRectangle(){return rectangle;}

    public void incrementY(float y){
        rectangle.top += y;
        rectangle.bottom += y;

        rectangle2.top += y;
        rectangle2.bottom += y;
    }
}
