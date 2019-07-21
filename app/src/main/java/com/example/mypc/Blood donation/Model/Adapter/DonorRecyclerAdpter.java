package com.example.mypc.inventorymanagement.Model.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mypc.inventorymanagement.Model.Donors;
import com.example.mypc.inventorymanagement.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class DonorRecyclerAdpter extends RecyclerView.Adapter<DonorRecyclerAdpter.ViewHolder> {

    List<Donors> donor_post;
    public String donorImage1;
    public String name1;
    public String gender1;
    public String email1;
    public String phoneNumber1;
    public String address1;
    public String city1;
    public String donorOption1;
    public String donorStatus1;

    public String user;
    public FirebaseAuth mAuth;
    public Context context;
    public FirebaseFirestore firebaseFirestore;

    public DonorRecyclerAdpter(List<Donors> donor_post) {
        this.donor_post = donor_post;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        context = parent.getContext();
        firebaseFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.donor_search_result, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        name1 = donor_post.get(position).getName();
        holder.name.setText(name1);

        holder.phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+holder.phone.getText()));
                context.startActivity(intent);
            }
        });


        gender1 = donor_post.get(position).getGender();
        holder.gender.setText(gender1);

        email1 = donor_post.get(position).getEmail();
        holder.email.setText(email1);

        phoneNumber1 = donor_post.get(position).getPhone();
        holder.phone.setText(phoneNumber1);

        address1 = donor_post.get(position).getAddress();
        holder.address.setText(address1);

        city1 = donor_post.get(position).getCity();
        holder.city.setText(city1);

        donorOption1 = donor_post.get(position).getOption();
        holder.bloodtype.setText(donorOption1);

        donorStatus1 = donor_post.get(position).getStatus();
        holder.donorstatus.setText(donorStatus1);

        donorImage1 = donor_post.get(position).getImage();
        holder.setUserImage(donorImage1);

    }

    @Override
    public int getItemCount() {
        return donor_post.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private  View mView;
        private ImageView donorImage;
        private TextView name;
        private TextView gender;
        private TextView email;
        private TextView phone;
        private TextView address;
        private TextView city;
        private TextView bloodtype;
        private TextView donorstatus;

        public ViewHolder(View itemView) {
            super(itemView);

            mView = itemView;

            name = (TextView)itemView.findViewById(R.id.ds_name);
            gender = (TextView)itemView.findViewById(R.id.ds_gender);
            email = (TextView)itemView.findViewById(R.id.ds_email);
            phone = (TextView)itemView.findViewById(R.id.ds_phoneNumber);
            address = (TextView)itemView.findViewById(R.id.ds_address);
            city = (TextView)itemView.findViewById(R.id.ds_city);
            bloodtype = (TextView)itemView.findViewById(R.id.ds_donoroption);
            donorstatus = (TextView)itemView.findViewById(R.id.ds_donorStatus);


            }

        public void setUserImage(String imageUri){
            donorImage = (ImageView) mView.findViewById(R.id.ds_image);
            Glide.with(context).load(imageUri).into(donorImage);

        }
    }
}
