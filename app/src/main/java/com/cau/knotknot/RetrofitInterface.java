package com.cau.knotknot;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitInterface {

    //로그인
    @POST("login")
    @FormUrlEncoded
    Call<Void> login(@FieldMap Map<String, Object> fields);

    //회원가입
    @POST("register")
    @FormUrlEncoded
    Call<Void> register(@FieldMap Map<String, Object> fields);

    //로그아웃
    @GET("logout")
    Call<Void> logout();

    //일기 불러오기
    @GET("diary/{username}")
    Call<List<Diary>> getDiary(@Path("username") String username, @Query("date") String date);

    //일기 작성
    @POST("diary")
    @FormUrlEncoded
    Call<Void> createDiary(@FieldMap Map<String, Object> fields);

    //일기 수정
    @PUT("diary/{diaryId}")
    @FormUrlEncoded
    Call<Void> updateDiary(@Path("diaryId") int diaryId, @FieldMap Map<String, Object> fields);

    //일기 삭제
    @DELETE("diary/{diaryId}")
    Call<Void> deleteDiary(@Path("diaryId") int diaryId);

    //코멘트 불러오기 (일기 1개 당)
    @GET("comments/{diaryId}")
    Call<List<Comments>> getComments(@Path("diaryId") int diaryId);

    //코멘트 작성
    @POST("comments/{diaryId}")
    @FormUrlEncoded
    Call<Void> createComments(@FieldMap Map<String, Object> fields);

    //코멘트 수정
    @PUT("comments/{commentsId}")
    @FormUrlEncoded
    Call<Void> updateComments(@Path("commentsId") int commentsId, @FieldMap Map<String, Object> fields);

    //코멘트 삭제
    @DELETE("comments/{commentsId}")
    Call<Void> deleteComments(@Path("commentsId") int commentsId);

}
