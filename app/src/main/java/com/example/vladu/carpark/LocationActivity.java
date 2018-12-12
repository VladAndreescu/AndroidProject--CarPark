package com.example.vladu.carpark;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener;
import com.google.android.gms.maps.GoogleMap.OnMyLocationClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class LocationActivity extends AppCompatActivity
        implements
        OnMyLocationButtonClickListener,
        OnMyLocationClickListener,
        OnMapReadyCallback,
        ActivityCompat.OnRequestPermissionsResultCallback {

    // location permission request
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;


    private boolean mPermissionDenied = false;

    private GoogleMap mMap;

    EditText postcodeEdit;
    EditText townEdit;
    Button getAddresses;
    JSONObject jsonObject;
    JSONArray jsonArray;
    ArrayList<String> addresses;
    ProgressDialog pd;
    Button nextbtn;
    Spinner addressSpinner;

    private DatabaseReference mDB;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        //Implemented Google Maps API
        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //declaring Firebase variables
        mDB = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        //declaring variables
        postcodeEdit = (EditText) findViewById(R.id.postcodeEdit);
        townEdit = (EditText) findViewById(R.id.townEdit);
        getAddresses = (Button) findViewById(R.id.findAddressBtn);
        nextbtn = (Button) findViewById(R.id.locationNextBtn);

        // Button that calls the GET Request to the Server in order to return the data in JSON format
        getAddresses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new getJsonData().execute("https://api.getAddress.io/find/" + postcodeEdit.getText().toString() + "?api-key=Q8BXrVkS7EGLaUmdNH8lIw16531");
            }
        });

        //Save the user's inputs and bring them along the activities using Intent
        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String postcodeValue = postcodeEdit.getText().toString();
                String streetValue = addressSpinner.getSelectedItem().toString();
                String townValue = townEdit.getText().toString();
                Intent i = new Intent(LocationActivity.this, CarDetailsActivity.class);
                i.putExtra("postcode", postcodeValue);
                i.putExtra("town", townValue);
                i.putExtra("street", streetValue);
                startActivity(i);
            }
        });



    }
    // Class Created in order to retrieve the JSON data from the URL API
    private class getJsonData extends AsyncTask<String, String, String> {


        protected void onPreExecute() {
            super.onPreExecute();

            //Setting up a ProgressDialog in order to display a loading image on the screen while data is handled
            pd = new ProgressDialog(LocationActivity.this);
            pd.setMessage("Please wait");
            pd.setCancelable(false);
            pd.show();
        }

        protected String doInBackground(String... uri){

            //declaring variables
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response;

            //this string will hold the JSON data
            String responseString = null;
            try{
                response = httpclient.execute(new HttpGet(uri[0]));
                StatusLine statusLine = response.getStatusLine();
                if(statusLine.getStatusCode() == HttpStatus.SC_OK){

                    //take the JSON data and add it in responseString
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    response.getEntity().writeTo(baos);
                    baos.close();
                    responseString = baos.toString();

                    //display the JSON in the console
                    Log.d("Response", responseString);
                }else {

                    //throw an error if the status is not OK
                    response.getEntity().getContent().close();
                    throw new IOException(statusLine.getReasonPhrase());
                }
            } catch (Exception e){

                //throw an error if there is an error on the route
                Log.d("Error", "Request failed");
            }
            return responseString;
        }

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);

            //hide the ProgressDialog after the process finished
            if (pd.isShowing()){
                pd.dismiss();
            }
            try{
                //convert the JSON received from previous function into an Array of Strings
                jsonObject = new JSONObject(response);
                jsonArray = jsonObject.getJSONArray("addresses");
                //add each address found in the JSON into the addresses ArrayList
                ArrayList<String> addresses = new ArrayList<>();
                for(int i = 0; i < jsonArray.length(); i++){
                    String spinnerElement = jsonArray.getString(i);
                    addresses.add(spinnerElement);
                }

                //declaring the spinner view
                addressSpinner = (Spinner) findViewById(R.id.addressSpinner);

                //Insert the addresses ArrayList into the spinner
                addressSpinner.setAdapter(new ArrayAdapter<String>(LocationActivity.this,
                        android.R.layout.simple_spinner_dropdown_item,addresses));

                // This application only requires the information from the SelectedItem.
                //which is taken by using addressSpinner.getSelectedItem().toString();
                addressSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

            }catch(JSONException e){
                e.printStackTrace();
            }




        }
    }





    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;

        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMyLocationClickListener(this);
        enableMyLocation();
    }

    //enables my location if permission was granted
    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission to access the location is missing.
            PermissionUtils.requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE,
                    Manifest.permission.ACCESS_FINE_LOCATION, true);
        } else if (mMap != null) {
            // Access to the location has been granted to the app.
            mMap.setMyLocationEnabled(true);
        }
    }

    @Override
    //test to see if the myLocation button was clicked
    public boolean onMyLocationButtonClick() {
        Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT).show();
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        Toast.makeText(this, "Current location:\n" + location, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            return;
        }

        if (PermissionUtils.isPermissionGranted(permissions, grantResults,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Enable the my location layer if the permission has been granted.
            enableMyLocation();
        } else {
            // Display the missing permission error dialog when the fragments resume.
            mPermissionDenied = true;
        }
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        if (mPermissionDenied) {
            // Permission was not granted, display error dialog.
            showMissingPermissionError();
            mPermissionDenied = false;
        }
    }

    //display an error message if the permission was declined
    private void showMissingPermissionError() {
        PermissionUtils.PermissionDeniedDialog
                .newInstance(true).show(getSupportFragmentManager(), "dialog");
    }

}

