package com.example.vladu.carpark;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText usernameLogin = (EditText) findViewById(R.id.usernameEdt);
        final EditText passwordLogin = (EditText) findViewById(R.id.passwordEdt);
        final Button registerLink = (Button) findViewById(R.id.registerLinkBtn);
        final Button login = (Button) findViewById(R.id.loginBtn);

        final DBManager dbManager = new DBManager(this);

        // Set up navigation link for register button
        registerLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(LoginActivity.this,RegisterActivity.class);
                LoginActivity.this.startActivity(registerIntent);
            }
        });

        //

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = usernameLogin.getText().toString();
                String password = passwordLogin.getText().toString();

                if (username.equals("") || password.equals(""))

                    Toast.makeText(getApplicationContext(), "Please complete all fields", Toast.LENGTH_LONG).show();


                else {
                        String[] usernameProjection = {"Username"};
                        String usernameSelection = "Username=?";
                        String[] usernameSelectionArgs = {username};

                        Cursor usernameCursor = dbManager.query(usernameProjection, usernameSelection, usernameSelectionArgs, DBManager.usernameCol);

                        if (usernameCursor.getCount() > 0) {

                            String[] passwordProjection = {"Password", "Username"};
                            String passwordSelection = "Password =? AND Username =?";
                            String[] passwordSelectionArgs = {password, username};

                            Cursor passwordCursor = dbManager.query(passwordProjection, passwordSelection, passwordSelectionArgs, DBManager.passwordCol);

                            if (passwordCursor.getCount() > 0) {

                                Toast.makeText(getApplicationContext(), "Login successful", Toast.LENGTH_LONG).show();
                                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                                LoginActivity.this.startActivity(registerIntent);

                            }
                            else
                                Toast.makeText(getApplicationContext(), "Login failed. Incorrect password", Toast.LENGTH_LONG).show();
                        }
                        else

                            Toast.makeText(getApplicationContext(), "Username not found", Toast.LENGTH_SHORT).show();


                }
            }

        });



        };

    }

