package com.cau.knotknot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
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

public class LoginActivity extends AppCompatActivity {

    private RetrofitClient retrofitClient = RetrofitClient.getInstance();
    private RetrofitInterface retrofitInterface = RetrofitClient.getRetrofitInterface();

    EditText id, pw;
    Button login, join;
    String s_id, s_pw;

    ViewDialog viewDialog = new ViewDialog(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        id = (EditText)findViewById(R.id.login_id);
        pw = (EditText)findViewById(R.id.login_pw);

        login = (Button)findViewById(R.id.login);
        join = (Button)findViewById(R.id.join);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if ( id.getText().toString().length() == 0 ) {

                    //공백일 때 에러 메시지
                    Toast.makeText(getApplicationContext(),"이메일을 입력해주세요.",Toast.LENGTH_SHORT).show();
                }
                else if ( pw.getText().toString().length() == 0 ) {

                    //공백일 때 에러 메시지
                    Toast.makeText(getApplicationContext(),"비밀번호를 입력해주세요.",Toast.LENGTH_SHORT).show();
                }
                else {
                    //..show gif
                    viewDialog.showDialog();

                    s_id = id.getText().toString();
                    s_pw = sha256ToString(pw.getText().toString());

                    login(s_id, s_pw);
                }
            }
        });

        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(),JoinActivity.class);
                startActivity(intent);
            }
        });

        // 이메일 필드에서 enter 눌렀을 때 비밀번호 필드 활성화
        id.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if ((event.getAction() == KeyEvent.ACTION_DOWN) && keyCode == KeyEvent.KEYCODE_ENTER) {
                    pw.setFocusableInTouchMode(true);
                    pw.requestFocus();
                    return true;
                }
                return false;
            }
        });
        // 비밀번호 필드에서 enter 눌렀을 때 로그인 버튼 작동
        pw.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if ((event.getAction() == KeyEvent.ACTION_DOWN) && keyCode == KeyEvent.KEYCODE_ENTER) {
                    login.performClick();
                    return true;
                }
                return false;
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

                    SharedPreferences pref = getSharedPreferences("user_pref", MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("email", email);
                    editor.putString("nickname", loginResponse.getNickname());
                    editor.putString("familyCode", loginResponse.getFamilyCode());
                    editor.apply();

                    Intent i = new Intent(getApplicationContext(),DiaryActivity.class);
                    startActivity(i);
                }
                // 로그인 실패
                else if (response.code() == 401) {
                    Toast.makeText(getApplicationContext(), "이메일 또는 비밀번호가 올바르지 않습니다.", Toast.LENGTH_SHORT).show();

                    // 필드 비우기
                }
                viewDialog.hideDialog();
            }

            @Override
            public void onFailure(@NonNull Call<LoginResponse> call, @NonNull Throwable t) {
                Toast.makeText(getApplicationContext(),"네트워크가 원활하지 않습니다.",Toast.LENGTH_SHORT).show();

                Log.d("retrofit", "Login failed");
                viewDialog.hideDialog();

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