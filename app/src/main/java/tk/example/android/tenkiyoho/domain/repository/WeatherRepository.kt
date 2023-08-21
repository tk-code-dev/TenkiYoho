package tk.example.android.tenkiyoho.domain.repository

import kotlinx.coroutines.flow.Flow
import tk.example.android.tenkiyoho.domain.model.Output
import tk.example.android.tenkiyoho.domain.model.WeatherResponse

interface WeatherRepository {

    suspend fun fetchWeather(cityName: String, appid: String): Flow<Output<WeatherResponse>>

    suspend fun fetchWeather(latitude: String, longitude:String, appid: String): Flow<Output<WeatherResponse>>

}