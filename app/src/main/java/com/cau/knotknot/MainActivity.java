package com.cau.knotknot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    EditText id, pw;
    Button login, join;
    String s_id, s_pw;

    private RetrofitClient retrofitClient;
    private RetrofitInterface retrofitInterface;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        id = (EditText)findViewById(R.id.main_id);
        pw = (EditText)findViewById(R.id.main_pw);

        login = (Button)findViewById(R.id.main_login);
        join = (Button)findViewById(R.id.main_join);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if ( id.getText().toString().length() == 0 ) {
                    //공백일 때 에러 메시지
                } else {
                    s_id = id.getText().toString();
                }

                if ( pw.getText().toString().length() == 0 ) {
                    //공백일 때 에러 메시지
                } else {
                    s_pw = pw.getText().toString();
                }

                //s_pw = sha256ToString(s_pw);
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

    private void login(String email, String password) {
        retrofitClient = RetrofitClient.getInstance();
        retrofitInterface = RetrofitClient.getRetrofitInterface();

        // 로그인 데이터 Body
        Map<String, Object> map = new HashMap<>();
        map.put("email", email);
        map.put("password", password);

        retrofitInterface.login(map).enqueue((new Callback<LoginResponse>() {
            @Override
            public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
                if(response.isSuccessful()) {
                    LoginResponse loginResponse = response.body();
                    String token = loginResponse.getToken();
                    retrofitClient = RetrofitClient.getInstanceWithToken(token);

                    Intent i = new Intent(getApplicationContext(),DiaryActivity.class);
                    startActivity(i);
                }
                // 로그인 실패
                else if (response.code() == 401) {
                    Toast.makeText(getApplicationContext(), "이메일 또는 비밀번호가 올바르지 않습니다.", Toast.LENGTH_SHORT).show();

                    // 필드 비우기
                }
            }

            @Override
            public void onFailure(@NonNull Call<LoginResponse> call, @NonNull Throwable t) {
                Toast.makeText(getApplicationContext(),"오류가 발생했습니다.",Toast.LENGTH_SHORT).show();

                Log.d("retrofit", "Login failed");
            }
        }));
    }

    public static String sha256ToString(String input) {
        String result = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(input.getBytes());
            byte[] bytes = md.digest();
            StringBuffer sb = new StringBuffer();
            for (byte b : bytes) {
                sb.append(String.format("%02x", b));
            }
            result = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return result;
    }
}