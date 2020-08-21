package com.example.prodigyteacher;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class dataAdapter extends RecyclerView.Adapter<dataAdapter.DataViewHolder> {

    private Context mCtx;
    private List<Upload> modelList;

    public dataAdapter(Context mCtx, List<Upload> modelList) {
        this.mCtx = mCtx;
        this.modelList = modelList;
    }


    @NonNull
    @Override
    public dataAdapter.DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.layout_files, null);
        return new DataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull dataAdapter.DataViewHolder holder, int position) {
        final Upload dataModel=modelList.get(position);
        holder.textname.setText(dataModel.getName());
        holder.uploadername.setText(dataModel.getUploader());
        holder.downloadlink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url;
                url=dataModel.getUrl();
                Uri webpage = Uri.parse(url);
                if (!url.startsWith("http://") && !url.startsWith("https://")) {
                    webpage = Uri.parse("https://" + url);
                }
                //Opening the upload file in browser using the upload url
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(webpage);
                mCtx.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class DataViewHolder extends RecyclerView.ViewHolder{

        TextView textname,uploadername;
        Button downloadlink;

        public DataViewHolder(@NonNull View itemView) {
            super(itemView);

            textname=itemView.findViewById(R.id.textname);
            uploadername=itemView.findViewById(R.id.uploadername);
            downloadlink=itemView.findViewById(R.id.downloadlink);
        }
    }
}
