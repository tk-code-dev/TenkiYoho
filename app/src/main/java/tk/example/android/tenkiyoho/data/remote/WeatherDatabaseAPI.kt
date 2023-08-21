package tk.example.android.tenkiyoho.data.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import tk.example.android.tenkiyoho.domain.model.WeatherResponse

object WeatherDatabaseAPI {
    private const val API_VERSION = "2.5"
    const val BASE_API_URL = "https://api.openweathermap.org/"

    interface WeatherService {
        @GET("data/$API_VERSION/forecast")
        suspend fun fetch5daysWeather(
            @Query("q") q: String,
            @Query("appid") appid: String,
            @Query("units") units: String = "metric",
            @Query("lang") lang: String = "jp"
        ): Response<WeatherResponse>

        @GET("data/$API_VERSION/forecast")
        suspend fun fetch5daysWeatherFromLocation(
            @Query("lat") lat: String,
            @Query("lon") lon: String,
            @Query("appid") appid: String,
            @Query("units") units: String = "metric",
            @Query("lang") lang: String = "jp"
        ): Response<WeatherResponse>
    }
}