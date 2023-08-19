package tk.example.android.tenkiyoho.presentation.ui.weather_details

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import coil.load
import tk.example.android.tenkiyoho.BuildConfig
import tk.example.android.tenkiyoho.databinding.FragmentWeatherDetailsBinding
import tk.example.android.tenkiyoho.domain.model.Output
import tk.example.android.tenkiyoho.presentation.ui.base.BaseFragment
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.TimeZone
import javax.inject.Inject

class WeatherDetailsFragment : BaseFragment() {
    private val detailsViewModel: WeatherDetailsViewModel by activityViewModels()
    private var binding: FragmentWeatherDetailsBinding? = null
    private val args: WeatherDetailsFragmentArgs by navArgs()
    private val apiKey = BuildConfig.WEATHER_API_KEY
    private var timeRange: Int = 1

    @Inject
    lateinit var detailsAdapter: DetailsAdapter

    var cityName: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentWeatherDetailsBinding.inflate(inflater, container, false).let {
        binding = it

        with(it) {
            lifecycleOwner = this@WeatherDetailsFragment
//            sharedElementEnterTransition =
//                TransitionInflater.from(requireContext())
//                    .inflateTransition(android.R.transition.move)
            root
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cityName = args.city
        Log.d("city", cityName)
        detailsViewModel.fetchWeatherData(cityName, apiKey)

    }

    override fun subscribeUi() {
        binding?.let {
            detailsAdapter = DetailsAdapter(arrayListOf())
            it.weatherRv.adapter = detailsAdapter
        }
        detailsViewModel.weatherList.observe(viewLifecycleOwner) { result ->
            when (result.status) {
                Output.Status.SUCCESS -> {
                    result.data?.let { item ->
//                        Log.d("test",SimpleDateFormat("dd/MM/yyyy hh:mm a", Locale.ENGLISH).format(Date((item.cnt*1000).toString())))

                        Log.d(ContentValues.TAG, "success:$item")
                        detailsAdapter.update(item.list)

                        binding?.cityNameTV?.text = item.city.name
                        timeRange = checkTimeRange()

                        val decimalFormat = DecimalFormat("##.#")

                        binding?.tempCurrentTv?.text =
                            decimalFormat.format(item.list[timeRange].main.temp)
                        binding?.descriptionTv?.text =
                            item.list[timeRange].weather[0].description.toString()

                        val baseUrl = "http://openweathermap.org/img/wn/"
                        val imageUrl = "${baseUrl}${item.list[timeRange].weather[0].icon}.png"
                        binding?.weatherIconCurrentIv?.load(imageUrl) {
                            crossfade(true)
                        }

                    }
                }

                Output.Status.ERROR -> {
                    result.message?.let {
                        showError("not find the country") {
                        }
                    }
                }

                Output.Status.LOADING -> {}
            }
        }
//        binding?.buttonToDetails?.setOnClickListener {
//            showDialog()
//            findNavController().navigate(
//                R.id.action_home_to_details,
//                null
//            )
//        }


    }

    fun checkTimeRange(): Int {

        val currentTimeMillis = System.currentTimeMillis()
        val date = Date(currentTimeMillis)
        val sdf = SimpleDateFormat("HH")
        sdf.timeZone = TimeZone.getDefault()
        val formattedDate = sdf.format(date)

        val timeRange = when (formattedDate.toDouble()) {
            in 0.0..10.5 -> 0
            in 10.5..13.5 -> 1
            in 13.5..16.5 -> 2
            in 16.5..19.5 -> 3
            in 19.5..22.5 -> 4
            in 22.5..23.0 -> 5
            else -> 1 // Default value or handle other cases
        }
        println("Result: $timeRange")
        return timeRange.toInt()
    }
}

