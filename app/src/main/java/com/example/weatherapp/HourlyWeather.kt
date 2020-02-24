package com.example.weatherapp


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.view.size
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.datasource.remote.OkHttpHelper
import com.example.weatherapp.model.Forecast.ForecastResults
import com.example.weatherapp.model.Forecast.X
import com.example.weatherapp.model.Weather.Weather
import com.example.weatherapp.view.adapter.WeatherAdapter
import kotlinx.android.synthetic.main.fragment_hourly_weather.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
class HourlyWeather : Fragment() {

    private var zipCode: String? = null
    var isCelsius = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            zipCode = it.getString(ZIP_CODE)
        }
        val weatherUrl = "https://api.openweathermap.org/data/2.5/forecast?zip=$zipCode,us&appid=$API_KEY"
        val okHttpHelper = OkHttpHelper()
        okHttpHelper.makeForecastWeatherApi(weatherUrl)

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onUserResponse(userResponse: ForecastResults)
    {
        if(isCelsius) {
            for (i in 0 until userResponse.list.size) {
                userResponse.list[i].main.temp_max = (userResponse.list[i].main.temp_max - 273.15)
                userResponse.list[i].main.temp_min = (userResponse.list[i].main.temp_min - 273.15)
            }
            isCelsius = false
        }
        else
        {
            for (i in 0 until userResponse.list.size) {
                userResponse.list[i].main.temp_max = (userResponse.list[i].main.temp_max - 273.15) * 9/5 + 32
                userResponse.list[i].main.temp_min = (userResponse.list[i].main.temp_min - 273.15) * 9/5 + 32
            }
            isCelsius = true
        }
        rtForecastList.layoutManager = LinearLayoutManager(this.context, RecyclerView.HORIZONTAL,false)
        val itemDecoration = DividerItemDecoration(this.context, DividerItemDecoration.HORIZONTAL)
        rtForecastList.addItemDecoration((itemDecoration))
        rtForecastList.adapter = WeatherAdapter(userResponse.list)
        (rtForecastList.adapter as WeatherAdapter).updateList()
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
        return inflater.inflate(R.layout.fragment_hourly_weather, container, false)
    }

    fun changeMetric()
    {
        val weatherUrl = "https://api.openweathermap.org/data/2.5/forecast?zip=$zipCode,us&appid=$API_KEY"
        val okHttpHelper = OkHttpHelper()
        okHttpHelper.makeForecastWeatherApi(weatherUrl)
    }


    companion object {
        @JvmStatic
        fun newInstance(param1: String) =
            HourlyWeather().apply {
                arguments = Bundle().apply {
                    putString(ZIP_CODE, param1)
                }
            }
    }
}
