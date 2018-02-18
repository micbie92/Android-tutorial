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

    private ObstacleManager obstacleManager;

    public GameplayScene(){
        player = new RectPlayer(new Rect(100, 100, 200, 200), Color.rgb(255,0,0));
        playerPoint = new Point(Constants.SCREEN_WIDTH/2, 3*Constants.SCREEN_HEIGHT/4);
        player.update(playerPoint);

        obstacleManager = new ObstacleManager(200, 350, 75, Color.BLACK);
    }

    public void reset(){
        playerPoint = new Point(Constants.SCREEN_WIDTH/2, 3*Constants.SCREEN_HEIGHT/4);
        player.update(playerPoint);
        obstacleManager= new ObstacleManager(200,350,75, Color.BLACK);
        gameOver = false;
        movingPlayer = false;
    }

    @Override
    public void update() {
        if(!gameOver){
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
            paint.setTextSize(200);
            paint.setColor(Color.MAGENTA);
            drawCenterText(canvas, paint,"Game over!!");
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
