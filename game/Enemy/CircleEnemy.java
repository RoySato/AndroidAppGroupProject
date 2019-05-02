package com.byuh.cistutors.game.Enemy;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PointF;
import android.view.View;

import com.byuh.cistutors.game.R;

import java.util.Random;

public class CircleEnemy extends Enemies {

    private boolean decrease_x;
    private boolean decrease_y;
    private float radius;
    private float default_x;
    private float default_y;

    public CircleEnemy(View parent, float w, float h){
        super(parent, w, h);
        setPoints();
        v = (float)Math.sqrt(Math.pow((double)width,2) + Math.pow((double)height,2))/2000;
        decrease_x = false;
        decrease_y = true;
        radius = 2f;
        default_x = 4f;
        default_y = 0f;
    }

    @Override
    public void trick(float x, float y){
        float slope = radius/60f;
        if(decrease_x){
            default_x -= slope;
        }else{
            default_x += slope;
        }
        if(default_x > radius){
            decrease_x=true;
        }
        if(default_x<0-radius){
            decrease_x=false;
        }
        if(decrease_y){
            default_y -= slope;
        }else{
            default_y += slope;
        }
        if(default_y > radius){
            decrease_y=true;
        }
        if(default_y<0-radius){
            decrease_y=false;
        }

        bounds.left += default_x;
        bounds.top += default_y;


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
    public void move(float x, float y){
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
        enemy = BitmapFactory.decodeResource(parent.getResources(), R.drawable.escape);
        enemy = Bitmap.createScaledBitmap(enemy, (int) sizeW+10, (int) sizeH+(10/6)*7, true);

    }

    @Override
    public void setDestination(float x, float y){
        velocity.x=0;
        velocity.y=0;
        if(moving!=true){

            velocity.x = x - bounds.left;
            velocity.y = y - bounds.top;

            float slope = (float)Math.sqrt(Math.pow((double)velocity.x,2) + Math.pow((double)velocity.y,2));
            float ratio = v/slope;

            velocity.x = velocity.x*ratio;
            velocity.y = velocity.y*ratio;

            velocity_save.x = velocity.x;
            velocity_save.y = velocity.y;



            default_y = velocity.y;

            if(side==0){
                decrease_y=false;
                default_y=radius;
                default_x=0;
                if(velocity.x<0){
                    decrease_x=true;
                }
                else{
                    decrease_x=false;
                }
            }
            if(side==1){
                decrease_x=false;
                default_x=radius;
                default_y=0;
                if(velocity.y<0){
                    decrease_y=true;
                }
                else{
                    decrease_y=false;
                }
            }
            if(side==2){
                decrease_y=true;
                default_y=0-radius;
                default_x=0;
                if(velocity.x<0){
                    decrease_x=true;
                }
                else{
                    decrease_x=false;
                }
            }
            if(side==1){
                decrease_x=true;
                default_x=0-radius;
                default_y=0;
                if(velocity.y<0){
                    decrease_y=true;
                }
                else{
                    decrease_y=false;
                }
            }
        }

    }
}
