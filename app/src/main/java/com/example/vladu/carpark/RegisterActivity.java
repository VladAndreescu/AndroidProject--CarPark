package com.example.vladu.carpark;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.VectorEnabledTintResources;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {
    DBManager dbManager;
    SQLiteDatabase sqlDB;
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
                //Inserting values into the database after the validation is passed

                ContentValues values = new ContentValues();
                String username = usernameReg.getText().toString();
                String email = emailReg.getText().toString();
                String password = passwordReg.getText().toString();

                // Empty boxes validation

                if(username.equals("") || email.equals("") || password.equals(""))
                    Toast.makeText(getApplicationContext(),"Please complete all fields",Toast.LENGTH_LONG).show();
                else {
                    // Username Validation - make sure that the username is unique
                    String[] usernameProjection = {"Username"};
                    String usernameSelection = "Username=?";

                    String[] usernameSelectionArgs = {username};
                    Cursor usernameCursor = dbManager.query(usernameProjection, usernameSelection, usernameSelectionArgs, DBManager.usernameCol);

                    // Email Validation - make sure that the email is unique

                    String[] emailProjection = {"Email"};
                    String emailSelection = "Email=?";

                    String[] emailSelectionArgs = {email};
                    Cursor emailCursor = dbManager.query(emailProjection, emailSelection, emailSelectionArgs, DBManager.usernameCol);


                    if (usernameCursor.getCount() > 0)
                        Toast.makeText(getApplicationContext(), "Username already exists", Toast.LENGTH_LONG).show();
                    else if (emailCursor.getCount() > 0)
                        Toast.makeText(getApplicationContext(), "Email already exists", Toast.LENGTH_LONG).show();
                    else {

                        values.put(DBManager.emailCol, emailReg.getText().toString());
                        values.put(DBManager.usernameCol, usernameReg.getText().toString());
                        values.put(DBManager.passwordCol, passwordReg.getText().toString());
                        long id = dbManager.Insert(values);

                        // Validating Registration
                        if (id > 0){
                            Toast.makeText(getApplicationContext(), "Register Completed", Toast.LENGTH_LONG).show();
                            Intent loginIntent = new Intent(RegisterActivity.this,LoginActivity.class);
                            RegisterActivity.this.startActivity(loginIntent);
                        }
                        else
                            Toast.makeText(getApplicationContext(), "Register Failed", Toast.LENGTH_LONG).show();
                    }
                }



            }
        });
    }


}
