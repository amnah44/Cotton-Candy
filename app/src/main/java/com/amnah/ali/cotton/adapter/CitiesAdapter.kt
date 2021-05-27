package com.amnah.ali.cotton.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.amnah.ali.cotton.data.domain.City
import com.amnah.ali.cotton.databinding.RawItemBinding

class CitiesAdapter(val list: ArrayList<City>) :
    RecyclerView.Adapter<CitiesAdapter.ViewHolder>() {

    //link this fragment with layout source
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: RawItemBinding = RawItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(view)
    }
    //show all components in cardView
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding?.apply {
            citiesBox.text = list[position].city
            countryBox.text = list[position].country
            populationBox.text = list[position].population
            longitude.text = list[position].lng
            latitude.text = list[position].lat
        }
    }
    //to get size of list
    override fun getItemCount() = list.size
    //to get all views in row item and with binding to make easy process
    class ViewHolder(val binding: RawItemBinding?) : RecyclerView.ViewHolder(binding?.root!!)

}
