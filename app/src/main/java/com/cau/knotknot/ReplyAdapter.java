package com.cau.knotknot;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReplyAdapter extends BaseAdapter {
    // Adapter에 추가된 데이터를 저장하기 위한 ArrayList
    private ArrayList<ListViewItem> replyItemList = new ArrayList<ListViewItem>() ;

    private RetrofitClient retrofitClient = RetrofitClient.getInstance();
    private RetrofitInterface retrofitInterface = RetrofitClient.getRetrofitInterface();

    // ListViewAdapter의 생성자
    public ReplyAdapter() {

    }

    // Adapter에 사용되는 데이터의 개수를 리턴. : 필수 구현
    @Override
    public int getCount() {
        return replyItemList.size() ;
    }

    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴. : 필수 구현
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;//현재 리스트뷰의 번호
        final Context context = parent.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.reply_layout, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득

        TextView userView = (TextView) convertView.findViewById(R.id.rl_user) ;
        TextView descriptionView = (TextView) convertView.findViewById(R.id.rl_description) ;

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        ListViewItem listViewItem = replyItemList.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        userView.setText(listViewItem.getNickname());
        descriptionView.setText(listViewItem.getDesc());

        //final String text = items.get(position); -> pos
        Button rl_delete = (Button)convertView.findViewById(R.id.rl_delete);
        rl_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //삭제버튼 클릭시

                /*int diaryId = listViewItem.getDiaryId();

                retrofitInterface.deleteDiary(diaryId).enqueue((new Callback<String>() {
                    @Override
                    public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                        Log.d("retrofit", "Diary delete success");
//                        Toast.makeText(getApplicationContext(),"삭제되었습니다.",Toast.LENGTH_SHORT).show();
//
//                        Intent i = new Intent(getApplicationContext(),DiaryActivity.class);
//                        startActivity(i);
                    }

                    @Override
                    public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                        Log.d("retrofit", "Diary delete failed");
                    }
                }
                ));*/



            }
        });
        return convertView;

//        TextView lv_description = (TextView)convertView.findViewById(R.id.lv_description);
//        lv_description.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(get,DiaryActivity.class);
//                startActivity(i);
//            }
//        });
    }



    // 지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴. : 필수 구현
    @Override
    public long getItemId(int position) {
        return position ;
    }

    // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
    @Override
    public Object getItem(int position) {
        return replyItemList.get(position) ;
    }

    // 아이템 데이터 추가를 위한 함수. 개발자가 원하는대로 작성 가능.
    public void addItem(String nickname, String desc, String createdAt, int commentsId, String email) {
        ListViewItem item = new ListViewItem();

        item.setNickname(nickname);
        item.setDesc(desc);
        item.setDate(createdAt);
        item.setPrimaryKey(commentsId);
        item.setEmail(email);

        replyItemList.add(item);
    }
}
