package com.example.weather_app.ui.viewstate

sealed class WeatherViewState{
    object Loading : WeatherViewState()
    data class Success(val temperature: Double) : WeatherViewState()
    data class Error(val errorMessage: String) : WeatherViewState()
}
