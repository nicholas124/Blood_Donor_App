package com.example.mypc.inventorymanagement;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DetailsActivity extends AppCompatActivity {

    private TextView detailImage;
    private TextView detailName;
    private TextView detailGender;
    private TextView detailEmail;
    private TextView detailPhone;
    private TextView detailAddress;
    private TextView detailCity;
    private TextView detailBloodType;
    private TextView detailBloodStatus;

    FirebaseDatabase database;
    DatabaseReference details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        database = FirebaseDatabase.getInstance();
        details = database.getReference();

        detailImage = (TextView) findViewById(R.id.detail_donor_image);
        detailName= (TextView) findViewById(R.id.detail_name);
        detailGender = (TextView) findViewById(R.id.detail_gender);
        detailEmail = (TextView) findViewById(R.id.detail_email);
        detailPhone= (TextView) findViewById(R.id.detail_phone);
        detailAddress= (TextView) findViewById(R.id.detail_address);
        detailCity = (TextView) findViewById(R.id.detail_city);
        detailBloodType = (TextView) findViewById(R.id.detail_bloodtype);
        detailBloodStatus = (TextView) findViewById(R.id.detail_donor_status);

        details.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
