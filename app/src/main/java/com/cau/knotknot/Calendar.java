package com.cau.knotknot;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Calendar {
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("user_count_total")
    @Expose
    private Integer userCountTotal;
    @SerializedName("user_count_diary")
    @Expose
    private Integer userCountDiary;
    @SerializedName("user_count_comments")
    @Expose
    private Integer userCountComments;


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getUserCountTotal() {
        return userCountTotal;
    }

    public void setUserCountTotal(Integer userCountTotal) {
        this.userCountTotal = userCountTotal;
    }

    public Integer getUserCountDiary() {
        return userCountDiary;
    }

    public void setUserCountDiary(Integer userCountDiary) {
        this.userCountDiary = userCountDiary;
    }

    public Integer getUserCountComments() {
        return userCountComments;
    }

    public void setUserCountComments(Integer userCountComments) {
        this.userCountComments = userCountComments;
    }

}
