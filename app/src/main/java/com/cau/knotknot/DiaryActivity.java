package com.cau.knotknot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    ListView listView;
    DiaryAdapter  adapter;

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
        listView = (ListView)findViewById(R.id.diary_list);


        String date = "2021-05-07";   // 수정할것!! 조회하려는 날짜 (첫 화면에선 오늘로 해야 함)

        getDiary(date);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(),EmoActivity.class);
                    startActivity(intent);

            }
        });
    }

    private void getDiary(String date){
        retrofitClient = RetrofitClient.getInstance();
        retrofitInterface = RetrofitClient.getRetrofitInterface();

        retrofitInterface.getDiary(date).enqueue(new Callback<List<Diary>>() {
            @Override
            public void onResponse(@NonNull Call<List<Diary>> call, @NonNull Response<List<Diary>> response) {
                if (!response.isSuccessful()) {
                    // 불러오는데 실패했다는 메세지
                    // textViewResult.setText("code: " + response.code());
                    return;
                }

                List<Diary> diaries = response.body();

                Log.d("retrofit", "Diary fetch success");

                //////////////////////////////////////////////
                /* diaries 리스트를 ListView 로 표현하는 코드 */
                //////////////////////////////////////////////

                //adapter생성
                adapter = new DiaryAdapter();

                Drawable useremo = getResources().getDrawable(R.drawable.useremo);

                int l=diaries.size();
                for(int i=0;i<l;i++){
                    Drawable emo=getResources().getDrawable(R.drawable.emo1);
                    switch (diaries.get(i).getEmotion()){
                        case 1:
                            emo=getResources().getDrawable(R.drawable.emo1);
                            break;
                        case 2:
                            emo=getResources().getDrawable(R.drawable.emo2);
                            break;
                        case 3:
                            emo=getResources().getDrawable(R.drawable.emo3);
                            break;
                        case 4:
                            emo=getResources().getDrawable(R.drawable.emo4);
                            break;
                        case 5:
                            emo=getResources().getDrawable(R.drawable.emo5);
                            break;
                        case 6:
                            emo=getResources().getDrawable(R.drawable.emo6);
                            break;
                        case 7:
                            emo=getResources().getDrawable(R.drawable.emo7);
                            break;
                        case 8:
                            emo=getResources().getDrawable(R.drawable.emo8);
                            break;
                    }
                    adapter.addItem( emo,useremo,diaries.get(i).getUserNickname(),diaries.get(i).getDescription(),diaries.get(i).getCreatedAt());
                }
                listView.setAdapter(adapter);



            }

            @Override
            public void onFailure(@NonNull Call<List<Diary>> call, @NonNull Throwable t) {
                Log.d("retrofit", "Diary fetch failed");
            }
        });
    }
}