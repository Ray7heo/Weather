package com.theo.weather.model

import com.google.gson.annotations.SerializedName

data class Live(
    val weather: String,
    val temperature: String,
    @SerializedName("winddirection") val windDirection: String,
    @SerializedName("windpower")val windPower : String,
    val humidity : String
)
