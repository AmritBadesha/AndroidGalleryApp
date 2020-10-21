package com.example.android_gallery_app;

import java.io.Serializable;

class Photo implements Graphic, Serializable {
    String file, caption, timeStamp;
    Double lat, lng;

    Photo(String file, Double lat, Double lng, String timeStamp) {
        this.file = file;
        this.lat = lat;
        this.lng = lng;
        this.timeStamp = timeStamp;
    }
    Photo(String file, Double lat, Double lng, String timeStamp, String caption) {
        this.file = file;
        this.lat = lat;
        this.lng = lng;
        this.timeStamp = timeStamp;
        this.caption = caption;
    }
    public String getFile() {
        return file;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public Double getLng() {
        return lng;
    }

    public Double getLat() {
        return lat;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getCaption() {
        return caption;
    }
    @Override
    public String toString() {
        return  this.getFile()
                + "," + this.getLng()
                + "," + this.getLat()
                + "," + this.getTimeStamp()
                + "," + this.getCaption()
                + "\n";
    }
}