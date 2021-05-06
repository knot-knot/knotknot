package com.cau.knotknot;

import android.graphics.drawable.Drawable;

public class ListViewItem {


    private Drawable emoticon;
    private Drawable useremo;
    private String user;
    private String description;
    private String createdAt;

    public void setIcon(Drawable icon) {
        emoticon = icon ;
    }
    public void setUseremo(Drawable icon) {
        useremo = icon ;
    }
    public void setUser(String username) {
        user=username ;
    }
    public void setDesc(String desc) {
        description = desc ;
    }
    public void setDate(String date) {
        createdAt = date ;
    }

    public Drawable getEmoticon() {
        return this.emoticon;
    }

    public Drawable getUseremo() {
        return this.useremo;
    }

    public String getUser() {
        return this.user;
    }

    public String getDesc() {
        return this.description;
    }

    public String getDate() {
        return createdAt;
    }
}
