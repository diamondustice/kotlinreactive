package com.eiselle.kotlinreactive.dto


data class WeatherDto(
        private val daily: List<WeatherDay>
) {
    fun getDaily(): List<WeatherDay> {
        return daily
    }
}


