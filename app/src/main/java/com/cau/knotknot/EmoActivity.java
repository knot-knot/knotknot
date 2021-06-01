package com.cau.knotknot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class EmoActivity extends AppCompatActivity {

    ImageButton emo1, emo2, emo3, emo4, emo5, emo6, emo7,emo8;
    Button.OnClickListener clickListener;
    int emoticon;
    Boolean newDiary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emo);

        newDiary=true;

        emo1 = (ImageButton)findViewById(R.id.emo1);
        emo2 = (ImageButton)findViewById(R.id.emo2);
        emo3 = (ImageButton)findViewById(R.id.emo3);
        emo4 = (ImageButton)findViewById(R.id.emo4);
        emo5 = (ImageButton)findViewById(R.id.emo5);
        emo6 = (ImageButton)findViewById(R.id.emo6);
        emo7 = (ImageButton)findViewById(R.id.emo7);
        emo8 = (ImageButton)findViewById(R.id.emo8);

        clickListener = new Button.OnClickListener(){
            @Override
            public void onClick(View view) {
                switch(view.getId()){
                    case R.id.emo1:
                        emoticon =1;
                        break;
                    case R.id.emo2:
                        emoticon =2;
                        break;
                    case R.id.emo3:
                        emoticon =3;
                        break;
                    case R.id.emo4:
                        emoticon =4;
                        break;
                    case R.id.emo5:
                        emoticon =5;
                        break;
                    case R.id.emo6:
                        emoticon =6;
                        break;
                    case R.id.emo7:
                        emoticon =7;
                        break;
                    case R.id.emo8:
                        emoticon =8;
                        break;
                }

                LocalDate now = LocalDate.now(ZoneId.of("Asia/Seoul"));
                DateTimeFormatter shortDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                String emoDate = now.format(shortDate);

                Intent intent = new Intent(getApplicationContext(),WriteActivity.class);
                intent.putExtra("emoticon",emoticon);
                intent.putExtra("emoDate",emoDate);
                intent.putExtra("newDiary",newDiary);
                intent.setFlags(intent.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intent);
            }
        };

        emo1.setOnClickListener(clickListener);
        emo2.setOnClickListener(clickListener);
        emo3.setOnClickListener(clickListener);
        emo4.setOnClickListener(clickListener);
        emo5.setOnClickListener(clickListener);
        emo6.setOnClickListener(clickListener);
        emo7.setOnClickListener(clickListener);
        emo8.setOnClickListener(clickListener);
    }
}