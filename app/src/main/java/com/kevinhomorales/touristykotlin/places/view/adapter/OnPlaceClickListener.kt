package com.kevinhomorales.touristykotlin.places.view.adapter

import com.kevinhomorales.touristykotlin.places.model.Place

interface OnPlaceClickListener {
    fun onPlaceClick(place: Place)
}