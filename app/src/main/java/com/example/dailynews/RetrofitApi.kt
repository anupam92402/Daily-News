package com.example.dailynews

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface RetrofitApi {
    @GET
    fun getAllNews(@Url url:String): Call<NewsModal>

    @GET
    fun getNewsByCategory(@Url url:String): Call<NewsModal>

}