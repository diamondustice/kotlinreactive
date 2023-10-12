package com.eiselle.kotlinreactive.model

data class Weather(
        private val properties: Properties
) {
    fun getProperties(): Properties {
        return properties
    }
}

