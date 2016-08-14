package com.valevich.balinasofttest;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.support.multidex.MultiDex;

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

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            MultiDex.install(base);
        }
    }

}
