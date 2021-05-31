package com.cau.knotknot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DiaryActivity extends AppCompatActivity implements View.OnClickListener{

    private RetrofitClient retrofitClient;
    private RetrofitInterface retrofitInterface;

    ImageButton yesterday_btn, tomorrow_btn, calendar_btn;
    LocalDate selectedDate;
    TextView date;
    long mNow;
    Date mDate;
    DateTimeFormatter mFormat_screen = DateTimeFormatter.ofPattern("M월 d일 (E)");
    DateTimeFormatter  mFormat_server = DateTimeFormatter.ofPattern("YYYY-MM-dd");
    ImageButton add;
    ListView listView;
    DiaryAdapter  adapter;

    private void changeDate() {
        date.setText(selectedDate.format(mFormat_screen));
        getDiary(selectedDate.format(mFormat_server));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);

        yesterday_btn = (ImageButton)findViewById(R.id.yesterday_btn);
        tomorrow_btn = (ImageButton)findViewById(R.id.tomorrow_btn);
        calendar_btn = (ImageButton)findViewById(R.id.calendar_btn);
        add = (ImageButton)findViewById(R.id.diary_add);
        date = (TextView)findViewById(R.id.date);
        listView = (ListView)findViewById(R.id.diary_list);

        Intent intent = getIntent();
        selectedDate = (LocalDate) intent.getSerializableExtra("selectedDate");
        if (selectedDate == null) {
            selectedDate = LocalDate.now(ZoneId.of("Asia/Seoul"));
        }
        changeDate();

        yesterday_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedDate = selectedDate.minusDays(1);
                changeDate();
            }
        });
        tomorrow_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedDate = selectedDate.plusDays(1);
                changeDate();
            }
        });

        calendar_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),CalendarActivity.class);
                startActivity(intent);
            }
        });

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

                Log.d("retrofit", "Diary fetch success - date: "+date);

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
                    adapter.addItem(emo,
                            useremo,
                            diaries.get(i).getUserNickname(),
                            diaries.get(i).getDescription(),
                            diaries.get(i).getCreatedAt().substring(11),    // 날짜를 제외하고 시간만 출력
                            diaries.get(i).getCommentsCount(),
                            diaries.get(i).getDiaryId(),
                            diaries.get(i).getWriter()
                    );
                }
                listView.setAdapter(adapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView parent, View v, int position, long id) {
                        Intent intent = new Intent(getApplicationContext(), ReplyActivity.class);
                        /* putExtra의 첫 값은 식별 태그, 뒤에는 다음 화면에 넘길 값 */
                        intent.putExtra("diaryId", diaries.get(position).getDiaryId());
                        intent.putExtra("description", diaries.get(position).getDescription());
                        //추가로 넘겨야할 정보 있으면 여기 작성
                        startActivity(intent);
                    }
                });



            }

            @Override
            public void onFailure(@NonNull Call<List<Diary>> call, @NonNull Throwable t) {
                Log.d("retrofit", "Diary fetch failed");
            }
        });
    }

    @Override
    public void onClick(View view) {

    }
}