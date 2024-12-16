package com.mbahrami.foodapp.data.server

import com.mbahrami.foodapp.data.model.ResponseCategoriesList
import com.mbahrami.foodapp.data.model.ResponseFoodsList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("random.php")
    suspend fun randomFood(): Response<ResponseFoodsList>

    @GET("categories.php")
    suspend fun categoriesList(): Response<ResponseCategoriesList>

    @GET("search.php")
    suspend fun foodsList(@Query("f") letter: String): Response<ResponseFoodsList>

    @GET("search.php")
    suspend fun searchFood(@Query("s") letter: String): Response<ResponseFoodsList>

    @GET("filter.php")
    suspend fun foodsByCategory(@Query("c") letter: String): Response<ResponseFoodsList>

    @GET("lookup.php")
    suspend fun foodDetail(@Query("i") id: Int): Response<ResponseFoodsList>
}