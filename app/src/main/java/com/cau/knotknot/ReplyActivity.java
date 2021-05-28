package com.cau.knotknot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class ReplyActivity extends AppCompatActivity {

    TextView reply_description;
    Button reply_diary_modify, reply_diary_delete;
    ListView reply;
    EditText reply_add;
    int diaryId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reply);

        Intent intent = getIntent();//일기 id 가져옴
        diaryId =  getIntent().getIntExtra("diaryId",0);
        reply_description = (TextView)findViewById(R.id.reply_description);
        reply_diary_modify = (Button)findViewById(R.id.reply_diary_modify);
        reply_diary_delete =(Button)findViewById(R.id.reply_diary_delete);
        reply = (ListView)findViewById(R.id.reply);
        reply_add =(EditText)findViewById(R.id.reply_add);

        //일기 id로 일기 정보 get()

        //가져온 정보 set()
        reply_description.setText("");

        reply_diary_modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //일기 id를 가지고
                //일기 수정(작성창) intent 띄우기
            }
        });

        reply_diary_delete.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //일기 id를 가지고 일기 삭제
            }
        }));
    }

    //getReply()
}