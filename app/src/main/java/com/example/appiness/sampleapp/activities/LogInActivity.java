package com.example.appiness.sampleapp.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.appiness.sampleapp.R;

public class LogInActivity extends AppCompatActivity
{
    /*log in activity*/
    private Context context = LogInActivity.this;

    private EditText userName,password;
    private Button logIn;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        initilizeViews();
        initilizeViewListeners();
    }

    private void initilizeViews()
    {
        userName = findViewById(R.id.et_username);
        password = findViewById(R.id.et_password);

        logIn = findViewById(R.id.btn_log_in);
    }

    private void initilizeViewListeners()
    {
        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean failFlag = false;

                String uName = userName.getText().toString();
                String pass = password.getText().toString();

                if(TextUtils.isEmpty(uName))
                {
                    failFlag = true;
                    userName.setError("Add username.");
                    userName.requestFocus();
                }

                if(TextUtils.isEmpty(pass))
                {
                    failFlag = true;
                    password.setError("Password is empty");
                    password.requestFocus();
                }

                if(failFlag == false)
                {
                    Intent intent = new Intent(context,UserDashBoardActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}
