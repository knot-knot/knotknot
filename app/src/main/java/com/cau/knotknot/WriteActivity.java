package com.cau.knotknot;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class WriteActivity extends AppCompatActivity {
    //일기 작성
    /*
        패턴 선택하는 콤보박스필요
        날짜는 '+'버튼을 누른 activity_diary.xml에 떠있는 날짜로 설정해야할 듯
        저장 버튼을 누르면 작성됨
     */
    Spinner spinner;
    ArrayAdapter sAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        spinner = (Spinner) findViewById(R.id.pattern); //butterknife 없을경우
        sAdapter = ArrayAdapter.createFromResource(this, R.array.pattern, android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(sAdapter);

    }
}