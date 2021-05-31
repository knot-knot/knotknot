package com.cau.knotknot;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Comments {
    @SerializedName("comments_id")
    @Expose
    private Integer commentsId;
    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("writer")
    @Expose
    private String writer;
    @SerializedName("user.nickname")
    @Expose
    private String userNickname;

    public Comments(Integer commentsId, String content, String createdAt, String userNickname, String writer) {
        super();
        this.commentsId = commentsId;
        this.content = content;
        this.createdAt = createdAt;
        this.writer = writer;
        this.userNickname = userNickname;
    }

    public Integer getCommentsId() {
        return commentsId;
    }

    public void setCommentsId(Integer commentsId) {
        this.commentsId = commentsId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
        sb.append("commentsId");
        sb.append('=');
        sb.append(((this.commentsId == null) ? "<null>" : this.commentsId));
        sb.append(',');
        sb.append("content");
        sb.append('=');
        sb.append(((this.content == null) ? "<null>" : this.content));
        sb.append(',');
        sb.append("createdAt");
        sb.append('=');
        sb.append(((this.createdAt == null) ? "<null>" : this.createdAt));
        sb.append(',');
        sb.append("userNickname");
        sb.append('=');
        sb.append(((this.userNickname == null) ? "<null>" : this.userNickname));
        sb.append(',');
        if (sb.charAt((sb.length() - 1)) == ',') {
            sb.setCharAt((sb.length() - 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }
}
