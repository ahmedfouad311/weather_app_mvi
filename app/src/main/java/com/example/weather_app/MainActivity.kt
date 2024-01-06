package com.example.weather_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.weather_app.ui.intent.WeatherIntent
import com.example.weather_app.ui.viewmodel.WeatherViewModel
import com.example.weather_app.ui.viewstate.WeatherViewState

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: WeatherViewModel
    private lateinit var textView: TextView
    private val apiKey: String = "4ecbc5f10b8546146d057b25c4f2d0cb"
    var city: String = "Cairo"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textView = findViewById(R.id.degree_TV)

        viewModel = ViewModelProvider(this).get(WeatherViewModel::class.java)

        // Observe the weatherState LiveData
        viewModel.weatherState.observe(this, Observer { state ->
            render(state)
        })

        // Trigger API call
        viewModel.handleIntent(WeatherIntent.FetchItems, cityName = city, apiKey = apiKey)
    }

    private fun render(state: WeatherViewState) {
        when (state) {
            is WeatherViewState.Loading -> showLoading()
            is WeatherViewState.Success -> showWeather(state.temperature)
            is WeatherViewState.Error -> showError(state.errorMessage)
        }
    }

    private fun showWeather(temperature: Double) {
        val degreeTextView: TextView = findViewById(R.id.degree_TV)
        degreeTextView.text = String.format("%.2f °C", temperature)
    }

    private fun showLoading() {
        // Update UI to show loading state
        Log.d("Loading State", "Loading")
    }


    private fun showError(errorMessage: String) {
        // Update UI to show error state
        Log.e("Error State", errorMessage)
    }
}