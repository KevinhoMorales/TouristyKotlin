package com.kevinhomorales.touristykotlin.place.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.kevinhomorales.touristykotlin.networkmanager.NetworkManager
import com.kevinhomorales.touristykotlin.places.model.Place

class PlaceViewModel: ViewModel() {
    lateinit var place: Place
    var isUpdate = false

    fun uploadPlace(place: Place, context: Context, completion: () -> Unit) {
        NetworkManager.shared.updatePlace(place, context, completion)
    }
}