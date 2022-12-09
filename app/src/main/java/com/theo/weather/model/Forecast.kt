package com.theo.weather.model


data class Forecast(
    //城市名称
    val city : String,
    //省份名称
    val province : String,
    //预报天气
    val casts : List<Casts>,
)