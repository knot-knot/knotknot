package com.cau.knotknot;

import android.graphics.drawable.Drawable;

public class ListViewItem {


    private Drawable emoticon;
    private Drawable useremo;
    private String nickname;
    private String description;
    private String createdAt;
    private int commentsCount;
    private int primaryKey;
    private String email;

    public void setEmoticon(Drawable icon) {
        emoticon = icon ;
    }
    public void setUseremo(Drawable icon) {
        useremo = icon ;
    }
    public void setNickname(String nick) {
        nickname=nick ;
    }
    public void setDesc(String desc) {
        description = desc ;
    }
    public void setDate(String date) {
        createdAt = date ;
    }
    public void setCommentsCount(int num) {
        commentsCount = num ;
    }
    public void setPrimaryKey(int num) {
        primaryKey = num ;
    }
    public void setEmail(String writer) {
        email = writer ;
    }

    public Drawable getEmoticon() {
        return this.emoticon;
    }

    public Drawable getUseremo() {
        return this.useremo;
    }

    public String getNickname() {
        return this.nickname;
    }

    public String getDesc() {
        return this.description;
    }

    public String getDate() {
        return createdAt;
    }

    public int getCommentsCount() {
        return this.commentsCount;
    }

    public int getPrimaryKey() {
        return this.primaryKey;
    }

    public String getEmail() {
        return this.email;
    }
}
