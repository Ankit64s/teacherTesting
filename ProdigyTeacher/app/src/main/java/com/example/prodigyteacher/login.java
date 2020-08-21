package com.example.prodigyteacher;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class login extends AppCompatActivity {
    EditText verifyid,verifyname,verifymobile;
    Button verifybutton,proceed;
    String ids,name,mobile,id;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        verifyid=findViewById(R.id.verifyid);
        verifyname=findViewById(R.id.verifyname);
        verifymobile=findViewById(R.id.verifymobile);
        verifybutton=findViewById(R.id.verifybutton);
        proceed=findViewById(R.id.proceed);
        progressDialog=new ProgressDialog(this);

        verifybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ids=verifyid.getText().toString();
                if(TextUtils.isEmpty(ids)){
                    verifyid.setError("Required Field");
                    return;
                }
                if(ids.length()<12){
                    verifyid.setError("Too Short");
                    return;
                }
                getData();
                new BackgroundJob().execute();
            }
        });

        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mobile=verifymobile.getText().toString();
                if(TextUtils.isEmpty(mobile)){
                    Toast.makeText(login.this, "Please Perform Verify Operation", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(mobile.equals("null")){
                    Toast.makeText(login.this, "You are Not Registered. Contact Admin", Toast.LENGTH_SHORT).show();
                    return;
                }

               Intent i=new Intent(getApplicationContext(),send_code.class);
                i.putExtra("mob",mobile);
                i.putExtra("id",id);
                i.putExtra("name",name);
                startActivity(i);
            }
        });

    }
    public void getData() {
        ids=verifyid.getText().toString();
        RequestQueue rq= Volley.newRequestQueue(this);
        StringBuilder sb= new StringBuilder();
        sb.append(Config.DATA_URL+ids);
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
        id="";
        name="";
        mobile="";


        try {
            JSONObject jo=new JSONObject(response).getJSONArray(Config.KEY_RESULT).getJSONObject(0);
            id=jo.getString(Config.KEY_Troll);
            name=jo.getString(Config.KEY_TName);
            mobile=jo.getString(Config.KEY_TMobile);


        } catch (JSONException e) {

        }
        verifyname.setText(name);
        verifymobile.setText(mobile);
    }

    private class BackgroundJob extends AsyncTask<Void,Void,Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setTitle("Record");
            progressDialog.setMessage("Fetching Details From Database.....");
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Thread.sleep(2000);
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

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(login.this).setIcon(R.drawable.ic_launcher_background)
                .setMessage("Do You Really Want To Close The App?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).setNegativeButton("No",null).show();
    }
}
