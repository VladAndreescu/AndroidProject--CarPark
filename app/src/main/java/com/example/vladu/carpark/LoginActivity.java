package com.example.vladu.carpark;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText usernameLogin = (EditText) findViewById(R.id.usernameEdt);
        final EditText passwordLogin = (EditText) findViewById(R.id.passwordEdt);
        final Button registerLink = (Button) findViewById(R.id.registerLinkBtn);
        final Button login = (Button) findViewById(R.id.loginBtn);

        registerLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(LoginActivity.this,RegisterActivity.class);
                LoginActivity.this.startActivity(registerIntent);
            }
        });
    }
}
