package com.example.vladu.carpark;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class OwnerDetails extends AppCompatActivity {


    private EditText ownerName;
    private EditText offence;
    private EditText description;
    private Button takePicture;
    private Button uploadRecord;
    private ImageView photo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_details);

        ownerName = (EditText) findViewById(R.id.ownerNameEdt);
        offence = (EditText) findViewById(R.id.offenceEdt);
        description = (EditText) findViewById(R.id.descriptionEdt);
        takePicture = (Button) findViewById(R.id.takePictureBtn);
        uploadRecord = (Button) findViewById(R.id.uploadRecordBtn);
        photo = (ImageView) findViewById(R.id.imageView);

        //Retrieve the information from the LocationActivity and CarDetails activity
        Bundle bundle = getIntent().getExtras();
        final String postcode = bundle.getString("postcode");
        final String street = bundle.getString("street");
        final String town = bundle.getString("town");
        final String permitNo = bundle.getString("permitNo");
        final String regNo = bundle.getString("registrationNo");
        final String carMake= bundle.getString("carMake");
        final String carModel = bundle.getString("carModel");

        
    }
}
