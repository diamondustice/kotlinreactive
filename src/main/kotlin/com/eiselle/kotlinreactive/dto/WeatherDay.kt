package com.eiselle.kotlinreactive.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class WeatherDay(
        private val day_name: String,
        private val temp_high_celsius: String,
        private val forecast_blurp: String
) {
    @JsonProperty("day_name")
    fun getDayName(): String {
        return day_name
    }

    @JsonProperty("temp_high_celsius")
    fun getTempHighCelsius(): String {
        return temp_high_celsius
    }

    @JsonProperty("forecast_blurp")
    fun getForecastBlurp(): String {
        return forecast_blurp
    }
}