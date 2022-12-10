package com.theo.weather.model

import com.google.gson.annotations.SerializedName

data class Additional(val daily: Daily)

data class Daily(
    @SerializedName("life_index") val lifeIndex: LifeIndex,
    val precipitation: List<Precipitation>
)

data class LifeIndex(
    val ultraviolet: List<Ultraviolet>,
    val carWashing: List<CarWashing>,
    val dressing: List<Dressing>,
    val comfort: List<Comfort>,
    val coldRisk: List<ColdRisk>
)

//紫外线
data class Ultraviolet(val desc: String)

//洗车指数
data class CarWashing(val desc: String)

//穿衣指数
data class Dressing(val desc: String)

//舒适指数
data class Comfort(val desc: String)

//感冒指数
data class ColdRisk(val desc: String)

//降水数据
data class Precipitation(val avg: String)