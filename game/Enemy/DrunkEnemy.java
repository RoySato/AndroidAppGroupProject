package com.byuh.cistutors.game.Enemy;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PointF;
import android.view.View;

import com.byuh.cistutors.game.R;

import java.util.Random;

public class DrunkEnemy extends Enemies {

    private boolean decrease_x;
    private boolean decrease_y;
    float slope_x;
    float slope_y;
    float radius;
    float change_y;
    float change_x;
    float max_x;
    float max_y;

    public DrunkEnemy(View parent, float w, float h){
        super(parent, w, h);
        velocity_save = new PointF();
        radius = v/2f;
        setPoints();
        v = (float)Math.sqrt(Math.pow((double)width,2) + Math.pow((double)height,2))/225;

    }

    @Override
    public void trick(float x, float y){
        change_x = max_x/3f;
        change_y = max_y/3f;



            if(change_y>max_y){
                decrease_y=true;
            }
            if(change_y<max_y*(-1)){
                decrease_y=false;
            }
            if(decrease_y){
                slope_y -= change_y;
            }
            else{
                change_y+=change_y;
            }


            if(change_x>max_x){
                decrease_x=true;
            }
            if(change_x<max_x*(-1)){
                decrease_x=false;
            }
            if(decrease_x){
                slope_x-=change_x;
            }
            else{
                slope_x-=change_x;
            }

        bounds.left+=slope_x;
        bounds.top+=slope_y;
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
    public void setDestination(float x, float y) {
        if (!moving) {
            velocity.x = x - bounds.left;
            velocity.y = y - bounds.top;

            float slope = (float) Math.sqrt(Math.pow((double) velocity.x, 2) + Math.pow((double) velocity.y, 2));
            float ratio = v / slope;

            velocity.x = velocity.x * ratio;
            velocity.y = velocity.y * ratio;

            velocity_save.x = velocity.x;
            velocity_save.y = velocity.y;

            float drunk_ratio = (float)Math.sqrt(((float)Math.pow(velocity.x,2)+(float)Math.pow(velocity.y,2)));
            max_x = velocity.y/4f;
            max_y = velocity.x/4f;

            slope_x=(-1)*max_x;
            slope_y=(-1)*max_y;

            if(side==0){
                decrease_x=false;
                decrease_y=true;
            }
            else if(side==1){
                decrease_x=false;
                decrease_y=false;
            }
            else if(side==2){
                decrease_x=true;
                decrease_y=false;
            }
            else {
                decrease_x=true;
                decrease_y=true;
            }


        }


    }

    @Override
    public void setPicture(View parent){
        enemy = BitmapFactory.decodeResource(parent.getResources(), R.drawable.drunk);
        enemy = Bitmap.createScaledBitmap(enemy, (int) sizeW+10, (int) sizeH+(10/6)*7, true);
    }
}

