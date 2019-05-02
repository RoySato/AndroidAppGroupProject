package com.byuh.cistutors.game.Enemy;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.PointF;
import android.view.View;

import com.byuh.cistutors.game.R;

import java.util.Random;

public class SleepEnemy extends Enemies {


    private boolean time;
    private boolean sleep;
    private int stopwatch;
    private int sleepcounter;
    private int awakecounter;
    private int stopcounter;

    public SleepEnemy(View parent, float w, float h){
        super(parent, w, h);
        velocity_save = new PointF();
        time = false;
        sleep = false;
        sleepcounter = 0;
        awakecounter = 0;
        stopcounter = 0;
        setPoints();
    }

    public void timer() {
        if (!time) {
            if (!sleep) {
                Random rd = new Random();
                stopwatch = rd.nextInt(300) + 100;
                time = true;
            } else {
                Random rd = new Random();
                stopwatch = rd.nextInt(500) + 5;
                time = true;
            }
        }
    }

    @Override
    public void trick(float x, float y){

        if(sleep){
            velocity.x = 0;
            velocity.y = 0;
            if(stopcounter<350){
                Random rd = new Random();
                float random_x = rd.nextFloat()*width;
                float random_y = rd.nextFloat()*height;
                int chase = rd.nextInt(5);
                if(chase==3){
                    newVelocity(x,y);
                }
                else{
                    newVelocity(random_x,random_y);

                }
            }
        }
        else{
            stopcounter++;
            velocity.x = velocity_save.x;
            velocity.y = velocity_save.y;
        }
    }

    @Override
    public void setPoints(){
        float alter = sizeH/4;
        spawn1 = new PointF(bounds.left+((bounds.right-bounds.left)/2),bounds.top+((bounds.bottom-bounds.top)/2));
        spawn2 = new PointF(-10,-10);
        spawn3 = new PointF(-10,-10);
        spawn4 = new PointF(-10,-10);
        spawn5 = new PointF(-10,-10);
        spawn6 = new PointF(-10,-10);
        spawn7 = new PointF(-10,-10);
        spawn8 = new PointF(-10,-10);
    }

    @Override
    public void move(float x, float y) {

        if(sleepcounter == stopwatch && sleep == false){
            sleep = true;
            time = false;
        }
        if(awakecounter == stopwatch && sleep == true){
            sleep = false;

            time = false;
        }
        if(time==true){
            if(!sleep){
                awakecounter = 0;
                sleepcounter++;
            }
            else{
                sleepcounter = 0;
                awakecounter++;
            }
        }
        timer();
        trick(x,y);

        if(bounds.left > 0-sizeW-50 && bounds.left < width+sizeW+50 ){
            bounds.left += velocity.x;
            bounds.right += velocity.x;
            moving = true;

        }
        else{
            delete = true;
        }
        if(bounds.top > 0-sizeH-50 && bounds.top < height+sizeH+50){
            bounds.top += velocity.y;
            bounds.bottom += velocity.y;
            moving = true;
        }
        else{
            delete = true;
        }


        setPoints();

    }

    @Override
    public void setPicture(View parent){
        enemy = BitmapFactory.decodeResource(parent.getResources(), R.drawable.shrek_lady);
        enemy = Bitmap.createScaledBitmap(enemy, (int) sizeW+10, (int) sizeH+(10/6)*7, true);
    }

    public void newVelocity(float x, float y){

            velocity_save.x = x - bounds.left;
            velocity_save.y= y - bounds.top;

            float slope = (float)Math.sqrt(Math.pow((double)velocity_save.x,2) + Math.pow((double)velocity_save.y,2));
            float ratio = v/slope;

            velocity_save.x = velocity_save.x*ratio;
            velocity_save.y = velocity_save.y*ratio;
    }
}

