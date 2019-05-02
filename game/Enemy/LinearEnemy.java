package com.byuh.cistutors.game.Enemy;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PointF;
import android.view.View;

import com.byuh.cistutors.game.R;

public class LinearEnemy extends Enemies {

    public LinearEnemy(View parent, float w, float h){
        super(parent, w, h);
        setPoints();

    }

    @Override
    public void trick(float x, float y){

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
        enemy = BitmapFactory.decodeResource(parent.getResources(), R.drawable.shrek2);
        enemy = Bitmap.createScaledBitmap(enemy, (int) sizeW+10, (int) sizeH+(10/6)*7, true);

    }

}
