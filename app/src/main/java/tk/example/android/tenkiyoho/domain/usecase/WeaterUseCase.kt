package tk.example.android.tenkiyoho.domain.usecase

import kotlinx.coroutines.flow.Flow
import tk.example.android.tenkiyoho.domain.model.Output
import tk.example.android.tenkiyoho.domain.model.WeatherResponse

interface WeatherUseCase {
    suspend fun execute(cityName: String, appid: String): Flow<Output<WeatherResponse>>
}