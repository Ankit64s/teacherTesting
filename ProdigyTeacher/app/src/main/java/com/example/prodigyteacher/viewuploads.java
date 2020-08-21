package com.example.prodigyteacher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class viewuploads extends AppCompatActivity {
    Button viewpdfs,viewimage,viewlink;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewuploads);

        viewpdfs=findViewById(R.id.viewpdfs);

        viewpdfs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),viewpdf.class);
                startActivity(i);
            }
        });

        viewimage=findViewById(R.id.viewimage);
        viewimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),view_images_uploads.class);
                startActivity(i);
            }
        });

        viewlink=findViewById(R.id.viewlink);

        viewlink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),view_links_uploads.class);
                startActivity(i);
            }
        });
    }
}
