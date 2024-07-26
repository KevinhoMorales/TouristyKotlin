package com.kevinhomorales.touristykotlin.places.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.kevinhomorales.touristykotlin.R
import com.kevinhomorales.touristykotlin.main.MainActivity
import com.kevinhomorales.touristykotlin.databinding.ActivityPlacesBinding
import com.kevinhomorales.touristykotlin.place.view.PlaceActivity
import com.kevinhomorales.touristykotlin.places.model.Place
import com.kevinhomorales.touristykotlin.places.view.adapter.OnPlaceClickListener
import com.kevinhomorales.touristykotlin.places.view.adapter.OnPlaceDeleteClickListener
import com.kevinhomorales.touristykotlin.places.view.adapter.PlaceAdapter
import com.kevinhomorales.touristykotlin.places.viewmodel.PlacesViewModel
import com.kevinhomorales.touristykotlin.utils.Constants
import java.io.Serializable

class PlacesActivity : MainActivity(), OnPlaceDeleteClickListener, OnPlaceClickListener {
    lateinit var binding: ActivityPlacesBinding
    lateinit var viewModel: PlacesViewModel
    lateinit var placeAdapter: PlaceAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlacesBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        setUpView()
    }

    private fun setUpView() {
        viewModel = ViewModelProvider(this).get(PlacesViewModel::class.java)
        setUpRecyclerView()
        setUpActions()
    }

    private fun setUpRecyclerView() {
        placeAdapter = PlaceAdapter(this, this, this)
        binding.placesRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.placesRecyclerView.adapter = placeAdapter
        getPlaces()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getPlaces() {
        callProgressDialog(true)
        viewModel.getPlaces(this) { places ->
            placeAdapter.setListData(places)
            placeAdapter.notifyDataSetChanged()
            callProgressDialog(false)
        }
    }

    private fun setUpActions() {
        binding.fabId.setOnClickListener {
            val place = Place()
            openPlace(place, false)
        }
    }

    override fun onPlaceClick(place: Place) {
        openPlace(place, true)
    }

    override fun onPlaceDeleteClick(place: Place) {
        deletePlace(place)
    }

    private fun deletePlace(place: Place) {
        val builder = AlertDialog.Builder(this)
        with(builder) {
            setTitle(getString(R.string.delete_place_alert_title))
            setMessage(getString(R.string.delete_place_alert_message))
            setPositiveButton(getString(R.string.confirm)) { dialog, _ ->
                viewModel.deletePlace(place, applicationContext) {
                    getPlaces()
                    dialog.dismiss()
                }
            }
            setNegativeButton(getString(R.string.cancel)) { dialog, _ ->
                dialog.dismiss()
            }
            show()
        }
    }

    private fun openPlace(place: Place, isUpdate: Boolean) {
        val intent = Intent(this, PlaceActivity::class.java)
        intent.putExtra(Constants.PLACE_KEY, place as Serializable)
        intent.putExtra(Constants.IS_UPDATE_KEY, isUpdate)
        startActivity(intent)
    }
}