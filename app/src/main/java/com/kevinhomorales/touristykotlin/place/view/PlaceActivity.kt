package com.kevinhomorales.touristykotlin.place.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.kevinhomorales.touristykotlin.main.MainActivity
import com.kevinhomorales.touristykotlin.R
import com.kevinhomorales.touristykotlin.databinding.ActivityPlaceBinding
import com.kevinhomorales.touristykotlin.place.viewmodel.PlaceViewModel
import com.kevinhomorales.touristykotlin.places.model.Place
import com.kevinhomorales.touristykotlin.utils.Constants
import com.kevinhomorales.touristykotlin.utils.MyLocationMap

class PlaceActivity : MainActivity(), OnMapReadyCallback {
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
            showUploadView()
            return
        }
        binding.placeNameId.setHint(viewModel.place.name)
        showUpdateView()
        setUpActions()
        createMapFragment()
        MyLocationMap(this, this)
    }

     private fun createMapFragment() {
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.fragmentMap) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    private fun showUpdateView() {
        binding.navButtonId.visibility = View.VISIBLE
        binding.updateButtonId.visibility = View.VISIBLE
        binding.uploadButtonId.visibility = View.GONE
    }

    private fun showUploadView() {
        binding.navButtonId.visibility = View.GONE
        binding.updateButtonId.visibility = View.GONE
        binding.uploadButtonId.visibility = View.VISIBLE
    }

    private fun setUpActions() {
        binding.navButtonId.setOnClickListener {
            val latitude = viewModel.place.latitude.toDouble()
            val longitude = viewModel.place.longitude.toDouble()
            openGoogleMap(latitude, longitude)
        }
        binding.updateButtonId.setOnClickListener {
            val nameEditText = binding.placeNameId.text
            if ((nameEditText.isEmpty()) || (viewModel.latitude == 0.0) || (viewModel.longitude == 0.0))  {
                Toast.makeText(applicationContext, getString(R.string.alert_title), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val contactTemp = Place(viewModel.place.id, nameEditText.toString(), viewModel.latitude.toString(), viewModel.longitude.toString())
            viewModel.uploadPlace(contactTemp, this) {
                finish()
            }
        }

        binding.uploadButtonId.setOnClickListener {
            val nameEditText = binding.placeNameId.text
            if ((nameEditText.isEmpty()))  {
                Toast.makeText(applicationContext, getString(R.string.alert_title), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val id = java.util.UUID.randomUUID().toString()
            val name = binding.placeNameId.text.toString()
            val place = Place(id, name, "", "")
            viewModel.uploadPlace(place, this) {
                finish()
            }
        }
    }

    fun openGoogleMap(latitude: Double, longitude: Double) {
        val gmmIntentUri = Uri.parse("geo:$latitude,$longitude")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        startActivity(mapIntent)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        viewModel.map = googleMap
        val latitude = viewModel.place.latitude.toDouble()
        val longitude = viewModel.place.longitude.toDouble()
        val zoomLevel = 15f
        val location = LatLng(latitude, longitude)
        googleMap.addMarker(
            MarkerOptions()
                .position(location)
                .title(viewModel.place.name)
                .draggable(true)

        )
        val cameraUpdate = CameraUpdateFactory.newLatLngZoom(location, zoomLevel)
        googleMap.moveCamera(cameraUpdate)

        googleMap.setOnMapClickListener { latLng ->
            val latitude = latLng.latitude
            val longitude = latLng.longitude
            viewModel.latitude = latitude
            viewModel.longitude = longitude
            Toast.makeText(applicationContext, "Latitude: $latitude, Longitude: $longitude", Toast.LENGTH_SHORT).show()
        }
    }
}