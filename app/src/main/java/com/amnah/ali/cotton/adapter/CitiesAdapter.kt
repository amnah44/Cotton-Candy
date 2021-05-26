package com.amnah.ali.cotton.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.amnah.ali.cotton.data.domain.City
import com.amnah.ali.cotton.databinding.RawItemBinding

class CitiesAdapter(val cityList: ArrayList<City>) :
    RecyclerView.Adapter<CitiesAdapter.ViewHolder>() {
    //link this fragment with layout source
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(RawItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }
    //show all components in cardView
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.citiesBox.text = cityList[position].city
        holder.binding.countryBox.text = cityList[position].country
        holder.binding.populationBox.text = cityList[position].population
    }

    //to get size of list
    override fun getItemCount() = cityList.size

    //to get all views in row item and with binding to make easy process
    class ViewHolder(val binding: RawItemBinding) : RecyclerView.ViewHolder(binding.root)
}
