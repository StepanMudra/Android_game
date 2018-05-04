package com.example.tpn.pokus;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class MainThread extends Thread{

    public static final int maxFPS = 30;
    private double averageFPS;
    private SurfaceHolder surfaceHolder;
    private GamePan gamePan;
    private boolean run;
    public static Canvas canvas;
    public MainThread(SurfaceHolder surfaceHolder, GamePan gamePan){
        super();
        this.surfaceHolder = surfaceHolder;
        this.gamePan = gamePan;
    }

    public void setRun(boolean run){this.run = run; }

    @Override
    public void run(){
        long startTime;
        long timeMillis = 1000/maxFPS;
        long waitTime;
        int frameCount = 0;
        long totalTime = 0;
        long targetTime = 1000/maxFPS;

        while (run){
            startTime = System.nanoTime();
            canvas = null;
            canvas = this.surfaceHolder.lockCanvas();
            synchronized (surfaceHolder){
                this.gamePan.update();
                this.gamePan.draw(canvas);
            }
            if(canvas != null){
                surfaceHolder.unlockCanvasAndPost(canvas);
            }
            timeMillis = (System.nanoTime()-startTime/1000000);
            waitTime = targetTime - timeMillis;
            if (waitTime > 0){
                try {
                    Thread.sleep(waitTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            totalTime += System.nanoTime() - startTime;
            frameCount++;
            if(frameCount == maxFPS){
                averageFPS = 1000/((totalTime/frameCount)/1000000);
                frameCount = 0;
                totalTime = 0;
                System.out.println(averageFPS);
            }
        }
    }
}
