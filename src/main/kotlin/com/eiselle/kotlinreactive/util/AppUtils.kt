package com.eiselle.kotlinreactive.util

import com.eiselle.kotlinreactive.dto.WeatherDay
import com.eiselle.kotlinreactive.model.Period
import java.time.LocalDate
import java.time.ZonedDateTime

object AppUtils {
    fun isPeriodToday(day: Period): Boolean {
        val date = ZonedDateTime.parse(day.getStartTime()).toLocalDate()
        return date.isEqual(LocalDate.now())
    }

    fun dayToDto(day: Period): WeatherDay {
        return WeatherDay(day.getName(), toCelsius(day.getTemperature()), day.getShortForecast())
    }

    private fun toCelsius(tempF: String): String {
        val fahrenheit = tempF.toDouble()
        val celsius = (5 * (fahrenheit - 32.0) / 9.0)
        return String.format("%.1f", celsius)
    }
}