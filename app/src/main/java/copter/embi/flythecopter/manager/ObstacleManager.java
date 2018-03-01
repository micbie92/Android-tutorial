package copter.embi.flythecopter.manager;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import java.util.ArrayList;

import copter.embi.flythecopter.common.Constants;
import copter.embi.flythecopter.game.object.Obstacle;
import copter.embi.flythecopter.game.object.RectPlayer;

/**
 * Created by eMBi on 12.02.2018.
 */

public class ObstacleManager {

    private static final String TAG = "ObstacleManager";

    //higher index = lower on screen = higher y value
    private ArrayList<Obstacle> obstacles;
    private int playerGap;
    private int obstacleGap;
    private int obstacleHeight;
    private int color;

    private long startTime;
    private long initTime;

    private float score= 0;

    public ObstacleManager(int playerGap, int obstacleGap, int obstacleHeight, int color){
        this.playerGap = playerGap;
        this.obstacleGap = obstacleGap;
        this.obstacleHeight = obstacleHeight;
        this.color = color;

        startTime = initTime =  System.currentTimeMillis();

        obstacles = new ArrayList<>();

        populateObstacles();
    }

    private void populateObstacles(){
        int currY = -5* Constants.SCREEN_HEIGHT/4;

        while(currY < 0){
            int xStart = (int) (Math.random()*(Constants.SCREEN_WIDTH-playerGap));
            obstacles.add(new Obstacle(obstacleHeight, color, xStart, currY, playerGap));
            currY += obstacleHeight+obstacleGap;
        }
    }

    public void update(){
        if (startTime < Constants.INIT_TIME) startTime = Constants.INIT_TIME;
        int elapsedTime =(int) (System.currentTimeMillis()-startTime);
        startTime = System.currentTimeMillis();

        float speed = (float) ((Math.sqrt(1+(startTime-initTime)/2000.0f))*Constants.SCREEN_HEIGHT/(10000.0f));
        for(Obstacle ob : obstacles){
            ob.incrementY(speed*elapsedTime);
//            ob.incrementY(10f);
        }
        Log.d(TAG, "Speed: "+speed);
        Log.d(TAG, "elapsedTime: "+elapsedTime);
        score+=0.1;

        if(obstacles.get(obstacles.size()-1).getRectangle().top >= Constants.SCREEN_HEIGHT){
            int xStart = (int) (Math.random()*(Constants.SCREEN_WIDTH-playerGap));
            obstacles.add(0, new Obstacle(obstacleHeight, color, xStart,
                    obstacles.get(0).getRectangle().top-obstacleGap,playerGap));
            obstacles.remove(obstacles.size()-1);
        }
    }

    public void reset(){
//        obstacles = new ArrayList<>();
        score=0;
    }

    public void draw(Canvas canvas){
        for(Obstacle ob: obstacles){
            ob.draw(canvas);
        }

        Paint paint = new Paint();
        paint.setTextSize(100);
        paint.setColor(Color.GREEN);
        canvas.drawText("Score: "+(int) score, 50,50 + paint.descent()-paint.ascent() , paint);
    }

    public boolean playerCollide(RectPlayer player){
        for(Obstacle ob: obstacles){
            if(ob.playerCollide(player)) return true;
        }
        return false;
    }
}
