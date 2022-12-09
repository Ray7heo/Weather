package com.theo.weather.model

import com.google.gson.annotations.SerializedName

data class Casts(
    val date : String,
    val week : String,
    //白天天气现象
    @SerializedName("dayweather")val dayWeather : String,
    //晚上天气现象
    @SerializedName("nightweather")val nightWeather : String,
    //白天温度
    @SerializedName("daytemp")val dayTemp : String,
    //晚上温度
    @SerializedName("nighttemp")val nightTemp : String,
)