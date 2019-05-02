package com.byuh.cistutors.game.util;


import android.os.Handler;
import android.os.Message;

import java.util.ArrayList;
import java.util.List;

public class Timer2 extends Handler {
    private int delay;
    private List<TickListener> observers;
    private boolean paused = false;

    public Timer2(int delay1){
        delay = delay1;
        observers = new ArrayList<>();
        handleMessage(obtainMessage());
    }

    public void pause(){ paused = true; }
    public void unpause(){paused = false;}
    public void subscribe(TickListener t) {
        observers.add(t);
    }

    public void unsubscribe(TickListener t) {
        observers.remove(t);
    }


    @Override
    public void handleMessage(Message m) {

        if (!paused) {
            for (TickListener t : observers) {
                t.onTick();
            }
        }
        sendMessageDelayed(obtainMessage(), delay);
    }
}
