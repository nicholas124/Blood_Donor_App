package com.example.mypc.inventorymanagement;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.algolia.search.saas.Client;
import com.algolia.search.saas.Index;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class DonorRegistrationActivity extends AppCompatActivity {

    private Button registerbtn;
    private Uri mainImageUri;
    private EditText email;
    private EditText phoneNumber;
    private EditText address;
    private EditText city;
    private EditText name;
    private CircleImageView signupImage;
    private Spinner gender;
    private Spinner donoroption;
    private RadioButton bloodStatus;
    private String currentUser;
    private ProgressBar progressBar;

    private NotificationManagerCompat notificationManager;

    FirebaseFirestore firestore;
    FirebaseAuth mAuth;
    StorageReference stroageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_registration);

       // notificationManager = NotificationManagerCompat.from(this);

        firestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        stroageReference = FirebaseStorage.getInstance().getReference();
        progressBar = (ProgressBar) findViewById(R.id.progress_bar_dr);

        currentUser = mAuth.getCurrentUser().getUid();

        registerbtn = (Button) findViewById(R.id.reg_button);
        phoneNumber = (EditText) findViewById(R.id.signup_phone_number);
        gender = (Spinner) findViewById(R.id.signup_blood_status);
        bloodStatus = (RadioButton) findViewById(R.id.donor_or_not);
        email = (EditText) findViewById(R.id.signup_email);
        city = (EditText) findViewById(R.id.signup_city);
        address = (EditText) findViewById(R.id.Address);
        name = (EditText)findViewById(R.id.namereg);
        signupImage = (CircleImageView) findViewById(R.id.sginup_image);
        donoroption = (Spinner)findViewById(R.id.donor_option);



        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               // String title =
/*
                String message = name + "has join Blood donor Application";
                                Notification notification = new NotificationCompat.Builder(DonorRegistrationActivity.this, App.CHANNEL_1_ID)
                                        .setSmallIcon(R.drawable.ic_notifications_active_black_24dp)
                                        .setContentTitle("Blood donor")
                                        .setContentText(message)
                                        .setPriority(NotificationCompat.PRIORITY_LOW)
                                        .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                                        .build();

                                notificationManager.notify(1,notification);

*/
                if(!TextUtils.isEmpty(phoneNumber.getText().toString()) && !TextUtils.isEmpty(gender.getSelectedItem().toString()) &&
                        !TextUtils.isEmpty(bloodStatuscheck(bloodStatus)) && !TextUtils.isEmpty(email.getText().toString()) &&
                        !TextUtils.isEmpty(city.getText().toString()) && !TextUtils.isEmpty(address.getText().toString()) &&
                        !TextUtils.isEmpty(name.getText().toString()) && !TextUtils.isEmpty(donoroption.getSelectedItem().toString()) && mainImageUri != null){
                    progressBar.setVisibility(View.VISIBLE);

                    StorageReference imageRef = stroageReference.child("Profile image").child(currentUser+ ".jpg");
                    imageRef.putFile(mainImageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            if (task.isSuccessful()){
                                String option = donoroption.getSelectedItem().toString();
                                String status = bloodStatuscheck(bloodStatus);

                                storeFirestore(task,phoneNumber.getText().toString(),gender.getSelectedItem().toString(), option ,
                                        email.getText().toString(),city.getText().toString(),address.getText().toString(),name.getText().toString(),
                                        status, mainImageUri);
                                Toast.makeText(DonorRegistrationActivity.this, "Image added to storage", Toast.LENGTH_SHORT).show();



                            }else {
                                String error = task.getException().getMessage();
                                Toast.makeText(DonorRegistrationActivity.this, "Image Error" + error, Toast.LENGTH_SHORT).show();
                            }
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    });

                }

            }
        });


        signupImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //checking if the build version is greater than or equal to mashmellow then ask for storage permission
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                    if (ContextCompat.checkSelfPermission(DonorRegistrationActivity.this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        //  Toast.makeText(SetupActivity.this, "Permission not grantted", Toast.LENGTH_SHORT).show();
                        ActivityCompat.requestPermissions(DonorRegistrationActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);

                    } else {
                        imagePicker();

                    }
                }else {
                    imagePicker();
                }
            }
        });

    }

    private void storeFirestore(Task<UploadTask.TaskSnapshot> task, String pho, String gen, String option, String ema, String cit, String add, String nam, String status, Uri mainImageUri) {

        Uri downloadUri;
        if (task != null){
            downloadUri = task.getResult().getDownloadUrl();
        }else {
            downloadUri = mainImageUri;
        }

        Map<String,String> user_map = new HashMap<>();
        user_map.put("name", nam);
        user_map.put("gender", gen);
        user_map.put("phone", pho);
        user_map.put("email", ema);
        user_map.put("address", add);
        user_map.put("city", cit);
        user_map.put("option", option);
        user_map.put("status", status);
        user_map.put("image", downloadUri.toString());

        Client client = new Client("E3KJPH4KKR", "5ccce791754efd4bbb4f9d7324895ea6");
        Index index = client.getIndex("Donor");

        List<JSONObject> Donor = new ArrayList<JSONObject>();

        Donor.add(new JSONObject(user_map));

        index.addObjectsAsync(new JSONArray(Donor), null);


        firestore.collection("Donor details").document(currentUser).set(user_map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Intent logIntent = new Intent(DonorRegistrationActivity.this, LoginActivity.class);
                    startActivity(logIntent);
                    finish();
                }else {
                    String error = task.getException().getMessage();
                    Toast.makeText(DonorRegistrationActivity.this, "Error" + error, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private String bloodStatuscheck(RadioButton bs) {

        String result;
        if (bs.isChecked()){
            result = "i am a donor";
        }else {
            result = "not a donor";
        }
        return  result;
    }

    private void imagePicker() {
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(1,1)
                .start(DonorRegistrationActivity.this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (resultCode == RESULT_OK) {

                mainImageUri = result.getUri();
                signupImage.setImageURI(mainImageUri);

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {

                Exception error = result.getError();

            }
        }
    }

    @Override
    public void onBackPressed() {
        Intent mainIntent = new Intent(DonorRegistrationActivity.this, HomeActivity.class);
        startActivity(mainIntent);
        finish();
        super.onBackPressed();
    }
}
