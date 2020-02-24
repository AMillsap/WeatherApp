package com.example.weatherapp.datasource.remote

import android.util.Log
import com.example.weatherapp.model.Forecast.ForecastResults
import com.example.weatherapp.model.Weather.Weather
import com.google.gson.Gson
import okhttp3.*
import org.greenrobot.eventbus.EventBus
import java.io.IOException

class OkHttpHelper
{
    private fun getClient() : OkHttpClient
    {
        val okHttpClient = OkHttpClient.Builder().build()
        return okHttpClient
    }

    fun makeCurrentWeatherApi(url : String)
    {
        val request = Request.Builder().url(url).build()
        getClient().newCall(request).enqueue(object  : Callback
        {
            override fun onResponse(call: Call, response: Response)
            {
                val json = response.body?.string()
                Log.d("TAG", json)
                val userResults = Gson().fromJson<Weather>(json, Weather::class.java)
                Log.d("TAG", userResults.toString())
                EventBus.getDefault().post(userResults)
            }

            override fun onFailure(call: Call, e: IOException)
            {
                Log.e("ERROR_TAG", "Error in makeAsyncApiCall ---->", e)
            }
        })
    }

    fun makeForecastWeatherApi(url : String)
    {
        val request = Request.Builder().url(url).build()
        getClient().newCall(request).enqueue(object  : Callback
        {
            override fun onResponse(call: Call, response: Response)
            {
                val json = response.body?.string()
                Log.d("TAG", json)
                val userResults = Gson().fromJson<ForecastResults>(json, ForecastResults::class.java)
                Log.d("TAG", userResults.toString())
                EventBus.getDefault().post(userResults)
            }

            override fun onFailure(call: Call, e: IOException)
            {
                Log.e("ERROR_TAG", "Error in makeAsyncApiCall ---->", e)
            }
        })
    }


}