package com.example.vladu.carpark;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;


public class LoginActivity extends AppCompatActivity {

    //setup variables
    private String TAG = "LoginActivity";
    private EditText emailLogin;
    private EditText passwordLogin;
    private Button registerLink;
    private Button login;


    //implement firebase authentication
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        //declaring views
        emailLogin = (EditText) findViewById(R.id.emailEdt);
        passwordLogin = (EditText) findViewById(R.id.passwordEdt);
        registerLink = (Button) findViewById(R.id.registerLinkBtn);
        login = (Button) findViewById(R.id.loginBtn);

        // register Button that redirects the user to Register Activity if he does not have an account
        registerLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(LoginActivity.this,RegisterActivity.class);
                LoginActivity.this.startActivity(registerIntent);
            }
        });

        // login button that checks if the user exists in the database
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login();

            }

        });



        }
        //
        private void Login(){

            //declaring user's variables
            String email = emailLogin.getText().toString();
            String password = passwordLogin.getText().toString();

            // check for empty boxes
            if (email.equals("") || password.equals(""))

                Toast.makeText(getApplicationContext(), "Please complete all fields", Toast.LENGTH_LONG).show();


            else {

                //Specific Firebase function that checks if the email and password that user
                //provided is the same with the email and password from Firebase database

                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Log.d(TAG, "signIn:onComplete:" + task.isSuccessful());


                                if (task.isSuccessful()) {
                                    final FirebaseUser currentUser = mAuth.getCurrentUser();

                                    // after the login was successful. Redirect users to Home Activity
                                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                                    finish();
                                } else {

                                    //display a message if the login failed
                                    Toast.makeText(LoginActivity.this, "Login was not successful",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });


            }


        }

    }

