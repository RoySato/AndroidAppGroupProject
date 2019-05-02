package com.byuh.cistutors.game;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.view.View;

import com.byuh.cistutors.game.util.TickListener;

/**
 * Created by Student on 1/10/2019.
 */

public class Player implements TickListener {

    private RectF bounds;
    private boolean alive = true;
    private static Bitmap dude;
    private float width;
    private float height;
    private float staggerx;
    private float staggery;


    public Player( View parent, float x, float y, float h, float w) {
        float sizeW = w / 5;
        float sizeH = h / 7;
        staggerx = sizeW/3;
        staggery = sizeH/4;
        width = w;
        height = h;

        bounds = new RectF(x+staggerx*2, y+staggery, x+sizeW, y+sizeH-staggery);
        dude = BitmapFactory.decodeResource(parent.getResources(), R.drawable.shrek);
        dude = Bitmap.createScaledBitmap(dude, (int) sizeW, (int) sizeH, true);
    }

    public void draw(Canvas c){
        c.drawBitmap(dude, bounds.left-(staggerx), bounds.top-staggery, null);

    }
    public float getLeft(){
        return bounds.left;
    }
    public float getTop(){
        return bounds.top;
    }
    public boolean contains(float x, float y) {
        return bounds.contains(x,y);
    }

    public RectF getBounds() {
        return bounds;
    }
    public void death(){
        alive = false;
    }
    public boolean living(){
        return alive;
    }
    public void move(float x, float y){
        if (bounds.right + x < width && bounds.left + x >-40){
            bounds.left += x;
            bounds.right += x;
        }
        if (bounds.bottom + y < height && bounds.top + y > -40){
            bounds.top += y;
            bounds.bottom += y;
        }

    }

    @Override
    public void onTick() {

    }
}
