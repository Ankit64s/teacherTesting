package com.example.prodigyteacher;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
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

public class attendance_layout extends AppCompatActivity {
    EditText enterclass,entersection;
    TextView totaldays,addday;
    Button detailsfetch,buttonaddaday,gonext;
    String cl,se,td,ntd;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_layout);
        enterclass=findViewById(R.id.enterclass);
        entersection=findViewById(R.id.entersection);
        totaldays=findViewById(R.id.totaldays);
        addday=findViewById(R.id.addday);
        buttonaddaday=findViewById(R.id.buttonaddaday);
        detailsfetch=findViewById(R.id.detailfetch);
        gonext=findViewById(R.id.gonext);
        progressDialog=new ProgressDialog(this);


        detailsfetch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cl=enterclass.getText().toString();
                se=entersection.getText().toString();
                if(TextUtils.isEmpty(cl)){
                    enterclass.setError("Required Field");
                    return;
                }
                if(TextUtils.isEmpty(se)){
                    entersection.setError("Required Field");
                    return;
                }
                getData();
                new BackgroundJob().execute();

            }
        });

        buttonaddaday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                td=totaldays.getText().toString();
                //ntd=addday.getText().toString();
                if(TextUtils.isEmpty(td)){
                    Toast.makeText(getApplicationContext(), "Kindly Perform Show Operation", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(td==null){
                    Toast.makeText(getApplicationContext(), "No Such Class or Section Exists. Contact Admin.", Toast.LENGTH_SHORT).show();
                    return;
                }

                int i=Integer.valueOf(td);
                i=i+1;
                ntd=Integer.toString(i);
                update();
                new progressor().execute();

                fetchdata();
            }
        });


        gonext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(addday.getText().toString())){
                    Toast.makeText(attendance_layout.this, "Kindly Perform Addition of Day", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent i=new Intent(attendance_layout.this,attendance.class);
                i.putExtra("cls",cl);
                i.putExtra("section",se);
                startActivity(i);
            }
        });

    }

    private void fetchdata() {
        new progressor1().execute();
        RequestQueue rq= Volley.newRequestQueue(this);
        StringBuilder sb= new StringBuilder();
        sb.append(Config.DATA_URL4+cl+"&s="+se);
        rq.add(new StringRequest(sb.toString(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "Data Fetched", Toast.LENGTH_SHORT).show();
                ShowJson1(response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Data Not Fetched", Toast.LENGTH_SHORT).show();
            }
        }));
    }

    public void ShowJson1(String response) {
        td="";

        try {
            JSONObject jo=new JSONObject(response).getJSONArray(Config.KEY_RESULT).getJSONObject(0);
            td=jo.getString(Config.KEY_TOTALDAYS);

        } catch (JSONException e) {

        }
        addday.setText(td);
    }

    private void update() {
        RequestQueue rq=Volley.newRequestQueue(this);
        StringRequest r1= new StringRequest(Request.Method.POST, "https://prodigyanand.000webhostapp.com/studentapi/updatetotaldays.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "Days Updated Successfully ", Toast.LENGTH_SHORT).show();
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
                hm.put("cl",cl);
                hm.put("s",se);
                hm.put("td",ntd);
                return hm;
            }
        };
        rq.add(r1);
    }

    private void getData() {
        RequestQueue rq= Volley.newRequestQueue(this);
        StringBuilder sb= new StringBuilder();
        sb.append(Config.DATA_URL4+cl+"&s="+se);
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
        td="";

        try {
            JSONObject jo=new JSONObject(response).getJSONArray(Config.KEY_RESULT).getJSONObject(0);
            td=jo.getString(Config.KEY_TOTALDAYS);

        } catch (JSONException e) {

        }
        totaldays.setText(td);
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

    public class progressor1 extends AsyncTask<Void,Void,Void> {
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
}
