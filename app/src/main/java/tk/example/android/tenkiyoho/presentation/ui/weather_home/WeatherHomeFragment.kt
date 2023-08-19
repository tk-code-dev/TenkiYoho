package tk.example.android.tenkiyoho.presentation.ui.weather_home

import android.app.Dialog
import android.content.ContentValues
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import tk.example.android.tenkiyoho.R
import tk.example.android.tenkiyoho.databinding.FragmentWeatherHomeBinding
import tk.example.android.tenkiyoho.presentation.ui.base.BaseFragment
import tk.example.android.tenkiyoho.util.NetworkCheck
import java.text.SimpleDateFormat
import java.util.Date
import java.util.TimeZone

class WeatherHomeFragment : BaseFragment() {
    private val homeViewModel: WeatherHomeViewModel by activityViewModels()
    private var binding: FragmentWeatherHomeBinding? = null

    //    private lateinit var navController: NavController
    private lateinit var networkCheck: NetworkCheck
    private val navController by lazy { findNavController() }

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


}

override fun subscribeUi() {


}


private val onCityClick: (cityName: String) -> Unit =
    { cityName ->
        navigateToDetail(cityName)
    }

private fun navigateToDetail(cityName: String) {
    val action = WeatherHomeFragmentDirections.actionHomeToDetails(cityName)
    navController.navigate(action)
}

//    private fun navigateToDetail(weatherData: WeatherResponse) {
//        findNavController().navigate(
//            R.id.action_home_to_details,
//            WeatherDetailsFragment.Args(weatherData).toBundle(),
//            null
//        )
//    }

private fun showDialog() {
    val dialog = Dialog(requireContext())
    dialog.setContentView(R.layout.dialog_error_message)

    val closeButton = dialog.findViewById<Button>(R.id.dialogButton)
    closeButton.setOnClickListener {
        dialog.dismiss() // Close the dialog when the button is clicked
    }

    dialog.show()

}

}