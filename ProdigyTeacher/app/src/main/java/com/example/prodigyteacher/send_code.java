package com.example.prodigyteacher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class send_code extends AppCompatActivity {
    EditText mobilepass;
    String passedmob,passedid,passedname;
    Button sendcode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_code);
        mobilepass=findViewById(R.id.mobilepass);
        sendcode=findViewById(R.id.sendcode);
        Intent i=getIntent();
        passedmob=i.getStringExtra("mob");
        passedid=i.getStringExtra("id");
        passedname=i.getStringExtra("name");
        mobilepass.setText(passedmob);

        sendcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),verify_code.class);
                i.putExtra("mobile",passedmob);
                i.putExtra("id",passedid);
                i.putExtra("name",passedname);
                startActivity(i);
            }
        });
    }
}
