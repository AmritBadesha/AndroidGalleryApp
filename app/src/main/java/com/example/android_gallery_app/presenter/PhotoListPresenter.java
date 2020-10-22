package com.example.android_gallery_app.presenter;

import com.example.android_gallery_app.model.Photo;

import java.io.IOException;
import java.util.Date;

public interface PhotoListPresenter {
    Photo addCaption(String caption);
    void addPhoto(Photo photo);
    void deletePhoto(String mCurrentPhotoPath) throws IOException;
    String findPhotos_second(Date startTimestamp, Date endTimestamp, String keywords, String topLeft, String bottomRight);
    Photo getPhoto();
    Photo getPhotoByLocation(String loc);
    Photo scrollPhotos(Boolean proc);
}
