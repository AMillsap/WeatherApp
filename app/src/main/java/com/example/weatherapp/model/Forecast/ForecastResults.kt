package com.example.weatherapp.model.Forecast

data class ForecastResults(
    val city: City,
    val cnt: Int,
    val cod: String,
    val list: List<X>,
    val message: Int
)