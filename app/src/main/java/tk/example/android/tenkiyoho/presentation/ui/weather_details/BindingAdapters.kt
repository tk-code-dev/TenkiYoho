package tk.example.android.tenkiyoho.presentation.ui.weather_details

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import android.widget.TextView
import com.bumptech.glide.Glide
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

object BindingAdapters {
    @BindingAdapter("timeFromDateTime")
    @JvmStatic
    fun setTimeFromDateTime(textView: TextView, dateTime: String) {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val outputFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

        val date = inputFormat.parse(dateTime)
        val formattedTime = date?.let { outputFormat.format(it) }

        textView.text = formattedTime
    }

    @BindingAdapter("doubleFormattedText")
    @JvmStatic
    fun setDoubleFormattedText(textView: TextView, number: Double) {
        val decimalFormat = DecimalFormat("##.#")
        val formattedNumber = decimalFormat.format(number)
        textView.text = formattedNumber
    }
}
