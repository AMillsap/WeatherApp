package com.example.weatherapp.view.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.model.Forecast.ForecastResults
import com.example.weatherapp.model.Forecast.X
import kotlinx.android.synthetic.main.hourly_weather_item.view.*

class WeatherAdapter(var forecastList: List<X>) : RecyclerView.Adapter<WeatherAdapter.ViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder((LayoutInflater.from(parent.context).inflate(R.layout.hourly_weather_item, parent, false)))

    override fun getItemCount(): Int = forecastList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {
        holder.populateItem(forecastList[position])
    }

    fun updateList()
    {
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView)
    {
        fun populateItem(forecast : X)
        {
            var hour = forecast.dt_txt.split(" ")
            itemView.tvHour.text = hour[0] + "\n" + hour[1]
            itemView.tvTempHigh.text = forecast.main.temp_max.toInt().toString()
            itemView.tvTempLow.text = forecast.main.temp_min.toInt().toString()
            when (forecast.weather[0].main)
            {
                "Clear" -> {
                    itemView.imgWeatherForHour.setImageResource(R.drawable.weather_sunny)
                }
                "Rain", "Drizzle" -> {
                    itemView.imgWeatherForHour.setImageResource(R.drawable.weather_rain)
                }
                "Snow" -> {
                    itemView.imgWeatherForHour.setImageResource(R.drawable.weather_snow)
                }
                "Thunderstorm" -> {
                    itemView.imgWeatherForHour.setImageResource(R.drawable.weather_strom)
                }
                "Clouds" -> {
                    itemView.imgWeatherForHour.setImageResource(R.drawable.weather_cloudy)
                }
                else -> {
                    itemView.imgWeatherForHour.setImageResource(R.drawable.weather_hazzard)
                }
            }
        }

    }
}