package com.example.prodigyteacher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class student_list_layout extends AppCompatActivity {
EditText enterclass,entersection;
Button detailsfetch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list_layout);

        enterclass=findViewById(R.id.enterclass);
        entersection=findViewById(R.id.entersection);

        detailsfetch=findViewById(R.id.detailfetch);

        detailsfetch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cl=enterclass.getText().toString();
                String se=entersection.getText().toString();
                Intent i=new Intent(student_list_layout.this,Student_List.class);
                i.putExtra("cls",cl);
                i.putExtra("section",se);
                startActivity(i);
            }
        });
    }
}
