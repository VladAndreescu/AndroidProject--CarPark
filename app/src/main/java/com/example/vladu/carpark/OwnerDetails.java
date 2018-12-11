package com.example.vladu.carpark;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;

public class OwnerDetails extends AppCompatActivity {

    private DatabaseReference mDB;
    private FirebaseAuth mAuth;
    private String userID;
    private FirebaseUser user;

    private EditText ownerName;
    private EditText offence;
    private EditText description;
    private Button takePicture;
    private Button uploadRecord;
    private ImageView photo;

    private  String postcode;
    private String street;
    private String town;
    private String permitNo;
    private String regNo;
    private String carMake;
    private String carModel;
    private Bitmap bitmap;



    private static final int REQUEST_IMAGE_CAPTURE = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_details);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        userID = user.getUid();

        ownerName = (EditText) findViewById(R.id.ownerNameEdt);
        offence = (EditText) findViewById(R.id.offenceEdt);
        description = (EditText) findViewById(R.id.descriptionEdt);
        takePicture = (Button) findViewById(R.id.takePictureBtn);
        uploadRecord = (Button) findViewById(R.id.uploadRecordBtn);
        photo = (ImageView) findViewById(R.id.imageView);

        //Retrieve the information from the LocationActivity and CarDetails activity
        Bundle bundle = getIntent().getExtras();
        postcode = bundle.getString("postcode");
        street = bundle.getString("street");
        town = bundle.getString("town");
        permitNo = bundle.getString("permitNo");
        regNo = bundle.getString("registrationNo");
        carMake= bundle.getString("carMake");
        carModel = bundle.getString("carModel");

        mAuth = FirebaseAuth.getInstance();

        takePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLaunchCamera();
            }
        });

        uploadRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bitmap = ((BitmapDrawable)photo.getDrawable()).getBitmap();
                encodeBitmapSaveToFirebase(bitmap);
                Intent i = new Intent(OwnerDetails.this, HomeActivity.class);
                startActivity(i);

            }
        });


    }

    public void onLaunchCamera(){
        Intent takePhotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(takePhotoIntent.resolveActivity(this.getPackageManager())!= null){
            startActivityForResult(takePhotoIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public  void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == this.RESULT_OK){
            Bundle extras = data.getExtras();
            Bitmap photoBitmap = (Bitmap) extras.get("data");
            photo.setImageBitmap(photoBitmap);
        }
    }

    public void encodeBitmapSaveToFirebase(Bitmap bitmap){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        String photoEncoded = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
        mDB = FirebaseDatabase.getInstance().getReference().child("Records").child(userID);
        String key =mDB.push().getKey();
        mDB.child(key).child("Postcode").setValue(postcode);
        mDB.child(key).child("Street").setValue(street);
        mDB.child(key).child("Town").setValue(town);
        mDB.child(key).child("Permit No").setValue(permitNo);
        mDB.child(key).child("Registration No").setValue(regNo);
        mDB.child(key).child("Car Make").setValue(carMake);
        mDB.child(key).child("Car Model").setValue(carModel);
        mDB.child(key).child("Owner Name").setValue(ownerName.getText().toString());
        mDB.child(key).child("Offence").setValue(offence.getText().toString());
        mDB.child(key).child("Description").setValue(description.getText().toString());
        mDB.child(key).child("Photo").setValue(photoEncoded);




    }
}
