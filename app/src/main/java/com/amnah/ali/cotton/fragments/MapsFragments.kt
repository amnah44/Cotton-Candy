package com.amnah.ali.cotton.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.amnah.ali.cotton.R
import com.amnah.ali.cotton.adapter.CitiesAdapter
import com.amnah.ali.cotton.data.DataManager
import com.amnah.ali.cotton.data.domain.City
import com.amnah.ali.cotton.databinding.FragmentMapBinding

class MapsFragments :  BaseFragment<FragmentMapBinding>(){
    //use binding instead findViewById to easy process
    override val LOG_TAG: String="MAPS_LOG"
    override val bindingInflater: (LayoutInflater) -> FragmentMapBinding=FragmentMapBinding::inflate


    private val _searchFragment = SearchFragment()


    override fun setup(){
        initRecyclerView()
    }

    override fun addCallBack()

    {
        binding!!.apply {
            floatingSearchBtn.setOnClickListener{
                 replaceFragments(_searchFragment )
            }
        }
    }

    private fun initRecyclerView(){
        // Add recyclerView to this fragment
        val list: ArrayList<City> = ArrayList()
        // Log.v("DATA",DataManager.getNextCity().city.length.toString())
        list.addAll(DataManager.getCityList())
        //activate recyclerView to be seen
        binding?.recyclerView?.apply {
            layoutManager = LinearLayoutManager(activity, RecyclerView.HORIZONTAL,false)
            adapter = CitiesAdapter(list)
        }
    }

    private fun replaceFragments(_searchFragment: SearchFragment) {
        (activity)!!.supportFragmentManager.beginTransaction().apply {
                add(R.id.fragment_container, this@MapsFragments._searchFragment)
                commit()
        }
    }
}