package tk.example.android.tenkiyoho.domain.model

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class WeatherResponse(
    val cod: String,
    val message: Int,
    val cnt: Int,
    val list: List<WeatherData>,
    val city: City
) : Parcelable

@Keep
@Parcelize
data class WeatherData(
    val dt: Long,
    val main: WeatherMain,
    val weather: List<Weather>,
    val clouds: Clouds,
    val wind: Wind,
    val visibility: Int,
    val pop: Double,
    val rain: Rain?,
    val sys: Sys,
    val dt_txt: String
) : Parcelable

@Keep
@Parcelize
data class WeatherMain(
    val temp: Double,
    val feels_like: Double,
    val temp_min: Double,
    val temp_max: Double,
    val pressure: Int,
    val sea_level: Int,
    val grnd_level: Int,
    val humidity: Int,
    val temp_kf: Double
) : Parcelable

@Keep
@Parcelize
data class Weather(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
) : Parcelable

@Keep
@Parcelize
data class Clouds(
    val all: Int
) : Parcelable

@Keep
@Parcelize
data class Wind(
    val speed: Double,
    val deg: Int,
    val gust: Double
) : Parcelable

@Keep
@Parcelize
data class Rain(
    val h: Double
) : Parcelable

@Keep
@Parcelize
data class Sys(
    val pod: String
) : Parcelable

@Keep
@Parcelize
data class City(
    val id: Int,
    val name: String,
    val coord: Coord,
    val country: String,
    val population: Int,
    val timezone: Int,
    val sunrise: Long,
    val sunset: Long
) : Parcelable

@Keep
@Parcelize
data class Coord(
    val lat: Double,
    val lon: Double
) : Parcelable
