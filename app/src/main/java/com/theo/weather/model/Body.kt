package com.theo.weather.model


data class Body(
    val status: String,
    //预报数据
    val forecasts: List<Forecast>,
    //实时数据
    val lives : List<Live>,
    //额外信息
    val result : Additional
)
