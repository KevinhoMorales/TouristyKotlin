package com.kevinhomorales.touristykotlin.places.model

import com.kevinhomorales.touristykotlin.utils.Constants
import java.io.Serializable

data class Place (
    val id: String = Constants.clearString,
    var name: String = Constants.clearString,
    var latitude: String = Constants.clearString,
    var longitude: String= Constants.clearString
): Serializable