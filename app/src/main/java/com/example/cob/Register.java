package com.example.cob;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class Register extends AppCompatActivity {
    String TAG="TAG";
    EditText mFullName;
    EditText mUsername;
    EditText mEmail;
    EditText mPassword;
    EditText mConfirmPassword;
    String userId;
    Button mNext,mLogBtn;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(ContextCompat.getColor(Register.this, R.color.black));
        setContentView(R.layout.activity_register);


        mFullName = findViewById(R.id.inputFullName);
        mUsername = findViewById(R.id.inputUsername);
        mEmail = findViewById(R.id.inputEmail);
        mPassword = findViewById(R.id.inputPssword);
        mConfirmPassword = findViewById(R.id.inputConfirmPassword);

        mNext = findViewById(R.id.next);
        mLogBtn=findViewById(R.id.logIn);

        fStore=FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();

        if (fAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }


        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                String confirmPassword = mConfirmPassword.getText().toString().trim();
                String fullName = mFullName.getText().toString();
                String username    = mUsername.getText().toString();

                if(TextUtils.isEmpty(fullName)){
                    mFullName.setError("Name is Required.");
                    return;
                }
                if(TextUtils.isEmpty(username)){
                    mUsername.setError("Username is Required.");
                    return;
                }
                if(TextUtils.isEmpty(email)){
                    mEmail.setError("Email is Required.");
                    return;
                }
                else if (!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                }
                else {
                    mEmail.setError("Invalid email");
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    mPassword.setError("Password is Required.");
                    return;
                }
                if(TextUtils.isEmpty(confirmPassword)){
                    mConfirmPassword.setError("Confirm your password.");
                    return;
                }
                if(!confirmPassword.equals(password)){
                    mConfirmPassword.setError("Try again, it's not the same.");
                    return;
                }

                if(password.length() < 6){
                    mPassword.setError("Password Must be >= 6 Characters");
                    return;
                }
                // register the user in firebase

                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Register.this, "User Created", Toast.LENGTH_LONG).show();

                            userId=fAuth.getCurrentUser().getUid();
                            DocumentReference documentReference= fStore.collection("users").document(userId);

                            Map<String,Object> user = new HashMap<>();
                            user.put("fullName",mFullName);
                            user.put("username",mUsername);
                            user.put("email",mEmail);
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Log.d(TAG,"onSuccess:user Profile is created for "+ userId );
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                Log.d(TAG,"onFailure: " + e.toString());
                                }
                            });


                            startActivity(new Intent(getApplicationContext(),CustomAccount.class));
                        }
                        else{
                            Toast.makeText(Register.this,"Error!" + task.getException().getMessage(),Toast.LENGTH_LONG).show();
                        }

                    }
                });

            }
        });
        mLogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v){
                startActivity(new Intent(getApplicationContext(), LogIn.class));

            }
        });
    }
}