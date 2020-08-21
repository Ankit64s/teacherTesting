package com.example.prodigyteacher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class view_links_uploads extends AppCompatActivity {
    DatabaseReference mDatabaseReference;

    //list to store uploads data
    List<link_upload> uploadList;

    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_links_uploads);
        //getting the recyclerview from xml
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        uploadList = new ArrayList<>();

        //getting the database reference
        mDatabaseReference = FirebaseDatabase.getInstance().getReference(link_constants.DATABASE_PATH_UPLOADS);

        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    link_upload upload = postSnapshot.getValue(link_upload.class);
                    uploadList.add(upload);
                }

                linkAdapter adapter=new linkAdapter(view_links_uploads.this,uploadList);

                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(view_links_uploads.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
