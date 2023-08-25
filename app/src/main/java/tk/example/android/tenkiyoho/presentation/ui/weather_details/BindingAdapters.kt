package tk.example.android.tenkiyoho.presentation.ui.weather_details

import android.annotation.SuppressLint
import android.widget.TextView
import androidx.databinding.BindingAdapter
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

object BindingAdapters {

    @SuppressLint("SetTextI18n")
    @BindingAdapter("timeFromUnixDateTime")
    @JvmStatic
    fun setTimeFromDateTime(textView: TextView, unixTimestamp: Long) {
        // display datetime form dt
        val cal = Calendar.getInstance()
        cal.timeInMillis = unixTimestamp * 1000
        val dayOfWeek = cal.get(Calendar.DAY_OF_WEEK)
        val sdf = SimpleDateFormat("EEEE", Locale.getDefault())
        val formattedDate = convertUnixTimestampToDate(unixTimestamp)
        textView.text = formattedDate + "\n" + sdf.format(Date(cal.timeInMillis))
    }

    private fun convertUnixTimestampToDate(unixTimestamp: Long): String {
        val sdf = SimpleDateFormat("dd\nHH:mm", Locale.getDefault())
        val date = Date(unixTimestamp * 1000) // Convert Unix timestamp to milliseconds
        return sdf.format(date)
    }

    @BindingAdapter("timeFromDateTime")
    @JvmStatic
    fun setTimeFromDateTime(textView: TextView, dateTime: String) {
        // display datetime form dtx
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
