package com.example.prodigyteacher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class upload_youtube_link extends AppCompatActivity {
    EditText videotitle,targetclass,linkuploader,links;
    Button linkuploads;
    TextView textViewStatus;
    ProgressBar progressBar;
    StorageReference mStorageReference;
    DatabaseReference mDatabaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_youtube_link);

        String var=getstringdata();
        //getting firebase objects
        mStorageReference = FirebaseStorage.getInstance().getReference();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference(link_constants.DATABASE_PATH_UPLOADS);


        videotitle=findViewById(R.id.videotitle);
        targetclass=findViewById(R.id.taregetclass);
        linkuploader=findViewById(R.id.linkuploader);
        linkuploads=findViewById(R.id.linkuploads);
        links=findViewById(R.id.links);
        textViewStatus=findViewById(R.id.textViewStatus);
        progressBar=findViewById(R.id.progressbar);
        linkuploader.setText(var);

        linkuploads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(videotitle.getText().toString())){
                    videotitle.setError("This Field is Mandatory");
                    return;
                }
                if(TextUtils.isEmpty(targetclass.getText().toString())){
                    targetclass.setError("This Field is Mandatory");
                    return;
                }
                if(TextUtils.isEmpty(linkuploader.getText().toString())){
                    linkuploader.setError("This Field is Mandatory");
                    return;
                }
                if(TextUtils.isEmpty(links.getText().toString())){
                    links.setError("This Field is Mandatory");
                    return;
                }
                //getIMAGE();
                String link=links.getText().toString();
                uploadlink(link);

            }
        });
    }

    private String getstringdata() {
        String var1;
        SharedPreferences sharedPreferences=getSharedPreferences("application", Context.MODE_PRIVATE);
        var1=sharedPreferences.getString("Name","");
        //Toast.makeText(this, var1, Toast.LENGTH_SHORT).show();
        return var1;
    }

    private void uploadlink(String link) {
        progressBar.setVisibility(View.VISIBLE);
        final link_upload upload=new link_upload(videotitle.getText().toString(),targetclass.getText().toString()
                ,linkuploader.getText().toString(),links.getText().toString());

        mDatabaseReference.child(mDatabaseReference.push().getKey()).setValue(upload);
        Toast.makeText(this, "Link Uploaded", Toast.LENGTH_SHORT).show();
        progressBar.setVisibility(View.INVISIBLE);
    }
}
