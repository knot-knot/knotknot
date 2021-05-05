package com.cau.knotknot;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitInterface {

    @GET("diary/{username}")
    Call<List<Diary>> getDiary(@Path("username") String username, @Query("date") String date);

    @POST("diary")
    @FormUrlEncoded
    Call<Void> createDiary(@FieldMap Map<String, Object> fields);

}
