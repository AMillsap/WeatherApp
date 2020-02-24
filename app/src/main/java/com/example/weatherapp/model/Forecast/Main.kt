package com.example.weatherapp.model.Forecast

data class Main(
    val feels_like: Double,
    val grnd_level: Int,
    val humidity: Int,
    val pressure: Int,
    val sea_level: Int,
    val temp: Double,
    val temp_kf: Double,
    var temp_max: Double,
    var temp_min: Double
)