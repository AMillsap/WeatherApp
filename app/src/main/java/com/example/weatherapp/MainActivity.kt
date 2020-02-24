package com.example.weatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.FragmentManager
import kotlinx.android.synthetic.main.activity_main.*

class
MainActivity : AppCompatActivity()
{
    var userZip = ""
    var previousZip = ""
    var currentWeather = CurrentWeather()
    var hourlyWeather = HourlyWeather()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    fun onClick(view: View)
    {
        previousZip = userZip
        userZip = etEnterZipCode.text.toString()
        when(view.id)
        {
            R.id.btnFindWeather ->
            {
                if(previousZip == "")
                {
                    currentWeather = CurrentWeather.newInstance(userZip)
                    supportFragmentManager
                        .beginTransaction()
                        .add(R.id.frmFragmentCurrentWeather, currentWeather)
                        .addToBackStack("CURRENT_WEATHER")
                        .commit()
                    hourlyWeather = HourlyWeather.newInstance(userZip)
                    supportFragmentManager
                        .beginTransaction()
                        .add(R.id.frmFragmentHourlyWeather, hourlyWeather)
                        .addToBackStack("HOURLY_WEATHER")
                        .commit()
                }
                else
                {
                    supportFragmentManager.popBackStack("CURRENT_WEATHER",FragmentManager.POP_BACK_STACK_INCLUSIVE)
                    currentWeather = CurrentWeather.newInstance(userZip)
                    supportFragmentManager
                        .beginTransaction()
                        .add(R.id.frmFragmentCurrentWeather, currentWeather)
                        .addToBackStack("CURRENT_WEATHER")
                        .commit()
                    hourlyWeather = HourlyWeather.newInstance(userZip)
                    supportFragmentManager
                        .beginTransaction()
                        .add(R.id.frmFragmentHourlyWeather, hourlyWeather)
                        .addToBackStack("HOURLY_WEATHER")
                        .commit()
                }
            }
            R.id.btnChangeMetric ->
            {
                currentWeather.changeText()
                hourlyWeather.changeMetric()
            }
        }
    }

}
