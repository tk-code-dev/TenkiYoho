package tk.example.android.tenkiyoho.presentation.ui.weather_details

import android.widget.TextView
import androidx.databinding.BindingAdapter
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

object BindingAdapters {
    @BindingAdapter("timeFromDateTime")
    @JvmStatic
    fun setTimeFromDateTime(textView: TextView, dateTime: String) {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val outputFormat = SimpleDateFormat("dd日 HH:mm", Locale.getDefault())

        val date = inputFormat.parse(dateTime)
        val formattedTime = date?.let { outputFormat.format(it) }

        textView.text = formattedTime
    }

    @BindingAdapter("doubleFormattedText")
    @JvmStatic
    fun setDoubleFormattedText(textView: TextView, number: Double) {
        // display even when the fractional part is 0
        val decimalFormat = DecimalFormat("##.0")
        val formattedNumber = decimalFormat.format(number)
        textView.text = formattedNumber
    }
}
