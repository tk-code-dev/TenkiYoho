package tk.example.android.tenkiyoho.presentation.ui.weather_home

import android.Manifest
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.SearchView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import tk.example.android.tenkiyoho.data.db.WeatherDbEntity
import tk.example.android.tenkiyoho.databinding.FragmentWeatherHomeBinding
import tk.example.android.tenkiyoho.presentation.ui.base.BaseFragment
import tk.example.android.tenkiyoho.util.NetworkCheck
import java.text.DecimalFormat
import javax.inject.Inject

class WeatherHomeFragment : BaseFragment(), LocationListener {
    private val homeViewModel: WeatherHomeViewModel by activityViewModels()
    private var binding: FragmentWeatherHomeBinding? = null
    private lateinit var networkCheck: NetworkCheck
    private val navController by lazy { findNavController() }
    var latitude: String = ""
    var longitude: String = ""

    // Format Latitude & Longitude
    private val precision = 2
    val df = DecimalFormat("#.${"#".repeat(precision)}")

    @Inject
    lateinit var homeAdapter: HomeAdapter

    private var buttonWeatherCityList = listOf(
        WeatherDbEntity("Tokyo", null, null, "img_tokyo"),
        WeatherDbEntity("Hyogo", null, null, "img_hyogo"),
        WeatherDbEntity("Oita", null, null, "img_oita"),
        WeatherDbEntity("Hokkaido", null, null, "img_hokkaido")
    )

    private lateinit var locationManager: LocationManager

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            locationStart()
        } else {
            val toast = Toast.makeText(
                requireContext(),
                "現在地を入手できません", Toast.LENGTH_SHORT
            )
            toast.show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentWeatherHomeBinding.inflate(inflater, container, false).let {
        networkCheck = NetworkCheck()
        binding = it

        // Set up a listener for search queries
        binding?.searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { it ->
                    Log.d(ContentValues.TAG, it)
                    binding?.searchView?.clearFocus()
                    val inputMethodManager =
                        requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputMethodManager.hideSoftInputFromWindow(binding?.searchView?.windowToken, 0)
                    binding?.searchView?.setQuery(null, false)
                    navigateToDetail(it,"","")
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                Log.d(ContentValues.TAG, newText.toString())
                return true
            }
        })

        with(it) {
            root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeAdapter.update(buttonWeatherCityList)

        binding?.currentLocationBtn?.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            } else {
                locationStart()
            }
        }
    }

    override fun subscribeUi() {
        binding?.let {
            homeAdapter = HomeAdapter(arrayListOf(), onCityClick)
            it.cityRv.adapter = homeAdapter
        }
        binding?.progressSpinner?.visibility = View.INVISIBLE
    }

    private val onCityClick: (city: WeatherDbEntity) -> Unit =
        { city ->
            navigateToDetail(city.city,"","")
        }

    private fun navigateToDetail(cityName: String,latitude: String, longitude: String) {
        val action =
            WeatherHomeFragmentDirections.actionHomeToDetails(cityName, latitude, longitude)
        navController.navigate(action)
    }

    private fun locationStart() {
        Log.d("debug", "locationStart()")

        // Instances of LocationManager class must be obtained using Context.getSystemService(Class)
        locationManager =
            requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager

        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Log.d("debug", "location manager Enabled")
        } else {
            // to prompt setting up GPS
            val settingsIntent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            startActivity(settingsIntent)
            Log.d("debug", "not gpsEnable, startActivity")
        }

        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1000
            )

            Log.d("debug", "checkSelfPermission false")
            return
        }

        locationManager.requestLocationUpdates(
            LocationManager.GPS_PROVIDER,
            10000,
            500f,
            this
        )
        binding?.progressSpinner?.visibility = View.VISIBLE

        if(latitude.isNotBlank()){
            binding?.progressSpinner?.visibility = View.INVISIBLE
            navigateToDetail("",String.format("%.${precision}f", latitude.toDouble()), String.format("%.${precision}f", longitude.toDouble()))
        }
    }

    override fun onLocationChanged(location: Location) {
        latitude = location.latitude.toString()
        longitude = location.longitude.toString()
        binding?.progressSpinner?.visibility = View.INVISIBLE

        navigateToDetail("",String.format("%.${precision}f", latitude.toDouble()), String.format("%.${precision}f", longitude.toDouble()))
    }

    override fun onProviderEnabled(provider: String) {

    }

    override fun onProviderDisabled(provider: String) {

    }
}