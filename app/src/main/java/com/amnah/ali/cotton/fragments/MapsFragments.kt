package com.amnah.ali.cotton.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.amnah.ali.cotton.R
import com.amnah.ali.cotton.adapter.CitiesAdapter
import com.amnah.ali.cotton.data.DataManager
import com.amnah.ali.cotton.data.domain.City
import com.amnah.ali.cotton.databinding.FragmentMapBinding
import com.amnah.ali.cotton.ui.CitiesInteractionListener
import com.amnah.ali.cotton.util.Constants

class MapsFragments :  BaseFragment<FragmentMapBinding>(),CitiesInteractionListener{
    //use binding instead findViewById to easy process
    override val LOG_TAG: String="MAPS_LOG"
    override val bindingInflater: (LayoutInflater) -> FragmentMapBinding=FragmentMapBinding::inflate
    private val _searchFragment = SearchFragment()

    override fun setup(){
        initRecyclerView()
    }

    override fun addCallBack() {
        binding!!.apply {
            floatingSearchBtn.setOnClickListener{
                addFragments(_searchFragment )
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
            adapter = CitiesAdapter(list,this@MapsFragments)
        }
    }

    private fun addFragments(fragment: Fragment) {
        (activity)!!.supportFragmentManager.beginTransaction().apply {
                add(R.id.fragment_container, fragment).addToBackStack(null)
                commit()
        }
    }
    //send data to details fragment after click on card view
    override fun onClickItem(city: City) {
        val detailsFragment = DetailsFragment()
        val bundle = Bundle()
        bundle.putString(Constants.Key.CITY,city.city)
        bundle.putString(Constants.Key.COUNTRY,city.country)
        bundle.putString(Constants.Key.POPULATION,city.population.toString())
        bundle.putString(Constants.Key.LAT,city.lat)
        bundle.putString(Constants.Key.LNG,city.lng)
        detailsFragment.arguments = bundle
        Log.i("argCity",bundle.toString())
        addFragments(detailsFragment)
    }
}