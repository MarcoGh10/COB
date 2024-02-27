package com.example.cob;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class LogIn extends AppCompatActivity {

    public static final String PREFS_NAME = "MyPrefsFile";

    private Button button;
    TextView forgotPassword;
    EditText mEmail,mPassword;
    Button mNext,mCreateBtn;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(ContextCompat.getColor(LogIn.this,R.color.black));
        setContentView(R.layout.activity_log_in2);

        mEmail= findViewById(R.id.inputEmail);
        mPassword= findViewById(R.id.inputPssword);
        forgotPassword=findViewById(R.id.forgot_password);

        mNext= findViewById(R.id.next);
        mCreateBtn=findViewById(R.id.register1);
        fAuth = FirebaseAuth.getInstance();

        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();

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
                fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>(){
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task){
                        if (task.isSuccessful()) {
                            SharedPreferences sharedPreferences=getSharedPreferences(LogIn.PREFS_NAME,0);
                            SharedPreferences.Editor editor=sharedPreferences.edit();

                            editor.putBoolean("hasLoggedIn",true);
                            editor.commit();

                            startActivity(new Intent(getApplicationContext(),Feed.class));
                            finish();
                        }else{
                            Toast.makeText(LogIn.this,"Error!" + task.getException().getMessage(),Toast.LENGTH_LONG).show();
                        }



                    }

                });


            }
        });
        mCreateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Register.class));

            }
        });
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText resetMail =new EditText(view.getContext());
                AlertDialog.Builder passwordReset= new AlertDialog.Builder(view.getContext());
                passwordReset.setTitle("Reset Password");
                passwordReset.setMessage("Enter your Email");
                passwordReset.setView(resetMail);

                passwordReset.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //extract the email and send reset link

                        String mail=resetMail.getText().toString();
                        fAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                              Toast.makeText(LogIn.this,"Reset link send to you",Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(LogIn.this,"Error!"+e.getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                });
                passwordReset.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                   //close the dialog
                    }
                });
                passwordReset.create().show();

            }
        });

    }
}

