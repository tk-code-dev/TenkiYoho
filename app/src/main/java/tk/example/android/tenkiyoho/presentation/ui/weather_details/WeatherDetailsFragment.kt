package tk.example.android.tenkiyoho.presentation.ui.weather_details

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import coil.load
import tk.example.android.tenkiyoho.BuildConfig
import tk.example.android.tenkiyoho.databinding.FragmentWeatherDetailsBinding
import tk.example.android.tenkiyoho.domain.model.Output
import tk.example.android.tenkiyoho.presentation.ui.base.BaseFragment
import tk.example.android.tenkiyoho.presentation.ui.weather_home.WeatherHomeViewModel
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class WeatherDetailsFragment : BaseFragment() {
    private val detailsViewModel: WeatherDetailsViewModel by activityViewModels()
    private var binding: FragmentWeatherDetailsBinding? = null
    private val args: WeatherDetailsFragmentArgs by navArgs()
    private val apiKey = BuildConfig.WEATHER_API_KEY

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
        Log.d("city",cityName)
        detailsViewModel.fetchWeatherData(cityName,apiKey)
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

                        val decimalFormat = DecimalFormat("##.#")

                        binding?.tempCurrentTv?.text = decimalFormat.format(item.list[0].main.temp)
                        binding?.tempMaxTv?.text = decimalFormat.format(item.list[0].main.temp_max)
                        binding?.tempMinTv?.text = decimalFormat.format(item.list[0].main.temp_min)
                        binding?.descriptionTv?.text = item.list[0].weather[0].description.toString()

                        val baseUrl = "http://openweathermap.org/img/wn/"
                        val imageUrl = "${baseUrl}${item.list[0].weather[0].icon}.png"
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

}

