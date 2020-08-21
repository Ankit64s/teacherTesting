package com.example.prodigyteacher;

import android.net.Uri;

import com.google.android.gms.tasks.Task;

public class Upload {
    public String name;
    public String uploader;
    public String url;

    public Upload() {
    }

    public Upload(String name, String uploader, String url) {
        this.name = name;
        this.uploader = uploader;
        this.url = url;
    }

    public String getUploader() {
        return uploader;
    }

    public Upload(String name, Task<Uri> addOnSuccessListener) {
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }
}
