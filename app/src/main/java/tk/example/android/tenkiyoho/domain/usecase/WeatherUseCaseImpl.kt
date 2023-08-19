package tk.example.android.tenkiyoho.domain.usecase

import kotlinx.coroutines.flow.Flow
import tk.example.android.tenkiyoho.domain.model.Output
import tk.example.android.tenkiyoho.domain.model.WeatherResponse
import tk.example.android.tenkiyoho.domain.repository.WeatherRepository
import javax.inject.Inject

class WeatherUseCaseImpl @Inject constructor(private val weatherRepository: WeatherRepository) :
    WeatherUseCase {
    override suspend fun execute(cityName: String, appid: String): Flow<Output<WeatherResponse>> {
        return weatherRepository.fetchWeather(cityName,appid)
    }
}