package com.example.prodigyteacher;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AttendanceAdapter extends RecyclerView.Adapter<AttendanceAdapter.ListViewHolder> {

    String name;
    String roll;
    String classe;
    String section;
    String atendance;
    ProgressDialog progressDialog;

    private Context mCtx;
    private List<AttendancePreview> studentList;

    public AttendanceAdapter(Context mCtx, List<AttendancePreview> studentList) {
        this.mCtx = mCtx;
        this.studentList = studentList;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.layouts_attendance,parent,false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ListViewHolder holder, int position) {
        final AttendancePreview datamodel=studentList.get(position);

        holder.stuname.setText(datamodel.getName());
        holder.sturoll.setText(datamodel.getRoll());
        holder.stuclass.setText(datamodel.getClasse());
        holder.stusection.setText(datamodel.getSection());
        holder.stuattendance.setText(datamodel.getAttendance());

        holder.present.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name=datamodel.getName();
                roll=datamodel.getRoll();
                classe=datamodel.getClasse();
                section=datamodel.getSection();
                atendance=datamodel.getAttendance();
                int i=Integer.valueOf(atendance);
                i=i+1;
                atendance=Integer.toString(i);
                update();
                //new progressor().execute();
            }
        });
    }

    private void update() {
        RequestQueue rq= Volley.newRequestQueue(mCtx);
        StringRequest r1= new StringRequest(Request.Method.POST, "https://prodigyanand.000webhostapp.com/studentapi/update_attendance.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(mCtx, "Attendance Marked", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mCtx, "Ohh Try Again", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> hm =new HashMap<>();
                hm.put("r",roll);
                hm.put("n",name);
                hm.put("cl",classe);
                hm.put("s",section);
                hm.put("at",atendance);
                return hm;
            }
        };
        rq.add(r1);
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {

        TextView stuname,sturoll,stuclass,stusection,stuattendance;
        Button present;
        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            stuname=itemView.findViewById(R.id.stuname);
            sturoll=itemView.findViewById(R.id.sturoll);
            stuclass=itemView.findViewById(R.id.stuclass);
            stusection=itemView.findViewById(R.id.stusection);
            stuattendance=itemView.findViewById(R.id.stuattendance);
            present=itemView.findViewById(R.id.present);
        }
    }
}
