package com.amnah.ali.cotton.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.amnah.ali.cotton.databinding.RawItemBinding

class CitiesAdapter(val list: ArrayList<String>) :
    RecyclerView.Adapter<CitiesAdapter.ViewHolder>() {
    //link this fragment with layout source
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(RawItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }
    //show all components in cardView
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.citiesBox.text = "baghdad"
        holder.binding.countryBox.text = "Iraq"
        holder.binding.populationBox.text = "200,22,444"
    }

    //to get size of list
    override fun getItemCount() = list.size

    //to get all views in row item and with binding to make easy process
    class ViewHolder(val binding: RawItemBinding) : RecyclerView.ViewHolder(binding.root)
}
