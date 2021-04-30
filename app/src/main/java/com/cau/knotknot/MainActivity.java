package com.cau.knotknot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText id, pw;
    Button login, join;
    String s_id, s_pw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*id = (EditText)findViewById(R.id.main_id);

        if ( id.getText().toString().length() == 0 ) {
            //공백일 때 에러 메시지
        } else {
            s_id = id.getText().toString();
        }

        pw = (EditText)findViewById(R.id.main_pw);

        if ( pw.getText().toString().length() == 0 ) {
            //공백일 때 에러 메시지
        } else {
            s_pw = pw.getText().toString();
        }*/

        login = (Button)findViewById(R.id.main_login);
        join = (Button)findViewById(R.id.main_join);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Log.d("maintodiary","s_id : "+s_id+" s_pw:"+s_pw);
                //if(s_id.equals("a")&&s_pw.equals("a")) {
                //    Log.d("maintodiary", "equal판별은 함");
                    Intent intent = new Intent(getApplicationContext(), DiaryActivity.class);
                    intent.putExtra("message", "DiaryActivity");
                    startActivity(intent);
                //}
                //else{
                //    Log.d("maintodiary", "equal아님");
                //}
            }
        });

    }
}