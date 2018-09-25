package com.example.appiness.sampleapp.activities;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.appiness.sampleapp.R;
import com.example.appiness.sampleapp.realm.RealmConnection;
import com.example.appiness.sampleapp.realm.UserObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmResults;

public class UserDashBoardActivity extends AppCompatActivity {

    private Context context = UserDashBoardActivity.this;

    private EditText name,aboutMe;
    private TextView dateOfBirth;

    private Button saveDetails;

    private Realm realm = null;

    private FloatingActionButton viewUserList;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dash_board);

        initilizeViews();
        initilizeListeners();
    }

    @SuppressLint("RestrictedApi")
    private void initilizeViews()
    {
        name = findViewById(R.id.et_name);
        aboutMe = findViewById(R.id.et_about_me);

        dateOfBirth = findViewById(R.id.tv_dob);

        saveDetails = findViewById(R.id.btn_save);

        viewUserList = findViewById(R.id.fab_view_user_list);

        dateOfBirth.setText(getCurrentDate());

        realm = Realm.getDefaultInstance();

        long count = realm.where(UserObject.class).count();

        if(count>0)
        {
            viewUserList.setVisibility(View.VISIBLE);
        }
    }

    private String getCurrentDate() {
        Date c = Calendar.getInstance().getTime();

        SimpleDateFormat df = new SimpleDateFormat("dd/MMM/yyyy");
        return df.format(c);
    }

    private void initilizeListeners()
    {
        dateOfBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                dateOfBirth.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        saveDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //validate befor saving
                boolean failFlag = false;

                String uName,uDOB,uAboutMe;

                uName = name.getText().toString().trim();
                uDOB = dateOfBirth.getText().toString();
                uAboutMe = aboutMe.getText().toString().trim();


                if(TextUtils.isEmpty(uName))
                {
                    failFlag = true;
                    name.setError("Add name.");
                    name.requestFocus();
                }


                if(TextUtils.isEmpty(uAboutMe))
                {
                    failFlag = true;
                    aboutMe.setError("Add description");
                    aboutMe.requestFocus();
                }

                if(failFlag==false) {
                    UserObject userObject = new UserObject();
                    userObject.setName(uName);
                    userObject.setDob(uDOB);
                    userObject.setAbout(uAboutMe);

                    addToRealm(userObject);
                }
            }
        });

        viewUserList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,ViewUserListActivity.class);
                startActivity(intent);
            }
        });
    }

    private void addToRealm(final UserObject userObject)
    {
        realm.executeTransaction(new Realm.Transaction() {
            @SuppressLint("RestrictedApi")
            @Override
            public void execute(Realm realm) {
                // increment index
                Number currentIdNum = realm.where(UserObject.class).max("id");
                int nextId;
                if(currentIdNum == null) {
                    nextId = 1;
                } else {
                    nextId = currentIdNum.intValue() + 1;
                }
                UserObject user = userObject; // unmanaged
                user.setId(nextId);
                //...
                realm.insertOrUpdate(user);

                RealmResults<UserObject> userObjects = realm.where(UserObject.class).findAll();
                Log.e("List :","realmList:"+userObjects);

                name.setText("");
                aboutMe.setText("");
                dateOfBirth.setText(getCurrentDate());

                if(viewUserList.getVisibility() == View.GONE)
                {
                    viewUserList.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(realm!=null) {
            realm.close();
        }
    }
}
