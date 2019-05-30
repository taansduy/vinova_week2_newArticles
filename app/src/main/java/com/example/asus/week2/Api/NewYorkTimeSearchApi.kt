package com.example.asus.week2.Api

import com.example.asus.week2.Model.Response
import com.example.asus.week2.Model.Result
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewYorkTimeSearchApi {
    @GET("articlesearch.json")
    fun doQuery(@Query("q") query: String,@Query("page") page: Int ): Call<Result>
    @GET("articlesearch.json")
    fun doQueryFilterDate(@Query("q") query: String,@Query("begin_date") date: String,@Query("page") page: Int ): Call<Result>
    @GET("articlesearch.json")
    fun doQueryFilterSort(@Query("q") query: String,@Query("sort") type: String,@Query("page") page: Int ): Call<Result>
    @GET("articlesearch.json")
    fun doQueryFilterNDesk(@Query("q") query: String,@Query("fq") newDesk: String,@Query("page") page: Int ): Call<Result>
    @GET("articlesearch.json")
    fun doQueryFilterDateandSort(@Query("q") query: String,@Query("begin_date") date: String,@Query("sort") type: String,@Query("page") page: Int ): Call<Result>
    @GET("articlesearch.json")
    fun doQueryFilterDateandNDesk(@Query("q") query: String,@Query("begin_date") date: String,@Query("fq") newDesk: String,@Query("page") page: Int ): Call<Result>
    @GET("articlesearch.json")
    fun doQueryFilterSortandNDesk(@Query("q") query: String,@Query("sort") type: String,@Query("fq") newDesk: String,@Query("page") page: Int ): Call<Result>
    @GET("articlesearch.json")
    fun doQueryFilterAll(@Query("q") query: String,@Query("begin_date") date: String,@Query("sort") type: String,@Query("fq") fq: String,@Query("page") page: Int ): Call<Result>
    @GET("articlesearch.json")
    fun doQueryFilterSort(@Query("sort") type: String,@Query("page") page: Int ): Call<Result>
}