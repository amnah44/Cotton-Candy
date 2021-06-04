package com.amnah.ali.cotton.adapter

import android.R
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.amnah.ali.cotton.data.domain.City
import com.amnah.ali.cotton.databinding.RawItemBinding
import com.amnah.ali.cotton.ui.CitiesInteractionListener


class CitiesAdapter(val list: ArrayList<City>,val listener:CitiesInteractionListener) :
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
            /**
             * Here is  to apply the animation
             */

            cardView.animation = AnimationUtils.loadAnimation(holder.itemView.context,com.amnah.ali.cotton.R.anim.anim_card)
            citiesBox.text = list[position].city
            countryBox.text = list[position].country
            populationBox.text = list[position].population.toString().chunked(3).joinToString (",")
//            longitude.text = list[position].lng
//            latitude.text = list[position].lat

            root.setOnClickListener { listener.onClickItem(list[position]) }
        }
    }
    //to get size of list
    override fun getItemCount() = list.size
    //to get all views in row item and with binding to make easy process
    class ViewHolder(val binding: RawItemBinding?) : RecyclerView.ViewHolder(binding?.root!!)


}

