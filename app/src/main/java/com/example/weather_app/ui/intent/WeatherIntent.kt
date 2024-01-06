package com.example.weather_app.ui.intent

sealed class WeatherIntent {
    object FetchItems : WeatherIntent()
}
