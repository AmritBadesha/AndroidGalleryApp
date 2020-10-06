package com.example.android_gallery_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.text.DateFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity implements Serializable{

    class Photo implements Serializable {
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

    static final int REQUEST_IMAGE_CAPTURE = 1;
    String mCurrentPhotoPath, photosFilePath;
    private JSONObject photos = null;
    private int index = 0;
    Button btnNext;
    Button btnPrev;
    Button button;
    List<Photo> list = new ArrayList<Photo>();
    ArrayList<Photo> foundPhotos = new ArrayList<>();
    static final int SEARCH_ACTIVITY_REQUEST_CODE = 88;
    private FusedLocationProviderClient fusedLocationClient;
    public String currentPhoto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnNext = (Button) findViewById(R.id.btnNext);
        btnPrev = (Button) findViewById(R.id.btnPrev);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        try {
            showOriginalView();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void showOriginalView() throws IOException {
        boolean fileExists = false;
        File file = new File(Environment.getExternalStorageDirectory()
                .getAbsolutePath(), "/Android/data/com.example.android_gallery_app/files/Pictures");
        File[] fList = file.listFiles();
        if (fList != null) {
            for (File f : fList) {
                if (f.getPath().contains("myPhotos")) {
                    photosFilePath = f.getPath();
                    fileExists = true;
                    File myObj = new File(photosFilePath);
                    Scanner myReader = new Scanner(myObj);
                    while (myReader.hasNextLine()) {
                        String data = myReader.nextLine();
                        String arr[] = data.split(",");
                        list.add(new Photo(arr[0], new Double(arr[2]), new Double(arr[1]), arr[3], arr[4]));
                    }
                    for (Photo photo : list) {
                        displayPhoto(photo.getFile()); // Just want to display the first photo that was added
                        break;
                    }
                    break;
                }
            }
        }
        if (fileExists == false) {
            File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            File photosFile = File.createTempFile("myPhotos", ".txt",storageDir);
            photosFilePath = photosFile.getAbsolutePath();
        }
    }
    public void showOriginalView(View v) throws IOException {
        boolean fileExists = false;
        File file = new File(Environment.getExternalStorageDirectory()
                .getAbsolutePath(), "/Android/data/com.example.android_gallery_app/files/Pictures");
        File[] fList = file.listFiles();
        if (fList != null) {
            for (File f : fList) {
                if(f.getPath().contains("myPhotos")) {
                    photosFilePath = f.getPath();
                    fileExists = true;
                    File myObj = new File(photosFilePath);
                    Scanner myReader = new Scanner(myObj);
                    list = new ArrayList<Photo>();
                    while (myReader.hasNextLine()) {
                        String data = myReader.nextLine();
                        String arr[] = data.split(",");
                        list.add(new Photo(arr[0], new Double(arr[2]), new Double(arr[1]), arr[3], arr[4]));
                    }
                    for (Photo photo: list) {
                        displayPhoto(photo.getFile()); // Just want to display the first photo that was added
                        break;
                    }
                    break;
                }
            }
        }
        if (fileExists == false) {
            File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            File photosFile = File.createTempFile("myPhotos", ".txt",storageDir);
            photosFilePath = photosFile.getAbsolutePath();
        }
    }
    //share image
    public void shareImage(View v){
        ImageView imageView = findViewById(R.id.ivGallery);
        Drawable drawable=imageView.getDrawable();
        Bitmap bitmap=((BitmapDrawable)drawable).getBitmap();


        try {
            String filename = mCurrentPhotoPath.split(getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath(), 2)[1];
            System.out.println(filename);
            File file = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath(), filename);
            FileOutputStream fOut = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
            fOut.flush();
            fOut.close();
            file.setReadable(true, false);
            final Intent intent = new Intent(android.content.Intent.ACTION_SEND);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Uri photoURI = FileProvider.getUriForFile(getApplicationContext(), BuildConfig.APPLICATION_ID +".fileprovider", file);

            intent.putExtra(Intent.EXTRA_STREAM, photoURI);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setType("image/jpg");

            startActivity(Intent.createChooser(intent, "Share image via"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void goToSearch(View view) {
        Intent intent = new Intent(this, SearchActivity.class);
        startActivityForResult(intent, SEARCH_ACTIVITY_REQUEST_CODE);
    }
    public void deletePhoto(View view) throws IOException {
        for (Photo photo: list) {
            if(photo.getFile().equals(mCurrentPhotoPath)) {
                list.remove(photo);
                writeToFile();
                displayPhoto("");
                break;
            }
        }
    }
    public void takePhoto(View v) throws IOException {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this, "com.example.android_gallery_app.fileprovider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    public void findPhotos_second(Date startTimestamp, Date endTimestamp, String keywords, String topLeft, String bottomRight) {
        File file = new File(Environment.getExternalStorageDirectory()
                .getAbsolutePath(), "/Android/data/com.example.android_gallery_app/files/Pictures");
        File[] fList = file.listFiles();
        if (fList != null) {
            for (File f : fList) {
                String split[] = f.getPath().split("\\.");
                if (!split[split.length-1].equals(".txt")) {
                    if (!(f.lastModified() >= startTimestamp.getTime()
                            && f.lastModified() <= endTimestamp.getTime())) {
                        for (int i = 0; i < list.size(); i++) {
                            if (f.getPath().equals(list.get(i).getFile())) {
                                list.remove(list.get(i));
                            }
                        }
                    }
                }
            }
        }
        if(topLeft.length() > 0 && bottomRight.length() > 0) {
            String topLeftCoord[] = topLeft.split(",");
            String bottomRightCoord[] = bottomRight.split(",");
            int j = 0;
            for (Photo photo: list) {
                if (!(new Double(topLeftCoord[0]) < photo.getLat() && new Double(topLeftCoord[0]) < photo.getLng())
                        && !(new Double(bottomRightCoord[0]) > photo.getLat() && new Double(bottomRightCoord[0]) > photo.getLng())) {
                    list.remove(list.get(j));
                }
                j++;
            }
        }
        if (keywords.length() > 0) {
            for (int i = 0; i < list.size(); i++) {
                if (!list.get(i).getCaption().contains(keywords)) {
                    list.remove(list.get(i));
                }
            }
        }
        if(list.isEmpty() == true ) {
            displayPhoto("");
        } else {
            for (Photo photo: list) {
                displayPhoto(photo.getFile());
                break;
            }
        }
    }

    public void scrollPhotos(View v) {
        switch (v.getId()) {
            case R.id.btnPrev:
                if (index > 0) {
                    index--;
                }
                break;
            case R.id.btnNext:
                if (index < (list.size() - 1)) {
                    index++;
                }
                break;
            default:
                break;
        }
        Iterator itr=list.iterator();
        int i = 0;
        while(itr.hasNext()){
            Photo ph =(Photo)itr.next();
            if (i == index) {
                displayPhoto(ph.getFile());
            }
            i++;
        }
    }
    public void addCaption(View v) {
        Iterator itr=list.iterator();
        EditText et = (EditText) findViewById(R.id.etCaption);
        while(itr.hasNext()){
            Photo ph =(Photo)itr.next();
            if (ph.getFile().equals(currentPhoto)) {
                ph.setCaption(et.getText().toString());
                displayPhoto(ph.getFile());
            }
        }
        writeToFile();
    }

    private void displayPhoto(String path) {
        ImageView iv = (ImageView) findViewById(R.id.ivGallery);
        TextView tv = (TextView) findViewById(R.id.tvTimestamp);
        EditText et = (EditText) findViewById(R.id.etCaption);
        if (path == null || path == "") {
            iv.setImageResource(R.mipmap.ic_launcher);
            et.setText("");
            tv.setText("");
        } else {
            mCurrentPhotoPath = path;
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getFile().equals(path)) {
                    iv.setImageBitmap(BitmapFactory.decodeFile(path));
                    tv.setText(list.get(i).getTimeStamp());
                    if (list.get(i).getCaption() != null) {
                        if (!list.get(i).getCaption().equals("null")) {
                            et.setText(list.get(i).getCaption());
                        } else {
                            et.setText("");
                        }
                    } else {
                        et.setText("");
                    }
                    currentPhoto = path;
                    break;
                }
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg",storageDir);
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SEARCH_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                DateFormat format = new SimpleDateFormat("yyyy‐MM‐dd HH:mm:ss");
                Date startTimestamp, endTimestamp;
                try {
                    String from = (String) data.getStringExtra("STARTTIMESTAMP");
                    String to = (String) data.getStringExtra("ENDTIMESTAMP");
                    startTimestamp = format.parse(from);
                    endTimestamp = format.parse(to);
                } catch (Exception ex) {
                    startTimestamp = null;
                    endTimestamp = null;
                }
                String keywords = (String) data.getStringExtra("KEYWORDS");
                String topLeft = data.getStringExtra("TOPLEFT");
                String bottomRight = data.getStringExtra("BOTTOMRIGHT");
                index = 0;
                findPhotos_second(startTimestamp, endTimestamp, keywords, topLeft, bottomRight);
            }
        }
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 5);
                return;
            }
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            // Got last known location. In some rare situations this can be null.
                            if (location != null) {
                                double mLatitude = location.getLatitude();
                                double mLongitude = location.getLongitude();
                                ImageView mImageView = (ImageView) findViewById(R.id.ivGallery);
                                mImageView.setImageBitmap(BitmapFactory.decodeFile(mCurrentPhotoPath));
                                EditText et = (EditText) findViewById(R.id.etCaption);
                                et.setText("");
                                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                                list.add(new Photo(mCurrentPhotoPath, mLatitude, mLongitude, timeStamp));
                                writeToFile();
                                displayPhoto(mCurrentPhotoPath);
                            }
                        }
                    });
        }
    }

    private void writeToFile() {
        FileWriter myWriter = null;
        try {
            myWriter = new FileWriter(photosFilePath);
            StringBuilder str = new StringBuilder("");
            for (Photo photo: list) {
                str.append(photo.toString());
            }
            myWriter.append(str);
            myWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}