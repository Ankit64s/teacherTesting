package com.example.prodigyteacher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth=FirebaseAuth.getInstance();

        if(Build.VERSION.SDK_INT>16){
            getWindow().setFlags(1024,1024);
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(mAuth.getCurrentUser()==null){
                    Intent i=new Intent(getApplicationContext(),login.class);
                    startActivity(i);
                    finish();
                }
                else {
                    Intent i=new Intent(getApplicationContext(),profile_teacher.class);
                    startActivity(i);
                    finish();
                }
                //Intent i=new Intent(MainActivity.this,home.class);
                //startActivity(i);
                //finish();
            }
        },3000);

    }
}
