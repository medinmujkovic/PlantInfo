package com.example.rma24projekat_19219.data.api

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TrefleApiService {
    @GET("species/search")
    suspend fun getPlantsByFlowerColor(
        @Query("filter[flower_color]") flowerColor: String,
        @Query("q") query: String,
        @Query("token") apiKey: String = "WRsQtW2Qqa4PJm0-TE1QsLYxKqWFa286xG-tj-WdKL0"
    ): TrefleResponse

    @GET("species/{id}")
    suspend fun getPlantById(
        @Path("id") id: String,
        @Query("token") apiKey: String = "WRsQtW2Qqa4PJm0-TE1QsLYxKqWFa286xG-tj-WdKL0"
    ): TrefleResponse2
}