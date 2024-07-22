package com.kevinhomorales.touristykotlin.networkmanager

import android.content.Context
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.kevinhomorales.touristykotlin.places.model.Place

class NetworkManager {
    companion object {
        val shared = NetworkManager()
    }
    private val dataBase = Firebase.firestore
    private val PATH = "places"

    fun getPlaces(context: Context, completion: (MutableList<Place>) -> Unit) {
        val places = mutableListOf<Place>()
        dataBase.collection(PATH)
            .get()
            .addOnSuccessListener { result ->
                result.documents.forEach { document ->
                    val id = document.id
                    val name = document.data?.get("name").toString()
                    val latitude = document.data?.get("latitude").toString()
                    val longitude = document.data?.get("longitude").toString()
                    val place = Place(id, name, latitude, longitude)
                    places.add(place)
                }
                completion(places)
            }
            .addOnFailureListener { e ->
                Toast.makeText(context, "Error -> ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
    }

    fun uploadPlace(place: Place, context: Context, completion: () -> Unit) {
        val placeMap = hashMapOf(
            "id" to place.id,
            "name" to place.name,
            "latitude" to place.latitude,
            "longitude" to place.longitude
        )
        dataBase.collection(PATH).document(place.id).set(placeMap)
            .addOnSuccessListener {
                completion()
            }
            .addOnFailureListener { e ->
                Toast.makeText(context, "Error -> ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
    }

    fun deletePlace(place: Place, context: Context, completion: () -> Unit) {
        dataBase.collection(PATH).document(place.id).delete()
            .addOnSuccessListener {
                completion()
            }
            .addOnFailureListener { e ->
                Toast.makeText(context, "Error -> ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
    }

    fun updatePlace(place: Place, context: Context, completion: () -> Unit) {
        dataBase.collection(PATH).document(place.id)
            .update("name", place.name,
                "latitude", place.latitude,
                "longitude", place.longitude)
            .addOnSuccessListener {
                completion()
            }
            .addOnFailureListener { e ->
                Toast.makeText(context, "Error -> ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
    }
}