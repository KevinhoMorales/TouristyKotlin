package com.kevinhomorales.touristykotlin.places.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.kevinhomorales.touristykotlin.networkmanager.NetworkManager
import com.kevinhomorales.touristykotlin.places.model.Place

class PlacesViewModel: ViewModel() {

    fun getPlaces(context: Context, completion: (MutableList<Place>) -> Unit) {
        return NetworkManager.shared.getPlaces(context, completion)
    }

    fun deletePlace(place: Place, context: Context, completion: () -> Unit) {
        NetworkManager.shared.deletePlace(place, context, completion)
    }
}