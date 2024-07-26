package com.kevinhomorales.touristykotlin.place.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.GoogleMap
import com.kevinhomorales.touristykotlin.networkmanager.NetworkManager
import com.kevinhomorales.touristykotlin.places.model.Place
import kotlin.properties.Delegates

class PlaceViewModel: ViewModel() {
    lateinit var place: Place
    var latitude = 0.0
    var longitude = 0.0

    var isUpdate = false
    lateinit var map: GoogleMap

    fun uploadPlace(place: Place, context: Context, completion: () -> Unit) {
        NetworkManager.shared.updatePlace(place, context, completion)
    }

    fun updatePlace(placeEdit: Place, context: Context, completion: () -> Unit) {
        val placeTemp = placeEdit
        if (placeEdit.name.isEmpty()) {
            placeTemp.name = place.name
        }
        if (placeTemp.latitude.isEmpty()) {
            placeTemp.latitude = place.latitude
        }
        if (placeTemp.longitude.isEmpty()) {
            placeTemp.longitude = place.longitude
        }
        NetworkManager.shared.updatePlace(placeTemp, context, completion)
    }
}