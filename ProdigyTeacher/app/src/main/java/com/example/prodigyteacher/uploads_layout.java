package com.example.prodigyteacher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class uploads_layout extends AppCompatActivity {
    Button uploadpdf,uploadimages,uploadlink;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploads_layout);

        uploadpdf=findViewById(R.id.uploadpdfs);

        uploadimages=findViewById(R.id.uploadimages);

        uploadlink=findViewById(R.id.uploadlink);

        uploadpdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(), uploadpdf.class);
                startActivity(i);
            }
        });

        uploadimages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(), upload_image.class);
                startActivity(i);
            }
        });

        uploadlink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(), upload_youtube_link.class);
                startActivity(i);
            }
        });
    }
}
