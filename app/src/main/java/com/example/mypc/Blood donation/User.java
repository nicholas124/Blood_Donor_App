package com.example.mypc.inventorymanagement;


import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentContainer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class User extends Fragment {

    private ImageView profileImage;
    private TextView gender;
    private TextView name;
    private TextView email;
    private TextView phonenumber;
    private TextView address;
    private TextView city;
    private TextView bloodType;
    private TextView status;

    FirebaseFirestore firebaseFirestore;
    FirebaseAuth mAuth;
    String currentUser;
    StorageReference storageReference;
    Uri mainImageUri;

    private ProgressDialog progressDialog;



    public User() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user, container, false);

        firebaseFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser().getUid();
        storageReference = FirebaseStorage.getInstance().getReference();

        profileImage = (ImageView) view.findViewById(R.id.profile_image);
        gender = (TextView) view.findViewById(R.id.gender_type);
        name = (TextView) view.findViewById(R.id.name_type);
        email = (TextView) view.findViewById(R.id.email_type);
        phonenumber = (TextView) view.findViewById(R.id.phone_type);
        address = (TextView) view.findViewById(R.id.address_type);
        city = (TextView) view.findViewById(R.id.city_type);
        bloodType = (TextView) view.findViewById(R.id.blood_type);
        status = (TextView) view.findViewById(R.id.status_type);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("User details");
        progressDialog.setMessage("Please wait....");


        if (currentUser != null){
            progressDialog.show();
            firebaseFirestore.collection("Donor details").document(currentUser).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()){
                        //progressDialog.show();
                        String imageS = task.getResult().getString("image");
                        String genderS = task.getResult().getString("gender");
                        String nameS = task.getResult().getString("name");
                        String emailS = task.getResult().getString("email");
                        String phoneNumberS = task.getResult().getString("phone");
                        String addressS = task.getResult().getString("address");
                        String cityS = task.getResult().getString("city");
                        String bloodTypeS = task.getResult().getString("option");
                        String statusS = task.getResult().getString("status");

                        mainImageUri = Uri.parse(imageS);
                        gender.setText(genderS);
                        name.setText(nameS);
                        email.setText(emailS);
                        phonenumber.setText(phoneNumberS);
                        address.setText(addressS);
                        city.setText(cityS);
                        bloodType.setText(bloodTypeS);
                        status.setText(statusS);

                        if (getActivity() == null) {
                            return;
                        }else {
                            Glide.with(getContext()).load(mainImageUri).into(profileImage);
                        }

                        progressDialog.cancel();
                    }else {
                        String error = task.getException().getMessage();
                        Toast.makeText(getContext(), "Error" + error, Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }



        return view;
    }

}
