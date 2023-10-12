package com.eiselle.kotlinreactive.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class WeatherDay(
        private val dayName: String,
        private val tempHighCelsius: String,
        private val forecastBlurp: String
) {
    @JsonProperty("day_name")
    fun getDayName(): String {
        return dayName
    }

    @JsonProperty("temp_high_celsius")
    fun getTempHighCelsius(): String {
        return tempHighCelsius
    }

    @JsonProperty("forecast_blurp")
    fun getForecastBlurp(): String {
        return forecastBlurp
    }
}