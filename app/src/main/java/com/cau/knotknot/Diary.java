package com.cau.knotknot;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Diary {

    @SerializedName("diary_id")
    @Expose
    private Integer diaryId;
    @SerializedName("writer")
    @Expose
    private String writer;
    @SerializedName("img_link")
    @Expose
    private Object imgLink;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("emotion")
    @Expose
    private Integer emotion;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;

    public Integer getDiaryId() {
        return diaryId;
    }

    public void setDiaryId(Integer diaryId) {
        this.diaryId = diaryId;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public Object getImgLink() {
        return imgLink;
    }

    public void setImgLink(Object imgLink) {
        this.imgLink = imgLink;
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Diary.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("diaryId");
        sb.append('=');
        sb.append(((this.diaryId == null)?"<null>":this.diaryId));
        sb.append(',');
        sb.append("writer");
        sb.append('=');
        sb.append(((this.writer == null)?"<null>":this.writer));
        sb.append(',');
        sb.append("imgLink");
        sb.append('=');
        sb.append(((this.imgLink == null)?"<null>":this.imgLink));
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
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

}