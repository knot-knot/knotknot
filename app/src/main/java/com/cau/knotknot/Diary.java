package com.cau.knotknot;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Diary {

    @SerializedName("diary_id")
    @Expose
    private Integer diaryId;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("emotion")
    @Expose
    private Integer emotion;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("writer")
    @Expose
    private String writer;
    @SerializedName("commentsCount")
    @Expose
    private Integer commentsCount;
    @SerializedName("user.nickname")
    @Expose
    private String userNickname;


    public Diary(Integer diaryId, String description, Integer emotion, String createdAt, String writer, Integer commentsCount, String userNickname) {
        super();
        this.diaryId = diaryId;
        this.description = description;
        this.emotion = emotion;
        this.createdAt = createdAt;
        this.writer = writer;
        this.commentsCount = commentsCount;
        this.userNickname = userNickname;
    }

    public Integer getDiaryId() {
        return diaryId;
    }

    public void setDiaryId(Integer diaryId) {
        this.diaryId = diaryId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getEmotion() {
        return emotion;
    }

    public void setEmotion(Integer emotion) {
        this.emotion = emotion;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public Integer getCommentsCount() {
        return commentsCount;
    }

    public void setCommentsCount(Integer commentsCount) {
        this.commentsCount = commentsCount;
    }

    public String getUserNickname() {
        return userNickname;
    }

    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Diary.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("diaryId");
        sb.append('=');
        sb.append(((this.diaryId == null)?"<null>":this.diaryId));
        sb.append(',');
        sb.append("description");
        sb.append('=');
        sb.append(((this.description == null)?"<null>":this.description));
        sb.append(',');
        sb.append("emotion");
        sb.append('=');
        sb.append(((this.emotion == null)?"<null>":this.emotion));
        sb.append(',');
        sb.append("createdAt");
        sb.append('=');
        sb.append(((this.createdAt == null)?"<null>":this.createdAt));
        sb.append(',');
        sb.append("writer");
        sb.append('=');
        sb.append(((this.writer == null)?"<null>":this.writer));
        sb.append(',');
        sb.append("commentsCount");
        sb.append('=');
        sb.append(((this.commentsCount == null)?"<null>":this.commentsCount));
        sb.append(',');
        sb.append("userNickname");
        sb.append('=');
        sb.append(((this.userNickname == null)?"<null>":this.userNickname));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

}