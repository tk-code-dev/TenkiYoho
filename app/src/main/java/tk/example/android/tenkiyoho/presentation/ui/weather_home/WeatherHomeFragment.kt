package tk.example.android.tenkiyoho.presentation.ui.weather_home

import android.content.ContentValues
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import tk.example.android.tenkiyoho.data.db.WeatherDbEntity
import tk.example.android.tenkiyoho.databinding.FragmentWeatherHomeBinding
import tk.example.android.tenkiyoho.presentation.ui.base.BaseFragment
import tk.example.android.tenkiyoho.util.NetworkCheck
import javax.inject.Inject

class WeatherHomeFragment : BaseFragment() {
    private val homeViewModel: WeatherHomeViewModel by activityViewModels()
    private var binding: FragmentWeatherHomeBinding? = null
    private lateinit var networkCheck: NetworkCheck
    private val navController by lazy { findNavController() }

    @Inject
    lateinit var homeAdapter: HomeAdapter

    private val buttonWeatherCityList = listOf(
        WeatherDbEntity(1, "Tokyo", null, null, "img_tokyo"),
        WeatherDbEntity(2, "Hyogo", null, null, "img_hyogo"),
        WeatherDbEntity(3, "Oita", null, null, "img_oita"),
        WeatherDbEntity(4, "Hokkaido", null, null, "img_hokkaido")
    )

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
                    navigateToDetail(it)
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

        }

    }

    override fun subscribeUi() {
        binding?.let {
            homeAdapter = HomeAdapter(arrayListOf(), onCityClick)
            it.cityRv.adapter = homeAdapter
        }
    }

    private val onCityClick: (city: WeatherDbEntity) -> Unit =
        { city ->
            navigateToDetail(city.city)
        }

    private fun navigateToDetail(cityName: String) {
        val action = WeatherHomeFragmentDirections.actionHomeToDetails(cityName)
        navController.navigate(action)
    }

//    private fun checkPermissions(): Boolean {
//        if (
//            ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
//                (requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
//            return true
//        }
//        return false
//    }
}