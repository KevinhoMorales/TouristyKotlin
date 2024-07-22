package com.kevinhomorales.touristykotlin.places.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kevinhomorales.touristykotlin.databinding.RowPlaceBinding
import com.kevinhomorales.touristykotlin.places.model.Place

class PlaceAdapter(private val context: Context, var itemClickListener: OnPlaceClickListener, var deleteClickListener: OnPlaceDeleteClickListener): RecyclerView.Adapter<PlaceAdapter.PlaceViewHolder>() {
    private lateinit var binding: RowPlaceBinding
    private var dataList = mutableListOf<Place>()

    fun setListData(data: MutableList<Place>) {
        dataList = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceViewHolder {
        binding = RowPlaceBinding.inflate(LayoutInflater.from(context), parent, false)
        return PlaceViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PlaceViewHolder, position: Int) {
        val list = dataList[position]
        return holder.bind(list)
    }

    override fun getItemCount(): Int {
        return if (dataList.size > 0) {
            dataList.size
        } else {
            0
        }
    }

    inner class PlaceViewHolder(private val itemBinding: RowPlaceBinding): RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(place: Place) {
            itemBinding.nameId.setText(place.name)
            itemBinding.deleteIconId.setOnClickListener { deleteClickListener.onPlaceDeleteClick(place) }
            itemView.setOnClickListener { itemClickListener.onPlaceClick(place) }
        }
    }
}