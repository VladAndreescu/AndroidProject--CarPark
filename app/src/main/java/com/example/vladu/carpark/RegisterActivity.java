package com.example.vladu.carpark;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.VectorEnabledTintResources;
import android.widget.Button;
import android.widget.EditText;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText usernameReg = (EditText) findViewById(R.id.usernameRegEdt);
        final EditText emailReg = (EditText) findViewById(R.id.emailRegEdt);
        final EditText passwordReg = (EditText) findViewById(R.id.passwordRegEdt);
        final Button register = (Button) findViewById(R.id.registerBtn);

        DBManager dbManager = new DBManager(this);
    }
}
