package com.example.mypc.inventorymanagement;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.example.mypc.inventorymanagement.Model.Adapter.DonorRecyclerAdpter;
import com.example.mypc.inventorymanagement.Model.Donors;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {

             Spinner searchBloodSelect;
            private RecyclerView detailsRecycler;
            List<Donors> donor_post;

            FirebaseFirestore firebaseFirestore;
            FirebaseAuth mAuth;
            DonorRecyclerAdpter donorRecyclerAdpter;
            FirebaseUser currentUser;
            String spinResult;

            Spinner optionSpin;


    public SearchFragment() {
        // Required empty public constructor
    }

    public String getSearchResult( Spinner spin){
        String result;
        result = spin.getSelectedItem().toString();

        return result;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         final View view = inflater.inflate(R.layout.fragment_search, container, false);

        donor_post = new ArrayList<>();
        detailsRecycler = view.findViewById(R.id.recycler_details);
        //searchBloodSelect = (Spinner) view.findViewById(R.id.s_select_bloodGroup);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        firebaseFirestore = FirebaseFirestore.getInstance();

        optionSpin =(Spinner) view.findViewById(R.id.options_spin);

        donorRecyclerAdpter = new DonorRecyclerAdpter(donor_post);
        detailsRecycler.setLayoutManager(new LinearLayoutManager(container.getContext()));
        detailsRecycler.setAdapter(donorRecyclerAdpter);


        optionSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                spinResult = optionSpin.getSelectedItem().toString();
//                Toast.makeText(view.getContext(), ""+spinResult+"  "+position, Toast.LENGTH_SHORT).show();

                if(spinResult.equals("Select")){
                    donor_post.clear();
                    load();
                }else if(spinResult.equals("O+")){
                    donor_post.clear();
                    load1();
                }else if (spinResult.equals("O-")){
                    donor_post.clear();
                    loadOneg();
                }else if (spinResult.equals("A+")){
                    donor_post.clear();
                    loadAplus();
                }else if (spinResult.equals("A-")){
                    donor_post.clear();
                    loadAneg();
                }else if (spinResult.equals("B-")){
                    donor_post.clear();
                    loadBneg();
                }else if (spinResult.equals("B+")){
                    donor_post.clear();
                    loadBplus();
                }else if (spinResult.equals("AB-")){
                    donor_post.clear();
                    loadABneg();
                }else if (spinResult.equals("AB+")){
                    donor_post.clear();
                    loadABplus();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

/*
        searchBloodSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view1, int i, long l) {
                Toast.makeText(adapterView.getContext(),
                        "OnItemSelectedListener : " + adapterView.getItemAtPosition(i).toString(),
                        Toast.LENGTH_SHORT).show();

                load(adapterView, i, container);


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

                if (currentUser != null){

                    firebaseFirestore.collection("Donor details").orderBy("name").addSnapshotListener(getActivity(),new EventListener<QuerySnapshot>() {
                        @Override
                        public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                            for (DocumentChange doc : documentSnapshots.getDocumentChanges()){
                                if (doc.getType() == DocumentChange.Type.ADDED){

                                    Donors donors = doc.getDocument().toObject(Donors.class);
                                    donor_post.add(donors);
                                    donorRecyclerAdpter.notifyDataSetChanged();
                                }
                            }
                        }
                    });
                }else {
                    Toast.makeText(adapterView.getContext(), "log out" , Toast.LENGTH_SHORT).show();
                }

            }
        });



*/
      //  load();



        return view;
    }

    public void load(){

        if (currentUser != null){

            CollectionReference collectionReference = firebaseFirestore.collection("Donor details");
            Query query = collectionReference.orderBy("option");
            //Query query = collectionReference.whereEqualTo("option", "O+");

            query.addSnapshotListener(getActivity(),new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                    for (DocumentChange doc : documentSnapshots.getDocumentChanges()){
                        if (doc.getType() == DocumentChange.Type.ADDED){

                            Donors donors = doc.getDocument().toObject(Donors.class);
                            donor_post.add(donors);
                            donorRecyclerAdpter.notifyDataSetChanged();
                        }
                    }
                }
            });
        }
    }

    public void load1(){

        if (currentUser != null){

            CollectionReference collectionReference = firebaseFirestore.collection("Donor details");
            //Query query = collectionReference.orderBy("option", Query.Direction.ASCENDING);
            Query query = collectionReference.whereEqualTo("option", "O+");

            query.addSnapshotListener(getActivity(),new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                    for (DocumentChange doc : documentSnapshots.getDocumentChanges()){
                        if (doc.getType() == DocumentChange.Type.ADDED){

                            Donors donors = doc.getDocument().toObject(Donors.class);
                            donor_post.add(donors);
                            donorRecyclerAdpter.notifyDataSetChanged();
                        }
                    }
                }
            });
        }
    }

    public void loadAplus(){

        if (currentUser != null){

            CollectionReference collectionReference = firebaseFirestore.collection("Donor details");
            //Query query = collectionReference.orderBy("option", Query.Direction.ASCENDING);
            Query query = collectionReference.whereEqualTo("option", "A+");

            query.addSnapshotListener(getActivity(),new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                    for (DocumentChange doc : documentSnapshots.getDocumentChanges()){
                        if (doc.getType() == DocumentChange.Type.ADDED){

                            Donors donors = doc.getDocument().toObject(Donors.class);
                            donor_post.add(donors);
                            donorRecyclerAdpter.notifyDataSetChanged();
                        }
                    }
                }
            });
        }
    }

    public void loadAneg(){

        if (currentUser != null){

            CollectionReference collectionReference = firebaseFirestore.collection("Donor details");
            //Query query = collectionReference.orderBy("option", Query.Direction.ASCENDING);
            Query query = collectionReference.whereEqualTo("option", "A-");

            query.addSnapshotListener(getActivity(),new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                    for (DocumentChange doc : documentSnapshots.getDocumentChanges()){
                        if (doc.getType() == DocumentChange.Type.ADDED){

                            Donors donors = doc.getDocument().toObject(Donors.class);
                            donor_post.add(donors);
                            donorRecyclerAdpter.notifyDataSetChanged();
                        }
                    }
                }
            });
        }
    }

    public void loadBplus(){

        if (currentUser != null){

            CollectionReference collectionReference = firebaseFirestore.collection("Donor details");
            //Query query = collectionReference.orderBy("option", Query.Direction.ASCENDING);
            Query query = collectionReference.whereEqualTo("option", "B+");

            query.addSnapshotListener(getActivity(),new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                    for (DocumentChange doc : documentSnapshots.getDocumentChanges()){
                        if (doc.getType() == DocumentChange.Type.ADDED){

                            Donors donors = doc.getDocument().toObject(Donors.class);
                            donor_post.add(donors);
                            donorRecyclerAdpter.notifyDataSetChanged();
                        }
                    }
                }
            });
        }
    }

    public void loadBneg(){

        if (currentUser != null){

            CollectionReference collectionReference = firebaseFirestore.collection("Donor details");
            //Query query = collectionReference.orderBy("option", Query.Direction.ASCENDING);
            Query query = collectionReference.whereEqualTo("option", "B-");

            query.addSnapshotListener(getActivity(),new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                    for (DocumentChange doc : documentSnapshots.getDocumentChanges()){
                        if (doc.getType() == DocumentChange.Type.ADDED){

                            Donors donors = doc.getDocument().toObject(Donors.class);
                            donor_post.add(donors);
                            donorRecyclerAdpter.notifyDataSetChanged();
                        }
                    }
                }
            });
        }
    }

    public void loadABneg(){

        if (currentUser != null){

            CollectionReference collectionReference = firebaseFirestore.collection("Donor details");
            //Query query = collectionReference.orderBy("option", Query.Direction.ASCENDING);
            Query query = collectionReference.whereEqualTo("option", "AB-");

            query.addSnapshotListener(getActivity(),new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                    for (DocumentChange doc : documentSnapshots.getDocumentChanges()){
                        if (doc.getType() == DocumentChange.Type.ADDED){

                            Donors donors = doc.getDocument().toObject(Donors.class);
                            donor_post.add(donors);
                            donorRecyclerAdpter.notifyDataSetChanged();
                        }
                    }
                }
            });
        }
    }

    public void loadABplus(){

        if (currentUser != null){

            CollectionReference collectionReference = firebaseFirestore.collection("Donor details");
            //Query query = collectionReference.orderBy("option", Query.Direction.ASCENDING);
            Query query = collectionReference.whereEqualTo("option", "AB+");

            query.addSnapshotListener(getActivity(),new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                    for (DocumentChange doc : documentSnapshots.getDocumentChanges()){
                        if (doc.getType() == DocumentChange.Type.ADDED){

                            Donors donors = doc.getDocument().toObject(Donors.class);
                            donor_post.add(donors);
                            donorRecyclerAdpter.notifyDataSetChanged();
                        }
                    }
                }
            });
        }
    }

    public void loadOneg(){

        if (currentUser != null){

            CollectionReference collectionReference = firebaseFirestore.collection("Donor details");
            //Query query = collectionReference.orderBy("option", Query.Direction.ASCENDING);
            Query query = collectionReference.whereEqualTo("option", "O+");

            query.addSnapshotListener(getActivity(),new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                    for (DocumentChange doc : documentSnapshots.getDocumentChanges()){
                        if (doc.getType() == DocumentChange.Type.ADDED){

                            Donors donors = doc.getDocument().toObject(Donors.class);
                            donor_post.add(donors);
                            donorRecyclerAdpter.notifyDataSetChanged();
                        }
                    }
                }
            });
        }
    }

}
