package com.eiselle.kotlinreactive.model

data class Period(
        private val number: Int,
        private val name: String,
        private val temperature: String,
        private val temperatureUnit: String,
        private val shortForecast: String,
        private val isDaytime: String? = null,
        private val startTime: String,
        private val endTime: String
) {
    fun getNumber(): Int {
        return number
    }

    fun getName(): String {
        return name
    }

    fun getTemperature(): String {
        return temperature
    }

    fun getTemperatureUnit(): String {
        return temperatureUnit
    }

    fun getShortForecast(): String {
        return shortForecast
    }

    fun getIsDaytime(): String? {
        return isDaytime
    }

    fun getStartTime(): String {
        return startTime
    }

    fun getEndTime(): String {
        return endTime
    }
}