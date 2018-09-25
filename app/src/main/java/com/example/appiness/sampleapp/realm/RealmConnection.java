package com.example.appiness.sampleapp.realm;

import io.realm.Realm;

public class RealmConnection
{
    public static Realm realmInstance;

    private RealmConnection()
    {

    }

    public static Realm getrealmClient()
    {
        if(realmInstance!=null)
        {
            // Create the Realm instance
            realmInstance = Realm.getDefaultInstance();
        }
        return realmInstance;
    }
}
