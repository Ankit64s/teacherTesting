package com.example.prodigyteacher;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Personal_Profile extends AppCompatActivity {
    TextView teacherid,teachername,teachermobile,teacheraddress,teacheradhar,teachersalary,teachercomments,teachernotice;
    String tid,name,mobile,address,adhar,salary,comments,notice;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal__profile);
        teacherid=findViewById(R.id.teacherid);
        teachername=findViewById(R.id.teachername);
        teachermobile=findViewById(R.id.teachermobile);
        teacheraddress=findViewById(R.id.teacheraddress);
        teacheradhar=findViewById(R.id.teacheradhar);
        teachersalary=findViewById(R.id.teachersalary);
        teachercomments=findViewById(R.id.teachercomments);
        teachernotice=findViewById(R.id.teachernotice);
        progressDialog=new ProgressDialog(this);

        tid=getid();
        getData();
        new BackgroundJob().execute();
    }

    public void getData() {
        RequestQueue rq= Volley.newRequestQueue(this);
        StringBuilder sb= new StringBuilder();
        sb.append(Config.DATA_URL2+tid);
        rq.add(new StringRequest(sb.toString(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "Data Fetched", Toast.LENGTH_SHORT).show();
                ShowJson(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Data Not Fetched", Toast.LENGTH_SHORT).show();
            }
        }));
    }

    public void ShowJson(String response) {
        name="";
        mobile="";
        address="";
        adhar="";
        salary="";
        comments="";
        notice="";
        try {
            JSONObject jo=new JSONObject(response).getJSONArray(Config.KEY_RESULT).getJSONObject(0);
            name=jo.getString(Config.KEY_TName);
            mobile=jo.getString(Config.KEY_TMobile);
            address=jo.getString(Config.KEY_TAddress);
            adhar=jo.getString(Config.KEY_TAdhar);
            salary=jo.getString(Config.KEY_TSalary);
            comments=jo.getString(Config.KEY_TComments);
            notice=jo.getString(Config.KEY_TNotice);
        } catch (JSONException e) {

        }
        teacherid.setText(tid);
        teachername.setText(name);
        teachermobile.setText(mobile);
        teacheraddress.setText(address);
        teacheradhar.setText(adhar);
        teachersalary.setText(salary);
        teachercomments.setText(comments);
        teachernotice.setText(notice);
    }

    private String getid() {
        String var;
        SharedPreferences sharedPreferences=getSharedPreferences("application", Context.MODE_PRIVATE);
        var=sharedPreferences.getString("Id","");
        return var;
    }

    private class BackgroundJob extends AsyncTask<Void,Void,Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setTitle("Teacher's Record");
            progressDialog.setMessage("Getting Record From Database....");
            progressDialog.show();
            progressDialog.setCancelable(false);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            progressDialog.dismiss();
        }
    }
}
