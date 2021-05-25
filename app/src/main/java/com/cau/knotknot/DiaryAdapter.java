package com.cau.knotknot;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class DiaryAdapter extends BaseAdapter {
    // Adapter에 추가된 데이터를 저장하기 위한 ArrayList
    private ArrayList<ListViewItem> listViewItemList = new ArrayList<ListViewItem>() ;

    // ListViewAdapter의 생성자
    public DiaryAdapter() {

    }

    // Adapter에 사용되는 데이터의 개수를 리턴. : 필수 구현
    @Override
    public int getCount() {
        return listViewItemList.size() ;
    }

    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴. : 필수 구현
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;//현재 리스트뷰의 번호
        final Context context = parent.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_layout, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        ImageView emoticonView = (ImageView) convertView.findViewById(R.id.lv_emoticon) ;
        ImageView useremoView = (ImageView) convertView.findViewById(R.id.lv_useremo) ;
        TextView userView = (TextView) convertView.findViewById(R.id.lv_user) ;
        TextView descriptionView = (TextView) convertView.findViewById(R.id.lv_description) ;
        TextView dateView = (TextView) convertView.findViewById(R.id.lv_createdAt) ;

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        ListViewItem listViewItem = listViewItemList.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        emoticonView.setImageDrawable(listViewItem.getEmoticon());
        useremoView.setImageDrawable(listViewItem.getUseremo());
        userView.setText(listViewItem.getUser());
        descriptionView.setText(listViewItem.getDesc());
        dateView.setText(listViewItem.getDate());

        //final String text = items.get(position); -> pos
        Button lv_edit = (Button)convertView.findViewById(R.id.lv_edit);
        lv_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //수정버튼 클릭시
            }
        });
        Button lv_delete = (Button)convertView.findViewById(R.id.lv_delete);
        lv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //삭제버튼 클릭시
            }
        });
        return convertView;
    }

    // 지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴. : 필수 구현
    @Override
    public long getItemId(int position) {
        return position ;
    }

    // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position) ;
    }

    // 아이템 데이터 추가를 위한 함수. 개발자가 원하는대로 작성 가능.
    public void addItem(Drawable icon, Drawable useremo, String user, String desc, String createdAt) {
        ListViewItem item = new ListViewItem();

        item.setEmoticon(icon);
        item.setUseremo(useremo);
        item.setUser(user);
        item.setDesc(desc);
        item.setDate(createdAt);

        listViewItemList.add(item);
    }
}
