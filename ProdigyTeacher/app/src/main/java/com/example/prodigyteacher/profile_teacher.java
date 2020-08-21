package com.example.prodigyteacher;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class profile_teacher extends AppCompatActivity {
    Button teacherlogout,viewprofile,studentlist;
    Button homework,attendance,notices, uploadfiles;
    Button viewuploads;
    private FirebaseAuth mAuth;
    String passed,passedid,passedname;
    TextView welcome;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_teacher);



        passed = getIntent().getStringExtra("mobile");
        passedid=getIntent().getStringExtra("id");
        passedname=getIntent().getStringExtra("name");


        String var=getstringdata();
                //sharedPreferences.getString("username","");

        welcome=findViewById(R.id.text1);
        viewprofile=findViewById(R.id.viewprofile);
        studentlist=findViewById(R.id.studentlist);
        homework=findViewById(R.id.homework);
        attendance=findViewById(R.id.attendance);
        notices=findViewById(R.id.notices);
        uploadfiles=findViewById(R.id.uploadfiles);
        viewuploads=findViewById(R.id.viewuploads);

        welcome.setText(welcome.getText().toString()+" "+var);
        teacherlogout=findViewById(R.id.teacherlogout);
        mAuth=FirebaseAuth.getInstance();

        viewprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(profile_teacher.this,Personal_Profile.class);
                startActivity(i);
            }
        });


        studentlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(profile_teacher.this,student_list_layout.class);
                startActivity(i);
            }
        });

        homework.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(profile_teacher.this,homework.class);
                startActivity(i);
            }
        });

        attendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(profile_teacher.this,attendance_layout.class);
                startActivity(i);
            }
        });

        notices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(profile_teacher.this,NoticesArea.class);
                startActivity(i);
            }
        });

        uploadfiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(profile_teacher.this,uploads_layout.class);
                startActivity(i);
            }
        });

        viewuploads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(profile_teacher.this,viewuploads.class);
                startActivity(i);
            }
        });

        teacherlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                finish();
                Intent i=new Intent(getApplicationContext(),login.class);
                startActivity(i);
            }
        });
    }

    private String getstringdata() {
        String var1;
        SharedPreferences sharedPreferences=getSharedPreferences("application",Context.MODE_PRIVATE);
        var1=sharedPreferences.getString("Name","");
        //Toast.makeText(this, var1, Toast.LENGTH_SHORT).show();
        return var1;
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(profile_teacher.this).setIcon(R.drawable.ic_launcher_background)
                .setMessage("Do You Really Want To Close The App?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).setNegativeButton("No",null).show();
    }
}
