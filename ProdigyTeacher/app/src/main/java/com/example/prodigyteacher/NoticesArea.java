package com.example.prodigyteacher;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class NoticesArea extends AppCompatActivity {
TextView currentnotices;
EditText enterroll, newnotices;
Button detailfetch, updatenotices;
String rolls,newnotice,currentnoticee;
ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notices_area);
        currentnotices=findViewById(R.id.currentnotices);
        newnotices=findViewById(R.id.newnotices);
        enterroll=findViewById(R.id.enterroll);
        detailfetch=findViewById(R.id.detailfetch);
        updatenotices=findViewById(R.id.updatenotices);
        progressDialog=new ProgressDialog(this);

        detailfetch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(enterroll.getText().toString())){
                    enterroll.setError("Required Field");
                    return;
                }
                getData();
               // new BackgroundJob().execute();
                new BackgroundJob1().execute();
            }
        });

        updatenotices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(currentnotices.getText().toString())){
                    Toast.makeText(NoticesArea.this, "Please Perform Show First", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(currentnotices.getText().toString()=="null"){
                    Toast.makeText(NoticesArea.this, "No such Roll No Exists. Contact Admin.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(newnotices.getText().toString())){
                    newnotices.setError("Required Field");
                    return;
                }
                rolls=enterroll.getText().toString();
                newnotice=newnotices.getText().toString();
                update();
                new progressor().execute();

            }
        });



    }

    private void update() {
        RequestQueue rq=Volley.newRequestQueue(this);
        StringRequest r1= new StringRequest(Request.Method.POST, "https://prodigyanand.000webhostapp.com/studentapi/noticeupdate.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "Notice Updated Successfully ", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Ohh Try Again", Toast.LENGTH_SHORT).show();
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> hm =new HashMap<>();
                hm.put("r",rolls);
                hm.put("n",newnotice);
                return hm;
            }
        };
        rq.add(r1);
    }

    private void getData() {
        rolls=enterroll.getText().toString();
        RequestQueue rq= Volley.newRequestQueue(this);
        StringBuilder sb= new StringBuilder();
        sb.append(Config.DATA_URL5+rolls);
        rq.add(new StringRequest(sb.toString(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(NoticesArea.this, "Data Fetched", Toast.LENGTH_SHORT).show();
                ShowJson(response);
                enterroll.setFocusable(false);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(NoticesArea.this, "Data Not Fetched", Toast.LENGTH_SHORT).show();
            }
        }));
    }
    public void ShowJson(String response) {
        currentnoticee="";

        try {
            JSONObject jo=new JSONObject(response).getJSONArray(Config.KEY_RESULT).getJSONObject(0);
            currentnoticee=jo.getString(Config.KEY_Notice);

        } catch (JSONException e) {

        }
        currentnotices.setText(currentnoticee);
    }

    public class BackgroundJob1 extends AsyncTask<Void,Void,Void>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setTitle("Student's Record");
            progressDialog.setMessage("Fetching From Database....");
            progressDialog.setCancelable(false);
            progressDialog.show();
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

    public class progressor extends AsyncTask<Void,Void,Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setTitle("Student's Record");
            progressDialog.setMessage("Updating into Database....");
            progressDialog.setCancelable(false);
            progressDialog.show();
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
