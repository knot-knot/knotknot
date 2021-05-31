package com.cau.knotknot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReplyActivity extends AppCompatActivity {

    private RetrofitClient retrofitClient = RetrofitClient.getInstance();
    private RetrofitInterface retrofitInterface = RetrofitClient.getRetrofitInterface();

    TextView reply_description;
    Button reply_diary_modify, reply_diary_delete, reply_add_btn;
    ListView reply;
    EditText reply_add;
    int diaryId, emoticon;
    String description, diary_writer;
    ImageView tv_back;
    ReplyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reply);

        Intent intent = getIntent();//일기 id 가져옴
        diaryId =  getIntent().getIntExtra("diaryId",0);
        description =  getIntent().getStringExtra("description");
        emoticon = getIntent().getIntExtra("emoticon",0);
        diary_writer =  getIntent().getStringExtra("email");
        tv_back = (ImageView)findViewById(R.id.tv_back);
        reply_description = (TextView)findViewById(R.id.reply_description);
        reply_diary_modify = (Button)findViewById(R.id.reply_diary_modify);
        reply_diary_delete =(Button)findViewById(R.id.reply_diary_delete);
        reply = (ListView)findViewById(R.id.reply);
        reply_add =(EditText)findViewById(R.id.reply_add);
        reply_add_btn =(Button)findViewById(R.id.reply_add_btn);

        SharedPreferences pref = getSharedPreferences("user_pref", MODE_PRIVATE);
        if (diary_writer.equals(pref.getString("email","이메일"))){
            reply_diary_modify.setVisibility(View.VISIBLE);
            reply_diary_delete.setVisibility(View.VISIBLE);
            reply_diary_modify.setClickable(false);
            reply_diary_delete.setClickable(false);
        }

        //가져온 정보 set()
        reply_description.setText(description);

        //일기 배경 gif
        switch (emoticon) {
            case 1:
                break;
            case 2:
                //고민있어요
                Glide.with(this).load(R.drawable.worry).into(tv_back);
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                Glide.with(this).load(R.drawable.praise).into(tv_back);
                break;
            case 7:
                break;
            case 8:
                break;
            default:
        }
        // 댓글 리스트 가져오기
        getReply();

        reply_diary_modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //일기 id를 가지고
                //일기 수정(작성창) intent 띄우기
            }
        });

        reply_diary_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //일기 id를 가지고 일기 삭제
            }
        });

        reply_add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = reply_add.getText().toString();
                createReply(content);


            }
        });
    }

    //getReply()
    private void getReply(){
        retrofitClient = RetrofitClient.getInstance();
        retrofitInterface = RetrofitClient.getRetrofitInterface();

        retrofitInterface.getComments(diaryId).enqueue(new Callback<List<Comments>>() {
            @Override
            public void onResponse(@NonNull Call<List<Comments>> call, @NonNull Response<List<Comments>> response) {
                if (!response.isSuccessful()) {
                    // 불러오는데 실패했다는 메세지
                    // textViewResult.setText("code: " + response.code());
                    return;
                }

                List<Comments> comments = response.body();

                Log.d("retrofit", "Comments fetch success - diaryId: "+diaryId);

                //////////////////////////////////////////////
                /* comments 리스트를 ListView 로 표현하는 코드 */
                //////////////////////////////////////////////

                //adapter생성
                adapter = new ReplyAdapter();

                int l=comments.size();
                for(int i=0;i<l;i++){

                    adapter.addItem(comments.get(i).getUserNickname(),
                            comments.get(i).getContent(),
                            comments.get(i).getCreatedAt().substring(11),    // 날짜를 제외하고 시간만 출력
                            comments.get(i).getCommentsId(),
                            comments.get(i).getWriter());
                }
                reply.setAdapter(adapter);
            }

            @Override
            public void onFailure(@NonNull Call<List<Comments>> call, @NonNull Throwable t) {
                Toast.makeText(getApplicationContext(),"네트워크가 원활하지 않습니다.",Toast.LENGTH_SHORT).show();

                Log.d("retrofit", "Comments fetch failed");
            }
        });
    }

    private void createReply(String content) {

        // 코멘트 데이터 Body
        Map<String, Object> map = new HashMap<>();
        map.put("content", content);

        retrofitInterface.createComments(diaryId, map).enqueue((new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                Log.d("retrofit", "Comments post success");
                Toast.makeText(getApplicationContext(),"댓글이 작성되었습니다.",Toast.LENGTH_SHORT).show();

                // 댓글 리스트 새로고침
                getReply();
                reply_add.setText(null);
            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                Toast.makeText(getApplicationContext(),"네트워크가 원활하지 않습니다.",Toast.LENGTH_SHORT).show();

                Log.d("retrofit", "Comments post failed");
            }
        }));
    }
}