package com.cau.knotknot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DiaryActivity extends AppCompatActivity {

    private RetrofitClient retrofitClient;
    private RetrofitInterface retrofitInterface;

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

        String username = "danny1234";       // 사용자 아이디
        String date = "2021-05-05";          // 조회하려는 날짜 (첫 화면에선 오늘로 해야 함)

        getDiary(username, date);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(),WriteActivity.class);
                    startActivity(intent);

            }
        });
    }

    private void getDiary(String username, String date){
        retrofitClient = RetrofitClient.getInstance();
        retrofitInterface = RetrofitClient.getRetrofitInterface();

        retrofitInterface.getDiary(username, date).enqueue(new Callback<List<Diary>>() {
            @Override
            public void onResponse(@NonNull Call<List<Diary>> call, @NonNull Response<List<Diary>> response) {
                if (!response.isSuccessful()) {
                    // 불러오는데 실패했다는 메세지
                    // textViewResult.setText("code: " + response.code());
                    return;
                }

                List<Diary> diaries = response.body();

                //////////////////////////////////////////////
                /* diaries 리스트를 ListView 로 표현하는 코드 */
                //////////////////////////////////////////////

                // Diary 객체에서 데이터 추출하는 거 예시
                int i = 0;
                diaries.get(i).getUserNickname();   // "은진이"
                diaries.get(i).getDescription();    // "오늘은 집에 혼자 있..."
                diaries.get(i).getEmotion();        // 1
                diaries.get(i).getCreatedAt();      // "2021-04-27 13:56:00"


                Log.d("retrofit", "Diary fetch success: "+diaries.get(0).toString());
            }

            @Override
            public void onFailure(@NonNull Call<List<Diary>> call, @NonNull Throwable t) {
                Log.d("retrofit", "Diary fetch failed");
            }
        });
    }
}