package com.example.mypc.inventorymanagement;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    private EditText emailid;
    private EditText passwordid;
    private Button signbtn;
    private ProgressDialog progressDialog;
    private ProgressBar progressBar;

    FirebaseDatabase database;
    DatabaseReference table_User;

    FirebaseAuth mAuth;
    FirebaseUser currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        emailid = (EditText) findViewById(R.id.signin_username);
        passwordid = (EditText) findViewById(R.id.signin_password);
        signbtn = (Button) findViewById(R.id.signin_btn);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        signbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = emailid.getText().toString();
                String password = passwordid.getText().toString();

                if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)){
                    progressBar.setVisibility(View.VISIBLE);

                    mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                Intent mainIntent = new Intent(LoginActivity.this, HomeActivity.class);
                                startActivity(mainIntent);
                                finish();
                            }else {
                                String error = task.getException().getMessage();
                                Toast.makeText(LoginActivity.this, "Error" + error , Toast.LENGTH_SHORT).show();
                            }
                            progressBar.setVisibility(View.INVISIBLE);

                        }
                    });
                }

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(currentUser != null){
            sendToHome();

        }


    }
    public void sendToLogin() {
        Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
        startActivity(intent);
        finish();
    }
    public void sendToHome() {
        Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
        super.onBackPressed();

        }

}
