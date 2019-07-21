package com.example.mypc.inventorymanagement;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
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

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private BottomNavigationView bottomNavigationView;
    private SearchFragment search;
    private BankFragment bank;
    private User userFrag;
    private Fragment account;
    private Uri mainImageUri;
    FirebaseFirestore firebaseFirestore;

    FirebaseAuth mAuth;
    FirebaseUser currentUser;

    TextView username_sideFrame;
    ImageView image_sideFrame;
    String currentUserString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Save a Life");
        setSupportActionBar(toolbar);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.main_bottomNav);


        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        currentUserString = mAuth.getCurrentUser().getUid();
        currentUser = mAuth.getCurrentUser();

        search = new SearchFragment();
        bank = new BankFragment();
        userFrag = new User();





       DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        replaceFragement(search);

        View headerView = navigationView.getHeaderView(0);
        username_sideFrame = (TextView) headerView.findViewById(R.id.username_txtview);
        image_sideFrame = (ImageView) headerView.findViewById(R.id.imageView);

        String displayName = currentUser.getEmail();
        username_sideFrame.setText(displayName);

        if (currentUser != null){

            firebaseFirestore.collection("Donor details").document(currentUserString).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()){

                        DocumentSnapshot snapshot = task.getResult();

                        String imageS = "";
                        if (snapshot.exists()) {
                            imageS = task.getResult().getString("image");
                        } else {
                            Toast.makeText(HomeActivity.this, "no data found", Toast.LENGTH_SHORT).show();
                        }
                        mainImageUri = Uri.parse(imageS);


                        Glide.with(HomeActivity.this).load(mainImageUri).into(image_sideFrame);
                    }else {
                        String error = task.getException().getMessage();
                        Toast.makeText(HomeActivity.this, "could not load header image" + error, Toast.LENGTH_SHORT).show();
                    }

                }
            });

        }

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch ((item.getItemId())){
                    case R.id.nav_search:
                        replaceFragement(search);
                        Toast.makeText(HomeActivity.this, "clicked", Toast.LENGTH_SHORT).show();
                        return true;

                    case R.id.nav_bank:
                        replaceFragement(bank);
                        return true;

                    case R.id.nav_details:
                        replaceFragement(userFrag);
                        return true;


                }
                return false;
            }
        });
    }

    private void replaceFragement(android.support.v4.app.Fragment fragment) {
        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.layout, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if(id == R.id.map){
            Intent loginIntent = new Intent(HomeActivity.this,Map.class);
            startActivity(loginIntent);
            return true;
        }

        if (id == R.id.action_logout) {
            mAuth.signOut();
            Intent loginIntent = new Intent(HomeActivity.this,LoginActivity.class);
            startActivity(loginIntent);
            return true;
        }

        return false;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_details1) {
            Intent donor = new Intent(HomeActivity.this, EditDeatils.class);
            startActivity(donor);
            finish();

        } else if (id == R.id.nav_help) {

        } else if (id == R.id.nav_howTo) {
            Intent howto= new Intent(HomeActivity.this, AboutActivity.class);
            startActivity(howto);
            finish();

        }else if (id == R.id.nav_map){
            Intent loginIntent = new Intent(HomeActivity.this,Map.class);
            startActivity(loginIntent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
