package com.byuh.cistutors.game;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {

    private GameView gv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gv = new GameView(this);
        setContentView(gv);
    }
}
