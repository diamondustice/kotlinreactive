package com.eiselle.kotlinreactive.service

import AppUtils
import com.eiselle.kotlinreactive.dto.WeatherDto
import com.eiselle.kotlinreactive.exception.WeatherAPIException
import com.eiselle.kotlinreactive.model.Weather
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.Duration

@Service
class WeatherForecastService(private val webClient: WebClient) {
    fun getWeatherToday(office: String, gridX: String, gridY: String): Mono<WeatherDto> =
            webClient.get()
                    .uri { it.path("/gridpoints/$office/$gridX,$gridY/forecast").build() }
                    .retrieve()
                    .bodyToMono(Weather::class.java)
                    .flatMapMany { Flux.fromIterable(it.getProperties().getPeriods()) }
                    .filter(AppUtils::isPeriodToday)
                    .delayElements(Duration.ofSeconds(1))
                    .switchIfEmpty(Mono.error(WeatherAPIException("No forecast available for today.")))
                    .map(AppUtils::dayToDto)
                    .collectList()
                    .map { WeatherDto(it) }

}
