package com.eiselle.kotlinreactive.controller

import com.eiselle.kotlinreactive.dto.WeatherDto
import com.eiselle.kotlinreactive.service.WeatherForecastService
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping(value = ["/gridpoints"])
class WeatherForecastController(private val weatherForecastService: WeatherForecastService) {

    @GetMapping(path = ["/{office}/{gridX},{gridY}/forecast"], produces = [MediaType.TEXT_EVENT_STREAM_VALUE])
    fun getWeatherToday(@PathVariable office: String, @PathVariable gridX: String, @PathVariable gridY: String): Mono<WeatherDto> =
            weatherForecastService.getWeatherToday(office, gridX, gridY)

}