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

public class RegisterActivity extends AppCompatActivity {

    //implement firebase authentication
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    //declaring variables
    private String TAG = "LoginActivity";
    private EditText emailReg;
    private EditText passwordReg;
    private Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        emailReg = (EditText) findViewById(R.id.emailRegEdt);
        passwordReg = (EditText) findViewById(R.id.passwordRegEdt);
        register = (Button) findViewById(R.id.registerBtn);



        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Register();
                Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                RegisterActivity.this.startActivity(i);
            }
        });
    }

    private void Register(){
        String email = emailReg.getText().toString();
        String password = passwordReg.getText().toString();

        // Empty boxes validation

        if(email.equals("") || password.equals(""))
            Toast.makeText(getApplicationContext(),"Please complete all fields",Toast.LENGTH_LONG).show();

        else {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Log.d(TAG, "createUser:onComplete:" + task.isSuccessful());

                            if (task.isSuccessful()) {
                                FirebaseUser user = task.getResult().getUser();
                                final FirebaseUser currentUser = mAuth.getCurrentUser();


                            } else {
                                Toast.makeText(RegisterActivity.this, "Sign Up has failed",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });}


        }
    }

