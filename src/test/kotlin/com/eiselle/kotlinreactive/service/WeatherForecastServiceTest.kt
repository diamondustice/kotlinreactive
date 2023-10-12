import com.eiselle.kotlinreactive.model.Period
import com.eiselle.kotlinreactive.model.Properties
import com.eiselle.kotlinreactive.model.Weather
import com.eiselle.kotlinreactive.service.WeatherForecastService
import com.fasterxml.jackson.databind.ObjectMapper
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.jetbrains.annotations.NotNull
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.web.reactive.function.client.WebClient
import reactor.test.StepVerifier
import java.io.IOException
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@ExtendWith(MockitoExtension::class)
class WeatherForecastServiceTest {

    companion object {
        private lateinit var mockBackEnd: MockWebServer

        @BeforeAll
        @JvmStatic
        @Throws(IOException::class)
        fun setUp() {
            mockBackEnd = MockWebServer()
            mockBackEnd.start()
        }

        @AfterAll
        @JvmStatic
        @Throws(IOException::class)
        fun tearDown() {
            mockBackEnd.shutdown()
        }
    }

    private lateinit var client: WeatherForecastService

    @BeforeEach
    fun initialize() {

        val builder = WebClient.builder()

        client = WeatherForecastService(webClient(builder))
    }

    @Test
    fun whenGetWeatherForecastToday_thenReturnAllPeriodForTodayForecast() {
        val objectMapper = ObjectMapper()

        val mockWeather = Weather(getMyProperties())

        mockBackEnd.enqueue(
                MockResponse()
                        .setBody(objectMapper.writeValueAsString(mockWeather))
                        .addHeader("Content-Type", "application/json")
        )

        val weatherDtoMono = client.getWeatherToday("MLB", "33", "70")

        StepVerifier.create(weatherDtoMono)
                .expectNextMatches { weatherDto ->
                     weatherDto.getDaily()[0].getDayName() == "Today - Day" &&
                            weatherDto.getDaily()[1].getDayName() == "Today - Night" &&
                            weatherDto.getDaily()[2].getDayName() == "Today - Overnight" &&
                            weatherDto.getDaily().size == 3
                }
                .verifyComplete()

        // Ensure that the MockWebServer received the correct HttpRequest.
        val recordedRequest = mockBackEnd.takeRequest()
        assertEquals("GET", recordedRequest.method)
        assertEquals("/gridpoints/MLB/33,70/forecast", recordedRequest.path)
    }

    @NotNull
    private fun getMyProperties(): Properties {
        val dateToday = LocalDate.now().format(DateTimeFormatter.ISO_DATE)

        return Properties(
                listOf(
                        Period(1, "Today - Day", "70", "F", "Cloudy", "false", dateToday + "T06:00:00-04:00", dateToday + "T18:00:00-04:00"),
                        Period(2, "Today - Night", "71", "F", "Rainy", "true", dateToday + "T06:00:00-04:00", dateToday + "T18:00:00-04:00"),
                        Period(3, "Today - Overnight", "71", "F", "Rainy", "true", dateToday + "T06:00:00-04:00", dateToday + "T18:00:00-04:00"),
                        Period(4, "Next Day - Day", "72", "F", "Sunny", "false", "2023-10-01T06:00:00-04:00", "2023-10-09T18:00:00-04:00"),
                        Period(5, "Next Day - Night", "73", "F", "Mostly Cloudy", "false", "2023-10-01T06:00:00-04:00", "2023-10-09T18:00:00-04:00"),
                        Period(6, "Next Day - Overnight", "73", "F", "Mostly Cloudy", "false", "2023-10-01T06:00:00-04:00", "2023-10-09T18:00:00-04:00")
                ))
    }

    private fun webClient(builder: WebClient.Builder): WebClient =
            builder
                    .baseUrl("http://localhost:${mockBackEnd.port}")
                    .build()
}