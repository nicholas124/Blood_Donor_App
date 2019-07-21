package com.example.mypc.inventorymanagement;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private EditText conPassword;
    private Button signupbtn;
    private Button alreadyhaveacc;


    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();

        email = (EditText) findViewById(R.id.signup_email);
        password = (EditText) findViewById(R.id.signup_password);
        conPassword = (EditText) findViewById(R.id.confirm_password);
        signupbtn = (Button) findViewById(R.id.Sign_up_btn) ;
        alreadyhaveacc = (Button) findViewById(R.id.already_btn) ;



        alreadyhaveacc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginint = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(loginint);
            }
        });

        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailString = email.getText().toString();
                String passwordString = password.getText().toString();
                String confirmpassword = conPassword.getText().toString();


                if(!TextUtils.isEmpty(emailString) && !TextUtils.isEmpty(passwordString) && !TextUtils.isEmpty(confirmpassword)){
                    if (passwordString.equals(confirmpassword)){
                        mAuth.createUserWithEmailAndPassword(emailString,passwordString).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){

                                    Toast.makeText(SignUpActivity.this, "Account created", Toast.LENGTH_SHORT).show();
                                    Intent conIntent = new Intent(SignUpActivity.this, DonorRegistrationActivity.class);
                                    startActivity(conIntent);

                                }else{
                                    String error = task.getException().getMessage();
                                    Toast.makeText(SignUpActivity.this, "Error" + error, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }
            }
        });

    }


    private void sendToMain() {
        Intent mainIntent = new Intent(SignUpActivity.this, HomeActivity.class);
        startActivity(mainIntent);
        finish();
    }


}
