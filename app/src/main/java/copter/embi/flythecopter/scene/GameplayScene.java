package copter.embi.flythecopter.scene;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;

import copter.embi.flythecopter.common.Constants;
import copter.embi.flythecopter.game.object.RectPlayer;
import copter.embi.flythecopter.manager.ObstacleManager;
import copter.embi.flythecopter.manager.SceneManager;
import copter.embi.flythecopter.scene.orientation.OrientationData;

/**
 * Created by eMBi on 18.02.2018.
 */

public class GameplayScene implements Scene {

    private Rect centerTextRect = new Rect();
    private RectPlayer player;
    private Point playerPoint;

    private boolean movingPlayer = false;
    private boolean gameOver = false;
    private long gameOverTime;
    private long frameTime;


    private ObstacleManager obstacleManager;
    private OrientationData orientationData;

    public GameplayScene(){
        player = new RectPlayer(new Rect(100, 100, 200, 200), Color.rgb(255,0,0));
        playerPoint = new Point(Constants.SCREEN_WIDTH/2, 3*Constants.SCREEN_HEIGHT/4);
        player.update(playerPoint);

        obstacleManager= new ObstacleManager(Constants.ObstacleManager.playerGap,
                Constants.ObstacleManager.obstacleGap, Constants.ObstacleManager.obstacleHeight,
                Color.BLACK);

        orientationData = new OrientationData();
        orientationData.register();
        frameTime = System.currentTimeMillis();
    }

    public void reset(){
        playerPoint = new Point(Constants.SCREEN_WIDTH/2, 3*Constants.SCREEN_HEIGHT/4);
        player.update(playerPoint);
        obstacleManager= new ObstacleManager(Constants.ObstacleManager.playerGap,
                Constants.ObstacleManager.obstacleGap, Constants.ObstacleManager.obstacleHeight,
                Color.BLACK);
        orientationData.newGame();
        gameOver = false;
        movingPlayer = false;
    }

    @Override
    public void update() {
        if(!gameOver){
            if(frameTime < Constants.INIT_TIME) frameTime = Constants.INIT_TIME;

            int elapsedTime = (int) (System.currentTimeMillis()-frameTime);
            frameTime = System.currentTimeMillis();
            if(orientationData.getStartOrientation() != null && orientationData.getOrientation() != null){
                float pitch = orientationData.getOrientation()[1]-orientationData.getStartOrientation()[1];
                float roll = orientationData.getOrientation()[2]-orientationData.getStartOrientation()[2];

                float xSpeed = roll * Constants.SCREEN_WIDTH/1500f;
                float ySpeed = pitch * Constants.SCREEN_HEIGHT/1500f;

                playerPoint.x += Math.abs(xSpeed*elapsedTime) >=2 ? xSpeed*elapsedTime : 0;
                playerPoint.y -= Math.abs(ySpeed*elapsedTime) >=2 ? ySpeed*elapsedTime : 0;
            }

//            Not move out of screen
            if(playerPoint.x < 0) playerPoint.x=0;
            if(playerPoint.x > Constants.SCREEN_WIDTH) playerPoint.x=Constants.SCREEN_WIDTH;
            if(playerPoint.y < 0) playerPoint.y=0;
            if(playerPoint.y > Constants.SCREEN_HEIGHT) playerPoint.y=Constants.SCREEN_HEIGHT;

            player.update(playerPoint);
            obstacleManager.update();

            if(obstacleManager.playerCollide(player)){
                gameOver = true;
                gameOverTime = System.currentTimeMillis();
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);
        player.draw(canvas);
        obstacleManager.draw(canvas);

        if(gameOver){
            Paint paint = new Paint();
            paint.setTextSize(100);
            paint.setColor(Color.RED);
            drawCenterText(canvas, paint,"Game over!");
        }
    }

    @Override
    public void terminate() {
        SceneManager.ACTIVE_SCENE=0;
    }

    @Override
    public void reciveTouch(MotionEvent event) {
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if(player.getRectangle().contains((int) event.getX(), (int) event.getY())) movingPlayer=true;
                if(gameOver && System.currentTimeMillis() - gameOverTime>= 2000) reset();
                break;
            case MotionEvent.ACTION_MOVE:
                if(movingPlayer) playerPoint.set((int)event.getX(),(int)event.getY());
                break;
            case MotionEvent.ACTION_UP:
                movingPlayer = false;
                break;
        }
    }

    private void drawCenterText(Canvas canvas, Paint paint, String text) {
        paint.setTextAlign(Paint.Align.LEFT);
        canvas.getClipBounds(centerTextRect);
        int cHeight = centerTextRect.height();
        int cWidth = centerTextRect.width();
        paint.getTextBounds(text, 0, text.length(), centerTextRect);
        float x = cWidth / 2f - centerTextRect.width() / 2f - centerTextRect.left;
        float y = cHeight / 2f + centerTextRect.height() / 2f - centerTextRect.bottom;
        canvas.drawText(text, x, y, paint);
    }
}
