package tk.example.android.tenkiyoho.presentation.ui.weather_home

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import tk.example.android.tenkiyoho.R
import tk.example.android.tenkiyoho.databinding.FragmentWeatherHomeBinding
import tk.example.android.tenkiyoho.presentation.ui.base.BaseFragment

class WeatherHomeFragment : BaseFragment() {
    private var binding: FragmentWeatherHomeBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentWeatherHomeBinding.inflate(inflater, container, false).let {
        binding = it
        with(it) {
            root
        }
    }

    override fun subscribeUi() {
        binding?.buttonToDetails?.setOnClickListener {
            showDialog()
//            findNavController().navigate(
//                R.id.action_home_to_details,
//                null
//            )
        }
    }

    private fun showDialog(){
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.dialog_error_message)

        val closeButton = dialog.findViewById<Button>(R.id.dialogButton)
        closeButton.setOnClickListener {
            dialog.dismiss() // Close the dialog when the button is clicked
        }

        dialog.show()

    }

}