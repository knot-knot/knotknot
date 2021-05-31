package com.cau.knotknot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.cau.knotknot.R.drawable.emo1;

public class WriteActivity extends AppCompatActivity {
    //일기 작성
    /*
        패턴 선택하는 콤보박스필요
        날짜는 '+'버튼을 누른 activity_diary.xml에 떠있는 날짜로 설정해야할 듯
        저장 버튼을 누르면 작성됨
     */

    String description;
    String createdAt;
    Button save;
    EditText et;
    ImageView write_emo;
    Drawable emo;

    private RetrofitClient retrofitClient;
    private RetrofitInterface retrofitInterface;

    ViewDialog viewDialog = new ViewDialog(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);

        save = (Button)findViewById(R.id.save);

        et = (EditText)findViewById(R.id.et);

        Intent intent = getIntent();
        int emoticon = intent.getIntExtra("emoticon",0);
        String emoDate = intent.getStringExtra("emoDate");

        write_emo =(ImageView)findViewById(R.id.write_emo);
        switch (emoticon){
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
        }
        write_emo.setImageDrawable(emo);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewDialog.showDialog();

                //시간 생성
                ZonedDateTime now = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));

                DateTimeFormatter shortDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                DateTimeFormatter longDate = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");

                createdAt = now.format(shortDate);

                if(emoDate.equals(createdAt)) {
                    createdAt = now.format(longDate);
                }else{
                    createdAt = emoDate + " 23:59:59";
                }

                description = et.getText().toString();

                /* 저장 버튼을 눌렀을 때 */
                createDiary(description, emoticon, createdAt);
            }
        });

    }

    private void createDiary(String description, int emotion, String createdAt) {
        retrofitClient = RetrofitClient.getInstance();
        retrofitInterface = RetrofitClient.getRetrofitInterface();

        // 일기 데이터 Body
        Map<String, Object> map = new HashMap<>();
        map.put("description", description);
        map.put("emotion", emotion);
        map.put("createdAt", createdAt);

        retrofitInterface.createDiary(map).enqueue((new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                Log.d("retrofit", "Diary post success");
                Toast.makeText(getApplicationContext(),"저장되었습니다.",Toast.LENGTH_SHORT).show();

                Intent i = new Intent(getApplicationContext(),DiaryActivity.class);
                startActivity(i);
                viewDialog.hideDialog();
            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                Log.d("retrofit", "Diary post failed");
            }
        }));
    }
}