package com.cau.knotknot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JoinActivity extends AppCompatActivity {


    private RetrofitClient retrofitClient = RetrofitClient.getInstance();
    private RetrofitInterface retrofitInterface = RetrofitClient.getRetrofitInterface();

    ViewDialog viewDialog = new ViewDialog(this);

    Button join_email_send,join_email_permit,join_profic_button;
    //     인증메일 보내기o  인증번호 일치체크o  파일선택창띄우기
    EditText join_email,join_email_edit,join_pwd,join_pwd_chk,join_nickname,join_profic;
    //       무결성o,중복                 무결성o  비번일치체크o
    Button join_birth_calendar,join_origin_code,                join_new_code;
    //      생일달력띄우기o      기존코드디비확인,모든정보디비에추가 새코드만들어디비에정보추가
    EditText join_birth,join_fam_code;
    TextView join_pwd_rule;
    private String createAuthKey;
    public static final String pattern_pwd = "^[A-Za-z[0-9]]{4,8}$";
    public static final String pattern_email = "/^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i";

    Calendar myCalendar = Calendar.getInstance();

    DatePickerDialog.OnDateSetListener myDatePicker = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, month);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        join_email =(EditText)findViewById(R.id.join_email);
        //join_email_edit =(EditText)findViewById(R.id.join_email_edit);
        join_pwd =(EditText)findViewById(R.id.join_pwd);
        join_pwd_chk =(EditText)findViewById(R.id.join_pwd_chk);
        join_pwd_chk.setEnabled(false);
        join_nickname =(EditText)findViewById(R.id.join_nickname);

        join_birth =(EditText)findViewById(R.id.join_birth);
        join_fam_code =(EditText)findViewById(R.id.join_fam_code);

        //join_email_send = (Button)findViewById(R.id.join_email_send);
        //join_email_permit = (Button)findViewById(R.id.join_email_permit);
        //join_profic_button = (Button)findViewById(R.id.join_profic_button);
        join_birth_calendar = (Button)findViewById(R.id.join_birth_calendar);
        join_origin_code = (Button)findViewById(R.id.join_origin_code);
        join_new_code = (Button)findViewById(R.id.join_new_code);

        join_pwd_rule = (TextView)findViewById(R.id.join_pwd_rule);

//        join_email_send.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                createAuthKey=createAuthKey();
//                Intent email = new Intent(Intent.ACTION_SEND);
//                email.setType("plain/text");
//                //tring[] address = {join_email};
//                email.putExtra(Intent.EXTRA_EMAIL, join_email.getText().toString());
//                email.putExtra(Intent.EXTRA_SUBJECT, "[KnotKnot] 인증 메일입니다.");
//                email.putExtra(Intent.EXTRA_TEXT, "이메일 인증을 완료해주세요.\n"
//                                        +"KnotKnot의 회원이 되시는 걸 진심으로 환영합니다\n"
//                                        +"앞으로 KnotKnot를 통하여 보다 화목한 가정되시기를 기원합니다\n"
//                                        +"아래의 인증번호를 앱에 입력해주세요\n"
//                                        +"인증번호 : "+createAuthKey+"\n"+"감사합니다.");
//                startActivity(email);
//            }
//        });

//        join_email_permit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String input_key=join_email_edit.getText().toString();
//
//                if (input_key.equals(createAuthKey)){
//                    join_email_edit.setText("인증되었습니다");
//                    join_email_permit.setEnabled(false);
//                }else{
//                    join_email_edit.setText("");
//                    join_email_edit.setHint("인증 번호를 입력하세요");
//                    Toast.makeText(getApplicationContext(),"인증번호가 다릅니다",Toast.LENGTH_LONG).show();
//                }
//            }
//        });

//        join_profic_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//                intent.setType("image/*");
//                startActivityForResult(intent,10);
//            }
//        });

        join_email.addTextChangedListener(new TextWatcher() {
            Boolean chk;
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }

        });

        join_pwd.addTextChangedListener(new TextWatcher() {
            Boolean chk;
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                chk = join_pwd.getText().toString().matches(pattern_pwd);
                if(!chk){
                    join_pwd_chk.setEnabled(false);
                    join_pwd_rule.setTextColor(getColor(R.color.design_default_color_error));
                }else{
                    join_pwd_chk.setEnabled(true);
                    join_pwd_rule.setTextColor(getColor(R.color.colorLightGray));
                }
            }

        });

        join_pwd_chk.addTextChangedListener(new TextWatcher() {
            Boolean chk;

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

//                chk = join_pwd_chk.getText().toString().equals(join_pwd.getText().toString());
//                if(!chk){
//                    join_pwd_chk.setTextColor(getColor(R.color.design_default_color_error));
//                }else{
//                    join_pwd_chk.setTextColor(getColor(R.color.colorLightGray));
//                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                chk = join_pwd.getText().toString().matches(pattern_pwd);
                if(!chk){
                    join_pwd_chk.setEnabled(false);
                    join_pwd_rule.setTextColor(getColor(R.color.design_default_color_error));
                }else{
                    join_pwd_chk.setEnabled(true);
                    join_pwd_rule.setTextColor(getColor(R.color.colorLightGray));
                }
            }

        });

        join_birth_calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(JoinActivity.this, myDatePicker,
                        myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        Boolean bJoin = join_email.getText()!=null && join_pwd.getText()!=null
                &&join_pwd_chk.getText()!=null&&join_nickname.getText()!=null
                &&join_birth.getText()!=null;

        join_origin_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(bJoin&&join_fam_code.getText()!=null){
                    //..show gif
                    viewDialog.showDialog();

                    join(join_email.getText().toString(),
                            sha256ToString(join_pwd.getText().toString()),
                            join_nickname.getText().toString(),
                            join_birth.getText().toString(),
                            join_fam_code.getText().toString()
                    );
                }else {
                    //내용이 다 있지 않은 경우
                    Toast.makeText(getApplicationContext(), "*의 필수 내용을 모두 입력해주세요", Toast.LENGTH_SHORT).show();
                }
            }
        });

        join_new_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(bJoin){
                    //..show gif
                    viewDialog.showDialog();

                    join(join_email.getText().toString(),
                            sha256ToString(join_pwd.getText().toString()),
                            join_nickname.getText().toString(),
                            join_birth.getText().toString(),
                            ""
                    );
                 }else {
                    //내용이 다 있지 않은 경우
                    Toast.makeText(getApplicationContext(), "*의 필수 내용을 모두 입력해주세요", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void join(String email, String password, String nickname, String birth, String family_code) {
        retrofitClient = RetrofitClient.getInstance();
        retrofitInterface = RetrofitClient.getRetrofitInterface();

        // 회원가입 데이터 Body
        Map<String, Object> map = new HashMap<>();
        map.put("email", email);
        map.put("password", password);
        map.put("nickname", nickname);
        map.put("birth", birth);
        map.put("family_code", family_code);

        retrofitInterface.join(map).enqueue((new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                if(response.isSuccessful()) {
                    String message = response.body();
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();

                    Intent i = new Intent(getApplicationContext(),LoginActivity.class);
                    startActivity(i);
                } else {
                    try {
                        String message = response.errorBody().string();
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                viewDialog.hideDialog();
            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                Toast.makeText(getApplicationContext(),"오류가 발생했습니다.",Toast.LENGTH_SHORT).show();

                Log.d("retrofit", "Join failed");
                viewDialog.hideDialog();
            }
        }));
    }

    private void updateLabel() {
        String myFormat = "yyyy/MM/dd";    // 출력형식   2018/11/28
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.KOREA);
        join_birth.setText(sdf.format(myCalendar.getTime()));
    }
    private String createAuthKey(){
        Random random = new Random();
        int length = random.nextInt(4)+6;

        StringBuffer newWord = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int choice = random.nextInt(3);
            switch(choice) {
                case 0:
                    newWord.append((char)((int)random.nextInt(25)+97));
                    break;
                case 1:
                    newWord.append((char)((int)random.nextInt(25)+65));
                    break;
                case 2:
                    newWord.append((char)((int)random.nextInt(10)+48));
                    break;
                default:
                    break;
            }
        }
        //System.out.println("newWord = (" + newWord + "), length = " + length);
        return newWord.toString();
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