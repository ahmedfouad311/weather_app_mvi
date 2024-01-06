package com.example.weather_app.data.api

import com.example.weather_app.data.model.ResponseModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("weather")
    fun getWeather(@Query("q") cityName: String,
                   @Query("appid") apiKey: String): Call<ResponseModel>
}