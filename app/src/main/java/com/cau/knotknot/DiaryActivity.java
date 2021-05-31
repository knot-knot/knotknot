package com.cau.knotknot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DiaryActivity extends AppCompatActivity implements View.OnClickListener{

    private RetrofitClient retrofitClient;
    private RetrofitInterface retrofitInterface;

    DrawerLayout drawer_layout;
    LinearLayout drawer;
    ImageButton openDrawer;
    TextView show_fam_code;
    ImageView drawer_prof;

    TextView date;
    long mNow;
    Date mDate;
    SimpleDateFormat mFormat = new SimpleDateFormat("MM월 dd일 (E)");
    SimpleDateFormat mFormat_server = new SimpleDateFormat("YYYY-MM-dd");
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

        drawer_layout = (DrawerLayout)findViewById(R.id.drawer_layout);
        drawer = (LinearLayout)findViewById(R.id.drawer);
        openDrawer = (ImageButton)findViewById(R.id.openDrawer);
        show_fam_code = (TextView)findViewById(R.id.show_fam_code);
        drawer_prof = (ImageView)findViewById(R.id.drawer_prof);

        openDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer_layout.openDrawer(drawer);
            }
        });

        drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED,drawer);

        drawer_prof.setBackground(new ShapeDrawable(new OvalShape()));
        drawer_prof.setClipToOutline(true);

        add = (ImageButton)findViewById(R.id.diary_add);
        date = (TextView)findViewById(R.id.date);
        date.setText(getTime());//수정할것!-->캘린더 선택한 날짜로 바꿔야함
        listView = (ListView)findViewById(R.id.diary_list);

        mNow = System.currentTimeMillis();
        mDate = new Date(mNow);

        getDiary(mFormat_server.format(mDate));

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
                        intent.putExtra("emoticon",diaries.get(position).getEmotion());
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