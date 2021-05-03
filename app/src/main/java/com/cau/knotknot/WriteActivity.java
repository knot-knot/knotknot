package com.cau.knotknot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.cau.knotknot.Diary;
import com.cau.knotknot.RetrofitClient;
import com.cau.knotknot.RetrofitInterface;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WriteActivity extends AppCompatActivity {
    //일기 작성
    /*
        패턴 선택하는 콤보박스필요
        날짜는 '+'버튼을 누른 activity_diary.xml에 떠있는 날짜로 설정해야할 듯
        저장 버튼을 누르면 작성됨
     */
    Spinner spinner;
    ArrayAdapter sAdapter;

    private RetrofitClient retrofitClient;
    private RetrofitInterface retrofitInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        spinner = (Spinner) findViewById(R.id.pattern); //butterknife 없을경우
        sAdapter = ArrayAdapter.createFromResource(this, R.array.pattern, android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(sAdapter);


        retrofitClient = RetrofitClient.getInstance();
        retrofitInterface = RetrofitClient.getRetrofitInterface();

        // 일기 예시
        Map<String, Object> map = new HashMap<>();
        map.put("writer", "danny1234");
        map.put("description", "오늘은 처음으로 무당벌레를 발견해서 기뻤다. 생각보다 엄청 작았다.");
        map.put("emotion", 1);
        map.put("createdAt", "2021-05-03 15:26:00");



        retrofitInterface.createDiary(map).enqueue((new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                Log.d("retrofit", "Diary post success");
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {

            }
        }));
    }
}