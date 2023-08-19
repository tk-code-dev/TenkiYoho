package tk.example.android.tenkiyoho.data.repository

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import tk.example.android.tenkiyoho.data.remote.WeatherRemoteDataSource
import tk.example.android.tenkiyoho.domain.model.Output
import tk.example.android.tenkiyoho.domain.model.WeatherResponse
import tk.example.android.tenkiyoho.domain.repository.WeatherRepository
import javax.inject.Inject

internal class WeatherRepositoryImpl @Inject constructor(
    private val weatherRemoteDataSource: WeatherRemoteDataSource
) : WeatherRepository {
    override suspend fun fetchWeather(cityName: String, appid: String): Flow<Output<WeatherResponse>> {
        return flow {
            emit(Output.loading())
            val result = weatherRemoteDataSource.fetchWeatherFromRemote(cityName, appid)
            emit(result)
        }.flowOn(Dispatchers.IO)
    }

}