package com.byuh.cistutors.game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.Chronometer;
import android.widget.Toast;


import com.byuh.cistutors.game.Enemy.BeegEnemy;
import com.byuh.cistutors.game.Enemy.CircleEnemy;
import com.byuh.cistutors.game.Enemy.DrunkEnemy;
import com.byuh.cistutors.game.Enemy.Enemies;
import com.byuh.cistutors.game.Enemy.LinearEnemy;
import com.byuh.cistutors.game.Enemy.SleepEnemy;
import com.byuh.cistutors.game.util.TickListener;
import com.byuh.cistutors.game.util.Timer2;
import com.byuh.cistutors.game.util.Quadtree;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;


/**
 * Created by Student on 1/10/2019.
 */
public class GameView extends android.support.v7.widget.AppCompatImageView implements TickListener {

    private Player shrek;
    private boolean firstrun;
    private float height;
    private float width;
    float originalX;
    float originalY;
    float joyX=-100;
    float joyY=-100;
    private  float spawn_timer;
    private ArrayList<Enemies> enemy;
    float movex;
    float movey;
    float vertSpeed;
    float horSpeed;
    private Timer2 time;
    boolean joystick = false;
    Quadtree quad;
    List returnObjects = new ArrayList<Enemies>();
    private Paint paint;

    public GameView(Context context) {
        super(context);
        firstrun = true;
        time = new Timer2(10);
        spawn_timer = -1;
        enemy = new ArrayList<Enemies>();

    }

    @Override
    public void onDraw(Canvas c) {
        super.onDraw(c);
        if (firstrun) {
            init();

            firstrun = false;
        }

        for(int i=0; i<enemy.size(); i++){
            if(enemy.get(i).getSpawn()==false){
                enemy.get(i).spawn(c);
            }
            else if(enemy.get(i).getDelete()==true){
                enemy.remove(i);
                System.out.println(enemy.size());
            }
            else{
                enemy.get(i).draw(c);
            }
        }


        if (shrek.living()) {

            shrek.draw(c);
//            RectF boundry = shrek.getBounds();
//            paint = new Paint();
//            paint.setStrokeWidth(15);
//            paint.setColor(Color.BLACK);
//            paint.setStyle(Paint.Style.STROKE);
            //c.drawRect(boundry.left, boundry.top, boundry.right, boundry.bottom, paint);
        }else{
            time.unsubscribe(shrek);
        }
        //enemy1.draw(c);
        if (joystick = true){
            Paint paint = new Paint();
            paint.setStyle(Paint.Style.FILL);
            c.drawCircle(originalX, originalY, width/100,paint);
            paint.setColor(Color.BLUE);
            float maxRadius = width/6;
            float displacement = (float) Math.sqrt(Math.pow(joyX - originalX, 2) + Math.pow(joyY - originalY, 2));
            float ratio = maxRadius / displacement;
            double yDif = Math.abs(joyY-originalY);
            double xDif = Math.abs(joyX-originalX);
            if(displacement > maxRadius){
                float constrainedX = originalX + (joyX - originalX) * ratio;
                joyX = constrainedX;

                float constrainedY = originalY + (joyY - originalY) * ratio;
                joyY = constrainedY;
            }

            c.drawCircle(joyX, joyY, width/14,paint);

        }

    }

    private void init() {
        width = getWidth();
        height = getHeight();
        quad = new Quadtree(0,new RectF(0,0, width,height));

        shrek = new Player(this, width/2, height/2, height, width);

        time.subscribe(this);
        time.subscribe(shrek);

    }
    private void touching(){
        //int count = 0;
        for (int i=0; i<returnObjects.size();i++){
            //count++;
            Enemies e = (Enemies) returnObjects.get(i);

            if (e.touching(shrek.getBounds())){

                shrek.death();
            }


        }
        //System.out.println(count);
//        for (Enemies e: returnObjects){
//            if (e.touching(shrek.getBounds())){
//                time.unsubscribe(shrek);
//                shrek.death();
//            }
//        }
//        if (shrek.contains(enemy1.getBounds())){
//
//            time.unsubscribe(shrek);
//            shrek.death();
//        }
//        for (Enemies e: enemy){
//            if (shrek.contains(e.getBounds())){
//
//                time.unsubscribe(shrek);
//                shrek.death();
//            }
//        }
    }

    public boolean onTouchEvent(MotionEvent m) {
        float x = m.getX();
        float y = m.getY();



        if (m.getAction() == MotionEvent.ACTION_DOWN) {

            originalX = m.getX();
            originalY = m.getY();
            joyX = x;
            joyY = y;

        } else if (m.getAction() == MotionEvent.ACTION_MOVE) {

            joyX = x;
            joyY = y;
            movex = 0;
            movey = 0;
            boolean upDown;
            if (y>originalY){//These for statements check direction, and compute movement.
                upDown = true;
                float check = y-originalY; //difference
                Speed(check, upDown);

                movey = (vertSpeed)*(height/600);
            }
            if (y<originalY){
                upDown = true;
                float check = Math.abs(y-originalY);
                Speed(check, upDown);

                movey = (vertSpeed)*(height/600)*-1;
            }
            if (x>originalX){
                upDown = false;
                float check = x-originalX;
                Speed(check, upDown);

                movex = (horSpeed)*(height/600);
            }
            if (x<originalX){
                upDown = false;
                float check = Math.abs(x-originalX)                                                                                                                                            ;
                Speed(check, upDown);

                movex = (horSpeed)*(height/600)*-1;
            }

        } else if (m.getAction() == MotionEvent.ACTION_UP) {
            movex = 0;//movement should stop upon release
            movey = 0;
            originalX = -100;
            originalY = -100;
            joyX = -100;
            joyY = -100;

        }
        return true;
    }

    public void Speed(float n, boolean w){
        float movementTeir = (width/6)/10;
        if (0<n && n<movementTeir/4){
            if (w){
                vertSpeed = 0;
            }else{
                horSpeed = 0;
            }

        }
        else if (movementTeir/4<n && n<movementTeir/2){
            if (w){
                vertSpeed = 1/3;
            }else{
                horSpeed = 1/3;
            }
        }
        else if (movementTeir/2<n && n<movementTeir*(3/4)){
            if (w){
                vertSpeed = 1/2;
            }else{
                horSpeed = 1/2;
            }
        }
        else if (movementTeir*(3/4)<n && n<movementTeir){
            if (w){
                vertSpeed = 3/4;
            }else{
                horSpeed = 3/4;
            }
        }

        else if (movementTeir<n && n<movementTeir*2){
            if (w){
                vertSpeed = 1;
            }else{
                horSpeed = 1;
            }
        }
        else if (movementTeir*2<n && n<movementTeir*3){
            if (w){
                vertSpeed = 1+(1/2);
            }else{
                horSpeed = 1+(1/2);
            }
        }
        else if (movementTeir*3<n && n<movementTeir*4){
            if (w){
                vertSpeed = 2;
            }else{
                horSpeed = 2;
            }
        }
        else if (movementTeir*4<n && n<movementTeir*5){
            if (w){
                vertSpeed = 2+(1/2);
            }else{
                horSpeed = 2+(1/2);
            }
        }
        else if (movementTeir*5<n && n<movementTeir*6){
            if (w){
                vertSpeed = 3;
            }else{
                horSpeed = 3;
            }
        }
        else if (movementTeir*6<n && n<movementTeir*7){
            if (w){
                vertSpeed = 3+(1/2);
            }else{
                horSpeed = 3+(1/2);
            }
        }
        else if (movementTeir*7<n && n<movementTeir*8){
            if (w){
                vertSpeed = 4;
            }else{
                horSpeed = 4;
            }
        }
        else if (movementTeir*8<n && n<movementTeir*9){
            if (w){
                vertSpeed = 4+(1/2);
            }else{
                horSpeed = 4+(1/2);
            }
        }
        else{
            if (w){
                vertSpeed = 5;
            }else{
                horSpeed = 5;
            }
        }
    }
    @Override
    public void onTick() {

        shrek.move(movex,movey);
        spawn_timer++;
        if(spawn_timer%150==0){
            Random rd = new Random();
            int enemy_pick = rd.nextInt(4);
            spawnEnemy(enemy_pick);
            int haste_clock = rd.nextInt(10);
            if(1 <=haste_clock && haste_clock <= 3){
                spawn_timer+=40;
            }
            if(0 ==haste_clock || 9 ==haste_clock){
                spawn_timer-=20;
            }

        }
        quad.clear();
        for(Enemies e : enemy){
            if(e.getSpawn()){
                e.move(shrek.getLeft(), shrek.getTop());
            }
            quad.insert(e);
        }
        returnObjects.clear();
        quad.retrieve(returnObjects,shrek.getBounds());
        if (shrek.living()) {
            touching();
        }

        invalidate();

    }

    public void manageEnemyCondition(Enemies e){
        e.setPoints();
        e.setPicture(this);
        Random rd = new Random();
        int chase = rd.nextInt(5);
        if(chase == 3){
            e.setDestination(shrek.getLeft(), shrek.getTop());
        }
        else{
            e.setDestination(randomX(), randomY());
        }

        enemy.add(e);
    }

    public void spawnEnemy(int r){
        if(r == 0) {
            Enemies e1 = new LinearEnemy(this,width,height);
            manageEnemyCondition(e1);
        }
        if(r == 1){
            Enemies e = new SleepEnemy(this,width,height);
            manageEnemyCondition(e);
        }
        if(r == 2){
            Enemies e2 = new BeegEnemy(this,width,height);
            manageEnemyCondition(e2);
        }
        if(r==3){
            Enemies e3 = new CircleEnemy(this, width, height);
            manageEnemyCondition(e3);
        }
        if(r==4){
            Enemies e4 = new DrunkEnemy(this, width, height);
            manageEnemyCondition(e4);
        }
    }

    public float randomX(){
        Random rd = new Random();
        return rd.nextFloat()*width;

    }
    public float randomY(){
        Random rd = new Random();
        return rd.nextFloat()*height;

    }


}

