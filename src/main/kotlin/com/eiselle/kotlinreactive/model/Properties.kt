package com.eiselle.kotlinreactive.model

data class Properties(
        private val periods: List<Period>
) {
    fun getPeriods(): List<Period> {
        return periods
    }
}