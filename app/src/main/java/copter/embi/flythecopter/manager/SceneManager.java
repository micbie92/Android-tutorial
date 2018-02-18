package copter.embi.flythecopter.manager;

import android.graphics.Canvas;
import android.view.MotionEvent;

import java.util.ArrayList;

import copter.embi.flythecopter.scene.GameplayScene;
import copter.embi.flythecopter.scene.Scene;

/**
 * Created by eMBi on 18.02.2018.
 */

public class SceneManager {

    private ArrayList<Scene> scenes= new ArrayList<>();
    public static int ACTIVE_SCENE;

    public SceneManager(){
        ACTIVE_SCENE=0;
        scenes.add(new GameplayScene());
    }

    public void receiveTouch(MotionEvent event){
        scenes.get(ACTIVE_SCENE).reciveTouch(event);
    }

    public void update(){
        scenes.get(ACTIVE_SCENE).update();
    }

    public void draw(Canvas canvas){
        scenes.get(ACTIVE_SCENE).draw(canvas);
    }
}
