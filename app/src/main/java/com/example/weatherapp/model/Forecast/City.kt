package com.example.weatherapp.model.Forecast

data class City(
    val coord: Coord,
    val country: String,
    val name: String,
    val sunrise: Int,
    val sunset: Int,
    val timezone: Int
)