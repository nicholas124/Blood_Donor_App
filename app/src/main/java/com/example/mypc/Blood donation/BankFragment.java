package com.example.mypc.inventorymanagement;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


/**
 * A simple {@link Fragment} subclass.
 */
public class BankFragment extends Fragment {

    private TextView bank1txt;
    private TextView bank2txt;


    private ProgressDialog progressDialog;

    FirebaseFirestore firebaseFirestore;

    public BankFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_bank, container, false);

        firebaseFirestore = FirebaseFirestore.getInstance();

        bank1txt = (TextView) view.findViewById(R.id.bank1);
        bank2txt = (TextView) view.findViewById(R.id.bank2);


        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Blood bank details");
        progressDialog.setMessage("Please wait....");
        progressDialog.show();

        DocumentReference docRef = firebaseFirestore.collection("Blood Bank").document("yBxHajsmggPFRJhyApm1");
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()){
                    String apositive = documentSnapshot.getString("uth");
                    bank1txt.setText(apositive);
                    String anegative = documentSnapshot.getString("cfb");
                    bank2txt.setText(anegative);
                   // String bpositive = documentSnapshot.getString("B+");
                   // bpos.setText(bpositive);
                   // String bnegative = documentSnapshot.getString("B-");
                  //  bneg.setText(bnegative);
                  //  String abpositive = documentSnapshot.getString("AB+");
                  //  abpos.setText(abpositive);
                  //  String abnegative = documentSnapshot.getString("AB-");
                //    abneg.setText(abnegative);
                  //  String opositive = documentSnapshot.getString("O+");
                  //  opos.setText(opositive);
                  //  String onegative = documentSnapshot.getString("O-");
                   // oneg.setText(onegative);

                    progressDialog.cancel();

                }else {
                    Toast.makeText(view.getContext(), "Document does'nt exist", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return  view;
    }

}
