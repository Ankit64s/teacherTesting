package com.example.prodigyteacher;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class student_list_real_adapter extends RecyclerView.Adapter<student_list_real_adapter.ListViewHolder> {

    private Context mCtx;
    private List<student_list_adapter> studentList;

    public student_list_real_adapter(Context mCtx, List<student_list_adapter> studentList) {
        this.mCtx = mCtx;
        this.studentList = studentList;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.layouts_student_list,parent,false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        student_list_adapter datamodel=studentList.get(position);

        holder.stuname.setText(datamodel.getName());
        holder.sturoll.setText(datamodel.getRoll());
        holder.stuclass.setText(datamodel.getClasse());
        holder.stusection.setText(datamodel.getSection());
        holder.stuattendance.setText(datamodel.getAttendance());
        holder.stucomments.setText(datamodel.getComments());

    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {
        TextView stuname,sturoll,stuclass,stusection,stuattendance,stucomments;
        public ListViewHolder(@NonNull View itemView) {
            super(itemView);

            stuname=itemView.findViewById(R.id.stuname);
            sturoll=itemView.findViewById(R.id.sturoll);
            stuclass=itemView.findViewById(R.id.stuclass);
            stusection=itemView.findViewById(R.id.stusection);
            stuattendance=itemView.findViewById(R.id.stuattendance);
            stucomments=itemView.findViewById(R.id.stucomments);

        }
    }
}
