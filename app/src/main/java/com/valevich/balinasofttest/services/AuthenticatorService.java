package com.valevich.balinasofttest.services;


import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.valevich.balinasofttest.network.sync.Authenticator;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EService;

@EService
public class AuthenticatorService extends Service {
    @Bean
    Authenticator mAuthenticator;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
