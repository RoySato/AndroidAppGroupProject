package com.byuh.cistutors.game.Enemy;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.RectF;
import android.view.View;
import android.util.Log;
import com.byuh.cistutors.game.R;
import com.byuh.cistutors.game.util.TickListener;

import java.util.Random;

import static java.lang.Math.abs;



public abstract class Enemies implements TickListener {
    protected RectF bounds;
    protected Bitmap enemy;
    protected float width;
    protected float height;
    protected float sizeW;
    protected float sizeH;
    protected boolean moving;
    protected boolean spawn;
    protected PointF spawnPoint;
    protected PointF velocity;
    protected PointF velocity_save;
    protected PointF spawn1;
    protected PointF spawn2;
    protected PointF spawn3;
    protected PointF spawn4;
    protected PointF spawn5;
    protected PointF spawn6;
    protected PointF spawn7;
    protected PointF spawn8;
    protected int side;
    protected float v;
    protected boolean delete;

    public Enemies(View parent, float w, float h){
        moving = false;
        delete = false;
        sizeW = w / 6;
        sizeH = h / 8;
        width = w;
        height = h;
        spawnPoint = new PointF();
        velocity = new PointF();
        velocity_save = new PointF();
        Random rd = new Random();
        Random rf2 = new Random();
        side = rd.nextInt(4);
        spawnPlace(side, rf2.nextFloat()*10);
        bounds = new RectF(spawnPoint.x, spawnPoint.y, spawnPoint.x + sizeW, spawnPoint.y + sizeH);
        v = (float)Math.sqrt(Math.pow((double)width,2) + Math.pow((double)height,2))/450;

    }

    public void setPoints(){
        spawn1 = new PointF(-10,-10);
        spawn2 = new PointF(-10,-10);
        spawn3 = new PointF(-10,-10);
        spawn4 = new PointF(-10,-10);
        spawn5 = new PointF(-10,-10);
        spawn6 = new PointF(-10,-10);
        spawn7 = new PointF(-10,-10);
        spawn8 = new PointF(-10,-10);

    }

    public void draw(Canvas c){
        c.drawBitmap(enemy, bounds.left, bounds.top, null);

    }

    public void spawn(Canvas c){
        spawn = true;
        c.drawBitmap(enemy, bounds.left, bounds.top, null);
    }

    public void spawnPlace(int a, float b){

        if(b>=1) {


            if (a == 0) {
                spawnPoint.x = (width + sizeW) / b;
                spawnPoint.y = 0 - sizeH;
            }

            if (a == 1) {
                spawnPoint.x = 0 - sizeW;
                spawnPoint.y = (height + sizeH) / b;
            }

            if (a == 2) {
                spawnPoint.x = width / b;
                spawnPoint.y = height;
            }

            if (a == 3) {
                spawnPoint.x = width;
                spawnPoint.y = (height + sizeH) / b;
            }
        }
        else{

            if (a == 0) {
                spawnPoint.x = (width + sizeW) * b;
                spawnPoint.y = 0 - sizeH;
            }

            if (a == 1) {
                spawnPoint.x = 0 - sizeW;
                spawnPoint.y = (height + sizeH) * b;
            }

            if (a == 2) {
                spawnPoint.x = width * b;
                spawnPoint.y = height;
            }

            if (a == 3) {
                spawnPoint.x = width;
                spawnPoint.y = (height + sizeH) * b;
            }

        }

    }

    public void setDestination(float x, float y){
        if(moving!=true){
            velocity.x = x - bounds.left;
            velocity.y = y - bounds.top;

            float slope = (float)Math.sqrt(Math.pow((double)velocity.x,2) + Math.pow((double)velocity.y,2));
            float ratio = v/slope;

            velocity.x = velocity.x*ratio;
            velocity.y = velocity.y*ratio;

            velocity_save.x = velocity.x;
            velocity_save.y = velocity.y;

        }


        System.out.println("side: " + side + " x: " + velocity.x  + " y: " + velocity.y );
        System.out.println(" spawn x: " + spawnPoint.x  + " spawn y: " + spawnPoint.y );
    }

    @Override
    public void onTick() {

    }

    public boolean touching(RectF ya){

        if (ya.contains(spawn1.x, spawn1.y)){
            return true;

        }


        else if (ya.contains(spawn2.x, spawn2.y)){
            return true;

        }

        else if (ya.contains(spawn3.x, spawn3.y)){
            return true;

        }

        else if (ya.contains(spawn4.x, spawn4.y)){
            return true;

        }

        else if (ya.contains(spawn5.x, spawn5.y)){
            return true;

        }

        else if (ya.contains(spawn6.x, spawn6.y)){
            return true;

        }

        else if (ya.contains(spawn7.x, spawn7.y)){
            return true;

        }

        else if (ya.contains(spawn8.x, spawn8.y)){
            return true;

        }

        else{
            return false;

        }

    }


    public abstract void move(float x, float y);

    protected abstract void trick(float x, float y);

    public abstract void setPicture(View parent);

    public boolean getSpawn(){
        return spawn;

    }

    public  boolean getDelete(){
        return delete;
    }

    public RectF getBounds() {
        return bounds;

    }

}
