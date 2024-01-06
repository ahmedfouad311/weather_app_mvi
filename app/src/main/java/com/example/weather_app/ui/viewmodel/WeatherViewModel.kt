package com.example.weather_app.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather_app.ui.viewstate.WeatherViewState
import com.example.weather_app.ui.intent.WeatherIntent
import com.example.weather_app.utils.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.awaitResponse

class WeatherViewModel : ViewModel() {
    private val _weatherState = MutableLiveData<WeatherViewState>()
    val weatherState: LiveData<WeatherViewState> get() = _weatherState

    fun handleIntent(intent: WeatherIntent, cityName: String, apiKey: String) {
        when (intent) {
            WeatherIntent.FetchItems -> getWeather(cityName, apiKey)
        }
    }

    private fun getWeather(cityName: String, apiKey: String) {
        viewModelScope.launch {
            _weatherState.value = WeatherViewState.Loading
            try {
                val response = RetrofitInstance.api.getWeather(cityName, apiKey).awaitResponse()
                Log.d("WeatherResponse", response.toString()) // Log the response
                if (response.isSuccessful) {
                    val temperature = response.body()?.main?.temp ?: 0.0
                    _weatherState.value = WeatherViewState.Success(temperature)
                } else {
                    _weatherState.value = WeatherViewState.Error("Error fetching weather")
                }
            } catch (e: Exception) {
                Log.e("WeatherError", e.message ?: "Unknown error") // Log the error
                _weatherState.value = WeatherViewState.Error("Network error: ${e.message}")
            }
        }
    }
}