package com.byuh.cistutors.game.Enemy;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PointF;
import android.view.View;

import com.byuh.cistutors.game.R;

public class BeegEnemy extends Enemies {

    public BeegEnemy(View parent, float w, float h){
        super(parent, w, h);
        setPoints();
        sizeW = w / 2;
        sizeH = h / 3;
        v = (float)Math.sqrt(Math.pow((double)width,2) + Math.pow((double)height,2))/1400;

    }

    @Override
    public void setPoints() {
        float alter = sizeH / 4;
        spawn1 = new PointF(bounds.left + ((bounds.right - bounds.left) / 2), bounds.top + ((bounds.bottom - bounds.top) / 2));
        spawn2 = new PointF(bounds.left + ((bounds.right - bounds.left) / 2), bounds.top);
        spawn3 = new PointF(bounds.left + ((bounds.right - bounds.left) / 2), bounds.bottom);
        spawn4 = new PointF(-10, -10);
        spawn5 = new PointF(-10, -10);
        spawn6 = new PointF(-10, -10);
        spawn7 = new PointF(-10, -10);
        spawn8 = new PointF(-10, -10);
    }
    @Override
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
    public void move(float x, float y){
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
    public void trick(float x, float y) {

    }

    @Override
    public void setPicture(View parent){
        enemy = BitmapFactory.decodeResource(parent.getResources(), R.drawable.beegyoshi);
        enemy = Bitmap.createScaledBitmap(enemy, (int) sizeW+10, (int) sizeH+(10/6)*7, true);

    }
}

