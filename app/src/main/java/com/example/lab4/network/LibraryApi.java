package com.example.lab4.network;

import androidx.loader.content.CursorLoader;

import com.example.lab4.model.Book;
import com.google.gson.JsonObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface LibraryApi {

    @GET("/search.json")
    Call<JsonObject> getBooks(@Query("title") String title,
                              @Query("limit") Integer limit);


    @GET("/search.json")
    Call<JsonObject> getBookByKey(@Query("q") String key);

    @GET()
    Call<ResponseBody> getImageForBook(@Url String url);

}
