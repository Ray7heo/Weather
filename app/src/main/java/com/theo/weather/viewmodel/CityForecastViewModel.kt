package com.theo.weather.viewmodel

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.google.gson.Gson
import com.theo.weather.R
import com.theo.weather.model.Body
import com.theo.weather.model.Forecast
import com.theo.weather.model.Live
import com.theo.weather.network.VolleySingleton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CityForecastViewModel(application: Application) : AndroidViewModel(application)
{

    //预报信息
    private val _forecasts =
        MutableLiveData<Forecast>()
    val forecasts get() = _forecasts

    //实时信息
    private val _lives =
        MutableLiveData<Live>()
    val lives get() = _lives

    @SuppressLint("StaticFieldLeak")
    val applicationContext: Context = application.applicationContext

    //位置信息
    private lateinit var locationClient: AMapLocationClient
    private lateinit var locationOption: AMapLocationClientOption

    //网络队列
    private val queue =
        VolleySingleton.getInstance(applicationContext).requestQueue

    companion object
    {
        //天气对应图片
        val weatherIcon = mapOf(
            "晴" to R.drawable.clear,
            "少云" to R.drawable.cloudy,
            "晴间多云" to R.drawable.cloudy,
            "多云" to R.drawable.shade,
            "阴" to R.drawable.shade,
            "阵雨" to R.drawable.shower,
            "雷阵雨" to R.drawable.thunder_shower,
            "小雨" to R.drawable.light_rain,
            "中雨" to R.drawable.moderate_rain,
            "大雨" to R.drawable.heavy_rain,
            "暴雨" to R.drawable.heavy_rain,
            "大暴雨" to R.drawable.heavy_rain,
            "特大暴雨" to R.drawable.heavy_rain,
        )
    }

    init
    {
        locate()
    }

    //定位后获取当地天气
    fun locate()
    {
        //开始定位
        AMapLocationClient.updatePrivacyShow(applicationContext, true, true)
        AMapLocationClient.updatePrivacyAgree(applicationContext, true)
        locationClient = AMapLocationClient(applicationContext)
        //定位回调
        locationClient.setLocationListener {
            //成功定位后获取天气
            if (it.adCode != "")
            {
                loadWeather(it.adCode)
            }
        }
        //设置场景,自动配置定位
        locationOption = AMapLocationClientOption()
        locationOption.locationPurpose = AMapLocationClientOption.AMapLocationPurpose.SignIn
        locationClient.setLocationOption(locationOption)
        locationClient.startLocation()

    }

    //加载地区天气
    private fun loadWeather(adCode: String)
    {
        val urlAll =
            "https://restapi.amap.com/v3/weather/weatherInfo?city=${adCode}&key=e93b697a37ceb83b5eaab3aff2540ee6&extensions=all"
        val urlBase =
            "https://restapi.amap.com/v3/weather/weatherInfo?city=${adCode}&key=e93b697a37ceb83b5eaab3aff2540ee6&extensions=base"
        //使用协程进行后台请求
        viewModelScope.launch(Dispatchers.IO)
        {
            //预测天气请求
            val requestAll = StringRequest(Request.Method.GET, urlAll,
                {
                    _forecasts.value = Gson().fromJson(it, Body::class.java).forecasts[0]
                },
                {

                })
            //实时天气请求
            val requestBase = StringRequest(Request.Method.GET, urlBase,
                {
                    _lives.value = Gson().fromJson(it, Body::class.java).lives[0]
                },
                {

                })
            queue.add(requestBase)
            queue.add(requestAll)
        }

    }
}