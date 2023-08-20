package tk.example.android.tenkiyoho.presentation.ui.weather_home

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import tk.example.android.tenkiyoho.R
import tk.example.android.tenkiyoho.data.db.WeatherDbEntity
import tk.example.android.tenkiyoho.databinding.ItemHomeCityBinding
import javax.inject.Singleton


@Singleton
class HomeAdapter(
    private val list: ArrayList<WeatherDbEntity>,
    private val onCityClick: (item: WeatherDbEntity) -> Unit
) : RecyclerView.Adapter<HomeAdapter.WeatherHomeHolder>() {

    inner class WeatherHomeHolder(private val binding: ItemHomeCityBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: WeatherDbEntity) {
            binding.cityHome = item
            binding.itemCityTitle.text = item.city
            val cityStrBuilder: String = item.img

            binding.imgCity.setImageResource(getDrawableIntByFileName(itemView.context.applicationContext,item.img))

            binding.root.setOnClickListener {
                Log.d(TAG, item.city)

                if (binding.cityHome != null) {
                    onCityClick.invoke(item)
                }
            }
        }
    }

    @SuppressLint("DiscouragedApi")
    fun getDrawableIntByFileName(context: Context, fileName: String): Int {
        return context.resources.getIdentifier(fileName, "drawable", context.packageName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherHomeHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemHomeCityBinding.inflate(inflater, parent, false)
        return WeatherHomeHolder(binding)
    }

    override fun onBindViewHolder(holder: WeatherHomeHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount() = list.size

    fun update(newList: List<WeatherDbEntity>) {
        list.clear()
        list.addAll(newList)
        Log.d(TAG, "$newList")
        notifyItemRangeChanged(0, list.size)
    }
}