package copter.embi.flythecopter.scene;

import android.graphics.Canvas;
import android.view.MotionEvent;

/**
 * Created by eMBi on 18.02.2018.
 */

public interface Scene {
    public void update();
    public void draw(Canvas canvas);
    public void terminate();
    public void reciveTouch(MotionEvent event);
}
