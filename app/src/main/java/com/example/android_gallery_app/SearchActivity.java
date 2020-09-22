package com.example.android_gallery_app;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;

public class SearchActivity extends AppCompatActivity {

    private static final String TAG = "SearchActivity";

    private TextView mDisplayStartDate;
    private TextView mDisplayEndDate;
    private DatePickerDialog.OnDateSetListener mDateSetListenerStart;
    private DatePickerDialog.OnDateSetListener mDateSetListenerEnd;

    private void DatePicker(DatePickerDialog.OnDateSetListener listener){
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(
                SearchActivity.this,
                android.R.style.Theme_DeviceDefault_DayNight,
                listener,
                year,month,day);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        dialog.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        mDisplayStartDate = (TextView) findViewById(R.id.textViewDate);
        mDisplayEndDate = (TextView) findViewById(R.id.textViewDate2);

        mDisplayEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePicker(mDateSetListenerEnd);
            }
        });

        mDisplayStartDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                DatePicker(mDateSetListenerStart);
            }
        });

        mDateSetListenerStart = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month+1;
                String date = month + "/" + day + "/" + year;
                mDisplayStartDate.setText(date);
            }
        };

        mDateSetListenerEnd = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month+1;
                String date = month + "/" + day + "/" + year;
                mDisplayEndDate.setText(date);
            }
        };
    }
}