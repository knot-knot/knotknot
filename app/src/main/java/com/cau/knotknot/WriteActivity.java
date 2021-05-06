package com.cau.knotknot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.Date;
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
    String username;
    String description;
    String createdAt;
    Button save;
    EditText et;

    private RetrofitClient retrofitClient;
    private RetrofitInterface retrofitInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        save = (Button)findViewById(R.id.save);

        et = (EditText)findViewById(R.id.et);

        Intent intent = getIntent();
        int emoticon = intent.getIntExtra("emoticon",0);
        String emoDate = intent.getStringExtra("emoDate");

        String username = "danny1234";  // 사용자 아이디
        //String description = "오늘은 처음으로....";  // 일기 내용
        //int emotion = 1;                            // 패턴 종류
        //String createdAt = "2021-05-03 15:26:00";  // 작성 시간

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //시간 생성
                long now = System.currentTimeMillis();
                Date mDate = new Date(now);
                SimpleDateFormat shortDate = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat longDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

                createdAt = shortDate.format(mDate);

                if(emoDate.equals(createdAt)) {
                    createdAt = longDate.format(mDate);
                }else{
                    createdAt = emoDate + " 23:59:59";
                }

                description = et.getText().toString();

                /* 저장 버튼을 눌렀을 때 */
                createDiary(username, description, emoticon, createdAt);

            }
        });

    }

    private void createDiary(String username, String description, int emotion, String createdAt) {
        retrofitClient = RetrofitClient.getInstance();
        retrofitInterface = RetrofitClient.getRetrofitInterface();

        // 일기 데이터 Body
        Map<String, Object> map = new HashMap<>();
        map.put("writer", username);
        map.put("description", description);
        map.put("emotion", emotion);
        map.put("createdAt", createdAt);

        retrofitInterface.createDiary(map).enqueue((new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                Log.d("retrofit", "Diary post success");
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                Log.d("retrofit", "Diary post failed");
            }
        }));
    }
}