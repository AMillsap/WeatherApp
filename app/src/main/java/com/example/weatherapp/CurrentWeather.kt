package com.example.weatherapp


import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.weatherapp.datasource.remote.OkHttpHelper
import com.example.weatherapp.model.Weather.Weather
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_current_weather.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class CurrentWeather : Fragment() {
    private var zipCode: String? = null
    var isCelsius = true
    var celsiusVale = 0
    var fahrenheitValue = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            zipCode = it.getString(ZIP_CODE)
        }

        val weatherUrl = "https://api.openweathermap.org/data/2.5/weather?zip=$zipCode,us&appid=$API_KEY"
        val okHttpHelper = OkHttpHelper()
        okHttpHelper.makeCurrentWeatherApi(weatherUrl)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onUserResponse(userResponse: Weather)
    {
        celsiusVale = (userResponse.main.temp - 273.15).toInt()
        fahrenheitValue = ((userResponse.main.temp - 273.15) * (9/5) + 32).toInt()
        tvCurrentWeather.text = "${userResponse.name}"
        tvCurrentTemp.text = celsiusVale.toString() + " °C"
        if(isCelsius)
        {
            if((userResponse.main.temp - 273.15).toInt() > 15.5)
            {
                view?.setBackgroundColor(resources.getColor(R.color.warmTemp))
            }
            else
            {
                view?.setBackgroundColor(resources.getColor(R.color.coolTemp))
            }
        }
        else
        {
            if((userResponse.main.temp - 273.15).toInt() > 60)
            {
                view?.setBackgroundColor(resources.getColor(R.color.warmTemp))
            }
            else
            {
                view?.setBackgroundColor(resources.getColor(R.color.coolTemp))
            }
        }
        when (userResponse.weather[0].main)
        {
            "Clear" -> {
                ivWeatherImage.setImageResource(R.drawable.weather_sunny)
            }
            "Rain" -> {
                ivWeatherImage.setImageResource(R.drawable.weather_rain)
            }
            "Drizzle" -> {
                ivWeatherImage.setImageResource(R.drawable.weather_rain)
            }
            "Snow" -> {
                ivWeatherImage.setImageResource(R.drawable.weather_snow)
            }
            "Thunderstorm" -> {
                ivWeatherImage.setImageResource(R.drawable.weather_strom)
            }
            "Clouds" -> {
                ivWeatherImage.setImageResource(R.drawable.weather_cloudy)
            }
            else -> {
                ivWeatherImage.setImageResource(R.drawable.weather_hazzard)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_current_weather, container, false)
    }

    fun changeText()
    {
        if(isCelsius)
        {
            tvCurrentTemp.text = fahrenheitValue.toString() + " °F"
            isCelsius = false
        }
        else
        {
            tvCurrentTemp.text = celsiusVale.toString() + " °C"
            isCelsius = true
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String) =
            CurrentWeather().apply {
                arguments = Bundle().apply {
                    putString(ZIP_CODE, param1)
                }
            }
    }
}
