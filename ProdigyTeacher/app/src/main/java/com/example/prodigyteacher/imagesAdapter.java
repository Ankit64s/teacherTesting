package com.example.prodigyteacher;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class imagesAdapter extends RecyclerView.Adapter<imagesAdapter.ViewHolder> {

    private Context mCtx;
    private List<img_upload> modelList;

    public imagesAdapter(Context mCtx, List<img_upload> modelList) {
        this.mCtx = mCtx;
        this.modelList = modelList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.layout_images, parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final img_upload dataModel=modelList.get(position);
        holder.textname.setText(dataModel.getName1());
        String name=dataModel.getUploader1();
        Glide.with(mCtx).load(dataModel.getUrl1()).into(holder.imageView);
        holder.uploadername.setText(name);
        holder.downloadimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url;
                url=dataModel.getUrl1();
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

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textname, uploadername;
        ImageView imageView;
        Button downloadimage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textname=itemView.findViewById(R.id.textname);
            uploadername=itemView.findViewById(R.id.uploadername);
            imageView=itemView.findViewById(R.id.imageview);
            downloadimage=itemView.findViewById(R.id.downloadimage);
        }

    }
}
