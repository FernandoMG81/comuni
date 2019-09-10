package com.project.comuni.Activities;

import android.app.Application;
import android.os.SystemClock;

public class delayIntro extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SystemClock.sleep(3000);
    }
}
