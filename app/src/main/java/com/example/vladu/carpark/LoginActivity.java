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


        emailLogin = (EditText) findViewById(R.id.emailEdt);
        passwordLogin = (EditText) findViewById(R.id.passwordEdt);
        registerLink = (Button) findViewById(R.id.registerLinkBtn);
        login = (Button) findViewById(R.id.loginBtn);

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
                Login();

            }

        });



        };

        private void Login(){
            String email = emailLogin.getText().toString();
            String password = passwordLogin.getText().toString();

            if (email.equals("") || password.equals(""))

                Toast.makeText(getApplicationContext(), "Please complete all fields", Toast.LENGTH_LONG).show();


            else {
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Log.d(TAG, "signIn:onComplete:" + task.isSuccessful());


                                if (task.isSuccessful()) {
                                    final FirebaseUser currentUser = mAuth.getCurrentUser();
                                    // Go to User Area Activity
                                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                                    finish();
                                } else {
                                    Toast.makeText(LoginActivity.this, "Login was not succesfull",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });


            }


        }

    }

