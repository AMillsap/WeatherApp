package com.example.weatherapp.model.Forecast

data class Weather(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
)