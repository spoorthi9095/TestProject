package com.example.appiness.sampleapp.realm;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class RealmSetup extends Application
{
    @Override
    public void onCreate() {
        super.onCreate();
        // The default Realm file is "default.realm" in Context.getFilesDir();
        // we'll change it to "sample.realm"
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder().name("sample.realm").build();
        Realm.setDefaultConfiguration(config);
    }
}
