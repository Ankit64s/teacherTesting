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

public class linkAdapter extends RecyclerView.Adapter<linkAdapter.ViewHolder> {

    private Context mCtx;
    private List<link_upload> modelList;

    //private final static String expression = "(?<=watch\\?v=|/videos/|embed\\/|youtu.be\\/|\\/v\\/|\\/e\\/|watch\\?v%3D|watch\\?feature=player_embedded&v=|%2Fvideos%2F|embed%\u200C\u200B2F|youtu.be%2F|%2Fv%2F)[^#\\&\\?\\n]*";


    public linkAdapter(Context mCtx, List<link_upload> modelList) {
        this.mCtx = mCtx;
        this.modelList = modelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.layout_links, parent,false);
        linkAdapter.ViewHolder viewHolder=new linkAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final link_upload dataModel=modelList.get(position);
        holder.title.setText(dataModel.getName2());
        //String url=dataModel.getUrl2();
        //String videoid=getVideoId(url);
        //String hq="http://img.youtube.com/vi/"+videoid+"/0.jpg";
        //Glide.with(mCtx).load(Uri.parse(hq)).into(holder.imageView);
        holder.uploadername.setText(dataModel.getUploader2());
        holder.target.setText(dataModel.getTarget2());
        holder.viewvideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url=dataModel.getUrl2();
                Uri webpage = Uri.parse(url);
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

        TextView title,uploadername,target;
        //ImageView imageView;
        Button viewvideo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title=itemView.findViewById(R.id.title);
            uploadername=itemView.findViewById(R.id.uploadername);
            target=itemView.findViewById(R.id.target);
            //imageView=itemView.findViewById(R.id.imageview);
            viewvideo=itemView.findViewById(R.id.viewvideo);
        }
    }

    //to get video thumbnail image
   /* public static String getVideoId(String videoUrl) {
        if (videoUrl == null || videoUrl.trim().length() <= 0){
            return null;
        }
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(videoUrl);
        try {
            if (matcher.find())
                return matcher.group();
        } catch (ArrayIndexOutOfBoundsException ex) {
            ex.printStackTrace();
        }
        return null;
    }*/
}
