package com.example.prodigyteacher;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

public class homework extends AppCompatActivity {
    EditText enterclass,entersection,newhw;
    TextView currenthw;
    Button detailsfetch,updatehw;
    ProgressDialog progressDialog;
    String classenter,sectionenter,chw,nhw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homework);

        enterclass=findViewById(R.id.enterclass);
        entersection=findViewById(R.id.entersection);
        currenthw=findViewById(R.id.currenthw);
        newhw=findViewById(R.id.newhw);
        updatehw=findViewById(R.id.updatehw);
        detailsfetch=findViewById(R.id.detailfetch);
        progressDialog=new ProgressDialog(this);

        detailsfetch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                classenter=enterclass.getText().toString();
                sectionenter=entersection.getText().toString();
                if(TextUtils.isEmpty(classenter)){
                    enterclass.setError("Required Field");
                    return;
                }
                if (TextUtils.isEmpty(sectionenter)){
                    entersection.setError("Required Field");
                    return;
                }
                getData();
                new BackgroundJob().execute();
            }
        });

        updatehw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chw=currenthw.getText().toString();
                nhw=newhw.getText().toString();
                if(TextUtils.isEmpty(chw)){
                    Toast.makeText(getApplicationContext(), "Kindly Perform Show Operation", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(chw==null){
                    Toast.makeText(getApplicationContext(), "No Such Class or Section Exists", Toast.LENGTH_SHORT).show();
                    return;
                }
                update();
                new progressor().execute();
            }
        });
    }

    public void update() {
        RequestQueue rq=Volley.newRequestQueue(this);
        StringRequest r1= new StringRequest(Request.Method.POST, "https://prodigyanand.000webhostapp.com/studentapi/update_hw.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "Homework Updated Successfully ", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Ohh Try Again", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hm =new HashMap<>();
                hm.put("cl",classenter);
                hm.put("s",sectionenter);
                hm.put("hw",nhw);
                return hm;
            }
        };
        rq.add(r1);
    }

    public void getData() {
            classenter=enterclass.getText().toString();
            sectionenter=entersection.getText().toString();
            RequestQueue rq= Volley.newRequestQueue(this);
            StringBuilder sb= new StringBuilder();
            sb.append(Config.DATA_URL3+classenter+"&s="+sectionenter);
            rq.add(new StringRequest(sb.toString(), new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Toast.makeText(getApplicationContext(), "Data Fetched", Toast.LENGTH_SHORT).show();
                    ShowJson(response);
                   enterclass.setFocusable(false);
                   entersection.setFocusable(false);

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), "Data Not Fetched", Toast.LENGTH_SHORT).show();
                }
            }));
        }

        public void ShowJson(String response) {
            chw="";

            try {
                JSONObject jo=new JSONObject(response).getJSONArray(Config.KEY_RESULT).getJSONObject(0);
                chw=jo.getString(Config.KEY_HOMEWORK);

            } catch (JSONException e) {

            }
            currenthw.setText(chw);
    }

    public class BackgroundJob extends AsyncTask<Void,Void,Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setTitle("Teacher's Record");
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
            progressDialog.setTitle("Teacher's Record");
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
