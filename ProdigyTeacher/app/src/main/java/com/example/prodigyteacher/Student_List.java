package com.example.prodigyteacher;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Student_List extends AppCompatActivity {
String passedclass,passedsection;
    private static final String url="https://prodigyanand.000webhostapp.com/studentapi/api.php?r=";
    List<student_list_adapter> studentList;

    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student__list);
        passedclass=getIntent().getStringExtra("cls");
        passedsection=getIntent().getStringExtra("section");
        //getting the recyclerview from xml
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        studentList =new ArrayList<>();

        loadstudents();
    }

    private void loadstudents() {
       String url1=url+passedclass+"&s="+passedsection;
        //Toast.makeText(this, url1, Toast.LENGTH_SHORT).show();
        StringRequest stringRequest=new StringRequest(Request.Method.GET, url1,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject student = array.getJSONObject(i);
                                studentList.add(new student_list_adapter(student.getString("Name"),
                                        student.getString("Roll"),
                                        student.getString("Classe"),
                                        student.getString("Section"),
                                        student.getString("Attendance"),
                                        student.getString("Comments")));
                            }

                            student_list_real_adapter adapter = new student_list_real_adapter(Student_List.this, studentList);
                            recyclerView.setAdapter(adapter);

                            Toast.makeText(Student_List.this, "Data Fetched", Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Student_List.this, "Data Not Fetched", Toast.LENGTH_SHORT).show();
                    }
                });

        Volley.newRequestQueue(this).add(stringRequest);
    }
}
