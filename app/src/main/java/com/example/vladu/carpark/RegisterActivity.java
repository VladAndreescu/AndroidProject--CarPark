package com.example.vladu.carpark;

import android.content.ContentValues;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.VectorEnabledTintResources;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {
    DBManager dbManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText usernameReg = (EditText) findViewById(R.id.usernameRegEdt);
        final EditText emailReg = (EditText) findViewById(R.id.emailRegEdt);
        final EditText passwordReg = (EditText) findViewById(R.id.passwordRegEdt);
        final Button register = (Button) findViewById(R.id.registerBtn);

        final DBManager dbManager = new DBManager(this);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues values = new ContentValues();
                values.put(DBManager.usernameCol,usernameReg.getText().toString());
                values.put(DBManager.emailCol,emailReg.getText().toString());
                values.put(DBManager.passwordCol,passwordReg.getText().toString());
                long id = dbManager.Insert(values);
                if (id>0)
                    Toast.makeText(getApplicationContext(),"Register Completed",Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(getApplicationContext(),"Register Failed",Toast.LENGTH_LONG).show();

            }
        });
    }


}
