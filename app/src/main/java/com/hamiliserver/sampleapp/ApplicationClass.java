package com.hamiliserver.sampleapp;

import com.activeandroid.ActiveAndroid;

public class ApplicationClass extends com.activeandroid.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ActiveAndroid.initialize(this);
    }
}
