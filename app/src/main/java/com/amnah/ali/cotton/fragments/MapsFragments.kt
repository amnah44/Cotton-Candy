package com.amnah.ali.cotton.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.amnah.ali.cotton.R
import com.amnah.ali.cotton.adapter.CitiesAdapter
import com.amnah.ali.cotton.data.DataManager
import com.amnah.ali.cotton.data.domain.City
import com.amnah.ali.cotton.databinding.FragmentMapBinding
import com.amnah.ali.cotton.util.Constants
import com.amnah.ali.cotton.util.CsvParser
import kotlin.math.log

class MapsFragments : Fragment(R.layout.fragment_map) {
    //use binding instead findViewById to easy process
    private var binding: FragmentMapBinding? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binded = FragmentMapBinding.bind(view)
        binding = binded

        // Add recyclerView to this fragment
        val list: ArrayList<City> = ArrayList()
       // Log.v("DATA",DataManager.getNextCity().city.length.toString())
        for (indx in DataManager.getCityList() as ArrayList) {
            list.add(indx)
        }
        //activate recyclerView to be seen
        binding?.recyclerView?.apply {
            layoutManager = LinearLayoutManager(activity, RecyclerView.HORIZONTAL,false)
            adapter = CitiesAdapter(list)
        }
    }
}