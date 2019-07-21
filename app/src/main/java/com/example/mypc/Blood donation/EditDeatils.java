package com.example.mypc.inventorymanagement;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.HashMap;
import java.util.Map;

public class EditDeatils extends AppCompatActivity {

    private Button registerbtn;
    //private Uri mainImageUri;
    private EditText email;
    private EditText phoneNumber;
    private EditText address;
    private EditText city;
    private EditText name;
    private ImageView signupImage;
    private Spinner gender;
    private Spinner donoroption;
    private RadioButton bloodStatus;
    private Uri mainImageUri;
    //private String currentUser;

    FirebaseFirestore firebaseFirestore;
    String currentUser;
    FirebaseAuth mAuth;
    StorageReference stroageReference;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_deatils);

        registerbtn = (Button) findViewById(R.id.reg_button_ed);
        phoneNumber = (EditText) findViewById(R.id.signup_phone_number_ed);
        gender = (Spinner) findViewById(R.id.gender_ed);
        bloodStatus = (RadioButton) findViewById(R.id.donor_or_not_ed);
        email = (EditText) findViewById(R.id.signup_email_ed);
        city = (EditText) findViewById(R.id.signup_city_ed);
        address = (EditText) findViewById(R.id.Address_ed);
        name = (EditText)findViewById(R.id.name_details_ed);
        signupImage = (ImageView) findViewById(R.id.sginup_image_ed);
        donoroption = (Spinner)findViewById(R.id.donor_option_ed);

        firebaseFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser().getUid();
        stroageReference = FirebaseStorage.getInstance().getReference();

        progressDialog = new ProgressDialog(EditDeatils.this);
        progressDialog.setTitle("User details");
        progressDialog.setMessage("Please wait....");


        signupImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){

                    if (ContextCompat.checkSelfPermission(EditDeatils.this,Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                        ActivityCompat.requestPermissions(EditDeatils.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},0);
                    }else {
                        imagePicker();
                    }

                }else {
                    imagePicker();
                }
            }
        });


        if (currentUser != null){

            progressDialog.show();
            firebaseFirestore.collection("Donor details").document(currentUser).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()){

                        Toast.makeText(EditDeatils.this, "starting", Toast.LENGTH_SHORT).show();

                        String imageS = task.getResult().getString("image");
                        String genderS = task.getResult().getString("gender");
                        String nameS = task.getResult().getString("name");
                        String emailS = task.getResult().getString("email");
                        String phoneNumberS = task.getResult().getString("phone");
                        String addressS = task.getResult().getString("address");
                        String cityS = task.getResult().getString("city");
                        String bloodTypeS = task.getResult().getString("option");
                        String statusS = task.getResult().getString("status");



                        if (genderS.equals("Male")){
                            gender.setSelection(1);
                        }else if (genderS.equals("Female")) {
                            gender.setSelection(2);
                        }

                       mainImageUri = Uri.parse(imageS);

                        name.setText(nameS);
                        email.setText(emailS);
                        phoneNumber.setText(phoneNumberS);
                        address.setText(addressS);
                        city.setText(cityS);
                        //bloodType.setText(bloodTypeS);
                        //status.setText(statusS);

                        switch (bloodTypeS){

                            case "A+":
                                donoroption.setSelection(1);

                            case "AB-":
                                donoroption.setSelection(2);

                            case "AB+":
                                donoroption.setSelection(3);

                            case "B+":
                                donoroption.setSelection(4);

                            case "O-":
                                donoroption.setSelection(5);

                            case "O+":
                                donoroption.setSelection(6);



                        }

                        if (statusS.equals("not a donor")){
                            bloodStatus.setChecked(false);
                        }else if (statusS.equals("i am a donor")){
                            bloodStatus.setChecked(true);
                        }

                        Glide.with(EditDeatils.this).load(mainImageUri).into(signupImage);

                        progressDialog.cancel();
                    }else {
                        String error = task.getException().getMessage();
                        Toast.makeText(EditDeatils.this, "Error" + error, Toast.LENGTH_SHORT).show();
                    }

                }
            });

            registerbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    if(!TextUtils.isEmpty(phoneNumber.getText().toString()) && !TextUtils.isEmpty(gender.getSelectedItem().toString()) &&
                            !TextUtils.isEmpty(bloodStatuscheck(bloodStatus)) && !TextUtils.isEmpty(email.getText().toString()) &&
                            !TextUtils.isEmpty(city.getText().toString()) && !TextUtils.isEmpty(address.getText().toString()) &&
                            !TextUtils.isEmpty(name.getText().toString()) && !TextUtils.isEmpty(donoroption.getSelectedItem().toString()) && mainImageUri != null){
                        //progressBar.setVisibility(View.VISIBLE);

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
                                    Toast.makeText(EditDeatils.this, "Image added to storage", Toast.LENGTH_SHORT).show();



                                }else {
                                    String error = task.getException().getMessage();
                                    Toast.makeText(EditDeatils.this, "Image Error" + error, Toast.LENGTH_SHORT).show();
                                }
                                //progressBar.setVisibility(View.INVISIBLE);
                            }
                        });


                    }

                }
            });



        }
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


        firebaseFirestore.collection("Donor details").document(currentUser).set(user_map).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Intent logIntent = new Intent(EditDeatils.this, LoginActivity.class);
                    startActivity(logIntent);
                    finish();
                }else {
                    String error = task.getException().getMessage();
                    Toast.makeText(EditDeatils.this, "Error" + error, Toast.LENGTH_SHORT).show();
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
                .start(EditDeatils.this);
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
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
}
