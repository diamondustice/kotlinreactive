package com.eiselle.kotlinreactive.exception

class WeatherAPIException : RuntimeException {
    constructor(message: String, cause: Throwable) : super(message, cause)
    constructor(message: String) : super(message)
}