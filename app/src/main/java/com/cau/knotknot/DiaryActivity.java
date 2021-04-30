package com.cau.knotknot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DiaryActivity extends AppCompatActivity {

    TextView date;
    long mNow;
    Date mDate;
    SimpleDateFormat mFormat = new SimpleDateFormat("MM월 dd일 (E)");
    ImageButton add;

    private String getTime(){
        mNow = System.currentTimeMillis();
        mDate = new Date(mNow);
        return mFormat.format(mDate);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);

        add = (ImageButton)findViewById(R.id.diary_add);
        date = (TextView)findViewById(R.id.date);
        date.setText(getTime());//수정할것!-->캘린더 선택한 날짜로 바꿔야함

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(),WriteActivity.class);
                    startActivity(intent);

            }
        });
    }
}