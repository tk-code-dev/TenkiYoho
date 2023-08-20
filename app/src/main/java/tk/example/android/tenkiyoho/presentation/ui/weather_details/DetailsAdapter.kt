package tk.example.android.tenkiyoho.presentation.ui.weather_details

import android.content.ContentValues.TAG
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import tk.example.android.tenkiyoho.databinding.ItemWeatherCardBinding
import tk.example.android.tenkiyoho.domain.model.WeatherData
import javax.inject.Singleton

@Singleton
class DetailsAdapter(
    private val list: ArrayList<WeatherData>,
) : RecyclerView.Adapter<DetailsAdapter.WeatherHolder>() {

    inner class WeatherHolder(private val binding: ItemWeatherCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: WeatherData) {
            binding.weather = item

            val baseUrl = "http://openweathermap.org/img/wn/"
            val imageUrl = "${baseUrl}${item.weather[0].icon}.png"
            binding.weatherIcon.load(imageUrl) {
                crossfade(true)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemWeatherCardBinding.inflate(inflater, parent, false)
        return WeatherHolder(binding)
    }

    override fun onBindViewHolder(holder: WeatherHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount() = list.size

    fun update(newList: List<WeatherData>) {
        list.clear()
        list.addAll(newList)
        Log.d(TAG, "$newList")
        notifyItemRangeChanged(0, list.size)
    }
}