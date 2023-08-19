package tk.example.android.tenkiyoho.data.remote

import retrofit2.Retrofit
import tk.example.android.tenkiyoho.data.services.BaseRemoteDataSource
import tk.example.android.tenkiyoho.domain.model.Output
import tk.example.android.tenkiyoho.domain.model.WeatherResponse
import javax.inject.Inject

class WeatherRemoteDataSource @Inject constructor(
    private val apiService: WeatherDatabaseAPI.WeatherService, retrofit: Retrofit
) : BaseRemoteDataSource(retrofit) {

    suspend fun fetchWeatherFromRemote(cityName: String, appid: String): Output<WeatherResponse> {
        return getResponse(
            request = { apiService.fetch5daysWeather(cityName, appid) },
            defaultErrorMessage = "Error fetching Photos"
        )
    }

}