package com.eiselle.kotlinreactive.config

import org.springframework.boot.autoconfigure.web.WebProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class Config {

    @Bean
    fun webClient(builder: WebClient.Builder): WebClient =
            builder
                    .baseUrl("https://api.weather.gov")
                    .build()

    @Bean
    fun resources(): WebProperties.Resources {
        return WebProperties.Resources()
    }

}