package tk.example.android.tenkiyoho.presentation.ui.weather_details

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import tk.example.android.tenkiyoho.BuildConfig
import tk.example.android.tenkiyoho.R
import tk.example.android.tenkiyoho.data.db.WeatherDataEntity
import tk.example.android.tenkiyoho.databinding.FragmentWeatherDetailsBinding
import tk.example.android.tenkiyoho.domain.model.Output
import tk.example.android.tenkiyoho.presentation.ui.base.BaseFragment
import java.text.DecimalFormat
import javax.inject.Inject

class WeatherDetailsFragment : BaseFragment() {
    private val detailsViewModel: WeatherDetailsViewModel by activityViewModels()
    private var binding: FragmentWeatherDetailsBinding? = null
    private val args: WeatherDetailsFragmentArgs by navArgs()
    private val apiKey = BuildConfig.WEATHER_API_KEY
    private var isRetry: Boolean = false

    @Inject
    lateinit var detailsAdapter: DetailsAdapter

    var cityName: String = ""
    var lat: String = ""
    var lon: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentWeatherDetailsBinding.inflate(inflater, container, false).let {
        binding = it

        with(it) {
            lifecycleOwner = this@WeatherDetailsFragment
            root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cityName = args.city
        Log.d("city", cityName)
        if (cityName.isNotBlank()) {
            detailsViewModel.fetchWeatherData(cityName, apiKey)
        } else {
            lat = args.latitude
            lon = args.longitude
            Log.d("latitude", lat)
            detailsViewModel.fetchWeatherDataFromLocation(lat, lon, apiKey)
        }
    }

    @SuppressLint("SetTextI18n")
    override fun subscribeUi() {
        binding?.let {
            detailsAdapter = DetailsAdapter(arrayListOf())
            it.weatherRv.adapter = detailsAdapter
        }
        detailsViewModel.weatherList.observe(viewLifecycleOwner) { result ->
            when (result.status) {
                Output.Status.SUCCESS -> {
                    result.data?.let { item ->

                        Log.d(ContentValues.TAG, "success:$item")
                        detailsAdapter.update(item.list)

                        binding?.cityNameTV?.text = item.city.name

                        val decimalFormat = DecimalFormat("##.0")

                        binding?.tempCurrentTv?.text = decimalFormat.format(item.list[0].main.temp)

                        binding?.tempMaxTv?.text =
                            "最高 " + decimalFormat.format(item.list[0].main.temp_max)

                        binding?.tempMinTv?.text =
                            "最低 " + decimalFormat.format(item.list[0].main.temp_min)

                        binding?.descriptionTv?.text =
                            item.list[0].weather[0].description.toString()

                        val baseUrl = "http://openweathermap.org/img/wn/"
                        val imageUrl = "${baseUrl}${item.list[0].weather[0].icon}.png"
                        binding?.weatherIconCurrentIv?.load(imageUrl) {
                            crossfade(true)
                        }

                        for (weatherData in item.list) {
                            val weatherDataList = WeatherDataEntity(
                                dt = weatherData.dt,
                                temp = weatherData.main.temp,
                                icon = weatherData.weather[0].icon
                            )
                        }
                    }
                    binding?.progressSpinner?.visibility = View.INVISIBLE
                }

                Output.Status.ERROR -> {
                    result.message?.let {
                        if (it.contains("from city name")) {
                            showDialog()
                        }
                    }
                    binding?.progressSpinner?.visibility = View.INVISIBLE
                }

                Output.Status.LOADING -> {
                    binding?.progressSpinner?.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun showDialog() {
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.dialog_error_message)

        val closeButton = dialog.findViewById<Button>(R.id.dialogButton)
        closeButton.setOnClickListener {
            if (!isRetry && isAlphabetic(cityName)) {
                isRetry = true
                detailsViewModel.fetchWeatherData(cityName, apiKey)

            } else {
                dialog.dismiss()  // Retry twice and Close the dialog, Back to Home
                try {
                    val currentDestination = findNavController().currentDestination
                    if (currentDestination?.id == R.id.weatherDetailsFragment) {
                        findNavController().popBackStack()
                    }
                } catch (e: IllegalStateException) {
                    Log.e("from WeatherDetails", "screen has moved WeatherHome")
                }
            }
        }
        dialog.show()
    }

    private fun isAlphabetic(input: String): Boolean {
        return input.matches(Regex("[a-zA-Z]+"))
    }
}

