package com.example.mypc.inventorymanagement;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class AboutActivity extends AppCompatActivity {

    TextView disclamer, informationTxt;

    FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        disclamer = (TextView) findViewById(R.id.disclamerTxt);
        informationTxt = (TextView) findViewById(R.id.inforTxt);
        firebaseFirestore = FirebaseFirestore.getInstance();

        DocumentReference documentReference = firebaseFirestore.collection("Blood Bank").document("howToUseApp");
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()){
                    String dis = documentSnapshot.getString("disclaimer");
                    disclamer.setText(dis);
                    String infor = documentSnapshot.getString("disclaimer");
                    informationTxt.setText(infor);
                }else {
                    Toast.makeText(AboutActivity.this, "There is no information", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this,HomeActivity.class);
        startActivity(intent);
    }
}
