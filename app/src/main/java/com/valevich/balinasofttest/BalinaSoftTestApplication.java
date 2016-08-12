package com.valevich.balinasofttest;

import android.app.Application;

import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;

import org.androidannotations.annotations.EApplication;

@EApplication
public class BalinaSoftTestApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //DbFlow init database
        FlowManager.init(new FlowConfig.Builder(this)
                .openDatabasesOnInit(true).build());

    }
}
