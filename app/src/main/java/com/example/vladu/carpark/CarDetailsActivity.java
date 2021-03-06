package com.example.vladu.carpark;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CarDetailsActivity extends AppCompatActivity {

    //declaring variables
    private EditText permitNo;
    private EditText regNo;
    private EditText carMake;
    private EditText carModel;
    private Button nextBtn;
    private Button backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_details);

        //declaring views for the variables
        permitNo = (EditText) findViewById(R.id.permitNoEdit);
        regNo = (EditText) findViewById(R.id.carRegEdit);
        carMake = (EditText) findViewById(R.id.makeEdit);
        carModel = (EditText) findViewById(R.id.modelEdit);
        nextBtn = (Button) findViewById(R.id.personDetailsButton);
        backBtn = (Button) findViewById(R.id.backButton);



        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //insert the user's input into variables so I can use them in other Activities
                String permitNoValue = permitNo.getText().toString();
                String regNoValue = regNo.getText().toString();
                String carMakeValue = carMake.getText().toString();
                String carModelValue = carModel.getText().toString();

                //I created a bundle to store the variables came from the previous Activity
                Bundle bundle = getIntent().getExtras();
                String postcode = bundle.getString("postcode");
                String street = bundle.getString("street");
                String town = bundle.getString("town");

                //Setting up a new intent to bring the variables to other Activities
                Intent i =  new Intent(CarDetailsActivity.this, OwnerDetails.class);
                i.putExtra("permitNo", permitNoValue);
                i.putExtra("registrationNo", regNoValue);
                i.putExtra("carMake", carMakeValue);
                i.putExtra("carModel", carModelValue);
                i.putExtra("postcode", postcode);
                i.putExtra("street", street);
                i.putExtra("town", town);
                startActivity(i);
            }

        });

        //backButton should return user to previous Activity
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =  new Intent(CarDetailsActivity.this, LocationActivity.class);
                startActivity(i);
            }
        });


    }
}
