package tk.example.android.tenkiyoho.presentation.ui.weather_details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import tk.example.android.tenkiyoho.domain.model.Output
import tk.example.android.tenkiyoho.domain.model.WeatherResponse
import tk.example.android.tenkiyoho.domain.usecase.WeatherUseCase
import tk.example.android.tenkiyoho.presentation.ui.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class WeatherDetailsViewModel @Inject constructor(
    private val weatherUseCase: WeatherUseCase,
) :
    BaseViewModel() {

    private val _weatherList = MutableLiveData<Output<WeatherResponse>>()
    val weatherList: LiveData<Output<WeatherResponse>> = _weatherList

    init {

    }

    fun fetchWeatherData(cityName: String, appid: String) {
        viewModelScope.launch {
            weatherUseCase.execute(cityName, appid).collect {
                _weatherList.value = it
            }
        }
    }

}