package com.example.vladu.carpark;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        final Button newRecordBtn = (Button) findViewById(R.id.newRecordBtn);
        final Button viewRecordsbtn = (Button) findViewById(R.id.viewRecBtn);
        final Button changeUserbtn = (Button) findViewById(R.id.changeUserBtn);
        final Button exitApp = (Button) findViewById(R.id.exitBtn);

        //Set up navigation links for the buttons

        //New Record Button
        newRecordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent locationIntent = new Intent(HomeActivity.this,LocationActivity.class);
                HomeActivity.this.startActivity(locationIntent);
            }
        });
    }
}
