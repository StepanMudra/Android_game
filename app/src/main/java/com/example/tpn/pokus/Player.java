package com.example.tpn.pokus;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

public class Player implements GameO{

    private Rect playerShape;
    private int color;


    public Player(Rect rect, int color){
        this.playerShape = rect;
        this.color = color;
    }

    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(color);
        canvas.drawRect(playerShape, paint);
    }

    @Override
    public void update() {

    }

    public void  update(Point point){
        playerShape.set(point.x - playerShape.width()/2, point.y - playerShape.height()/2, point.x - playerShape.width()/2, point.y - playerShape.height()/2);
    }
}
