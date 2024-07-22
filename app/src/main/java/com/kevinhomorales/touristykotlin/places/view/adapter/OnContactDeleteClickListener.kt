package com.kevinhomorales.touristykotlin.places.view.adapter

import com.kevinhomorales.touristykotlin.places.model.Place

interface OnPlaceDeleteClickListener {
    fun onPlaceDeleteClick(place: Place)
}