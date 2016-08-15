package com.valevich.balinasofttest.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.valevich.balinasofttest.network.sync.SyncAdapter;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EService;

@EService
public class SyncService extends Service{
    @Bean
    SyncAdapter sSyncAdapter;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return sSyncAdapter.getSyncAdapterBinder();
    }
}
