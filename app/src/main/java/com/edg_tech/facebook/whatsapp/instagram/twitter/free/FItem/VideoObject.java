package com.edg_tech.facebook.whatsapp.instagram.twitter.free.FItem;

import android.graphics.Bitmap;

class VideoObject {
    private final String quality;
    private final Bitmap thumb;
    private String type;
    private final String videoSize;
    private String videoUrl;

    public VideoObject(Bitmap bitmap, String str, String str2, String str3, String str4) {
        this.thumb = bitmap;
        this.videoUrl = str;
        this.videoSize = str2;
        this.type = str3;
        this.quality = str4;
    }

    public Bitmap getThumb() {
        return this.thumb;
    }

    public String getVideoSize() {
        return this.videoSize;
    }

    public String getQuality() {
        return this.quality;
    }

    public String getVideoUrl() {
        return this.videoUrl;
    }

    public void setVideoUrl(String str) {
        this.videoUrl = str;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String str) {
        this.type = str;
    }
}