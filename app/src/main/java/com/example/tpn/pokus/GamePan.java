package com.example.tpn.pokus;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GamePan extends SurfaceView implements SurfaceHolder.Callback{

    private MainThread mainThread;
    private Player player;
    private Point point;

    public GamePan (Context context){
        super(context);
        getHolder().addCallback(this);
        mainThread = new MainThread(getHolder(), this);
        player = new Player(new Rect(100,100,200,200), Color.rgb(0,255,0));
        point = new Point(150, 150);
        setFocusable(true);
    }
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mainThread = new MainThread(getHolder(), this);
        mainThread.setRun(true);
        mainThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        while (true){
            mainThread.setRun(false);
            try {
                mainThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            retry = false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent){
        switch (motionEvent.getAction()){
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                point.set((int) motionEvent.getX(), (int) motionEvent.getY());
        }
        //return super.onTouchEvent(motionEvent);
        return true;
    }

    public void update(){
        player.update(point);
    }


    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);
        canvas.drawColor(Color.WHITE);
        player.draw(canvas);
    }
}
