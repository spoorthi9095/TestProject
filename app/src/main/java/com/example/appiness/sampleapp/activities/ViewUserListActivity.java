package com.example.appiness.sampleapp.activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.appiness.sampleapp.R;
import com.example.appiness.sampleapp.adapters.UserListAdapter;
import com.example.appiness.sampleapp.realm.RealmConnection;
import com.example.appiness.sampleapp.realm.UserObject;

import io.realm.Realm;
import io.realm.RealmResults;

public class ViewUserListActivity extends AppCompatActivity {

    private Context context = ViewUserListActivity.this;

    private RecyclerView userObjectList;
    private Realm realm = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_user_list);

        initlizeViews();
    }

    private void initlizeViews()
    {
        realm = Realm.getDefaultInstance();

        userObjectList = findViewById(R.id.rv_user_list);
        userObjectList.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));

        RealmResults<UserObject> userObjects = realm.where(UserObject.class).findAll();

        UserListAdapter userListAdapter = new UserListAdapter(context,userObjects);
        userObjectList.setAdapter(userListAdapter);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(realm!=null)
        {
            realm.close();
        }
    }
}
