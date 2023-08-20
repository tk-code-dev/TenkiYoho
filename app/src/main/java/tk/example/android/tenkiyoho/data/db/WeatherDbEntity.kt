package tk.example.android.tenkiyoho.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather_city")
data class WeatherDbEntity(
    @PrimaryKey(autoGenerate = true)
    val weatherId: Int,
    val city: String,
    val cnt: Int?,
    val list: List<WeatherDataEntity>?,
    val img: String
)

@Entity(tableName = "weather_data")
data class WeatherDataEntity(
    val dt: Long,
    val temp: Double,
    val icon: String
)



