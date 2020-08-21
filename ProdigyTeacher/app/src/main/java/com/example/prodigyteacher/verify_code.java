package com.example.prodigyteacher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class verify_code extends AppCompatActivity {
    EditText verifycode;
    //String passedid,passedmob;
    Button login;
    private FirebaseAuth mAuth;
    private String mVerificationId;
    ProgressBar progressBar;
    String passed,passedid,passedname;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_code);

        verifycode = findViewById(R.id.verifycode);
        login = findViewById(R.id.login);
         passed = getIntent().getStringExtra("mobile");
         passedid=getIntent().getStringExtra("id");
         passedname=getIntent().getStringExtra("name");
        sharedPreferences=this.getSharedPreferences("application", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor=sharedPreferences.edit();

        editor.putString("Name", passedname);
        editor.putString("Id", passedid);
        editor.putString("Mobile", passed);
        editor.apply();

        mAuth = FirebaseAuth.getInstance();
        progressBar=findViewById(R.id.progressbar);

        sendVerificationcode(passed);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = verifycode.getText().toString();
                if (code.isEmpty() || code.length() < 6) {
                    verifycode.setError("Enter Valid Code");
                    verifycode.requestFocus();
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                verifyVerificationcode(code);
            }
        });

    }

    private void verifyVerificationcode(String code) {
        try{
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);

            //signing the user
            signInWithPhoneAuthCredential(credential);}
        catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(verify_code.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(), "Logged iN", Toast.LENGTH_SHORT).show();
                            Intent i=new Intent(getApplicationContext(),profile_teacher.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            i.putExtra("mobile",passed);
                            i.putExtra("id",passedid);
                            i.putExtra("name",passedname);
                            startActivity(i);
                            finish();
                        }
                        else{
                            String message = "Somthing is wrong, we will fix it soon...";

                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                //message = "Invalid code entered...";
                                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                            }

                            /*Snackbar snackbar = Snackbar.make(findViewById(R.id.parent), message, Snackbar.LENGTH_LONG);
                            snackbar.setAction("Dismiss", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                }
                            });
                            snackbar.show();*/
                        }
                    }
                });
    }

    private void sendVerificationcode(String passed) {
        String phonenumber="+91"+passed;
        PhoneAuthProvider.getInstance().verifyPhoneNumber(phonenumber,60, TimeUnit.SECONDS,
                this,
                mCallbacks);
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            Toast.makeText(getApplicationContext(), "Code Sent", Toast.LENGTH_SHORT).show();
            super.onCodeSent(s, forceResendingToken);
            mVerificationId = s;
        }


        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();

            if (code != null) {
                progressBar.setVisibility(View.VISIBLE);
                verifycode.setText(code);
                verifyVerificationcode(code);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }


    };

}
