package com.kevinhomorales.touristykotlin.place.view

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.kevinhomorales.touristykotlin.main.MainActivity
import com.kevinhomorales.touristykotlin.R
import com.kevinhomorales.touristykotlin.databinding.ActivityPlaceBinding
import com.kevinhomorales.touristykotlin.place.viewmodel.PlaceViewModel
import com.kevinhomorales.touristykotlin.places.model.Place
import com.kevinhomorales.touristykotlin.utils.Constants

class PlaceActivity : MainActivity() {
    lateinit var binding: ActivityPlaceBinding
    lateinit var viewModel: PlaceViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlaceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpView()
    }

    private fun setUpView() {
        viewModel = ViewModelProvider(this).get(PlaceViewModel::class.java)
        if (intent.extras != null) {
            viewModel.place = intent.extras!!.get(Constants.PLACE_KEY) as Place
        }

        if (viewModel.place.id.isEmpty()) {
            binding.navButtonId.visibility = View.GONE
            return
        }
        binding.placeNameId.setText(viewModel.place.name)
    }
}