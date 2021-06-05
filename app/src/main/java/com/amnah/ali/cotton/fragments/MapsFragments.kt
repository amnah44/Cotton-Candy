package com.amnah.ali.cotton.fragments

import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.amnah.ali.cotton.R
import com.amnah.ali.cotton.adapter.CitiesAdapter
import com.amnah.ali.cotton.data.DataManager
import com.amnah.ali.cotton.data.domain.City
import com.amnah.ali.cotton.databinding.FragmentMapBinding
import com.amnah.ali.cotton.ui.CitiesInteractionListener
import com.amnah.ali.cotton.util.Constants
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions

class MapsFragments :  BaseFragment<FragmentMapBinding>(),CitiesInteractionListener,
    OnMapReadyCallback{
    //use binding instead findViewById to easy process
    val list: ArrayList<City> = ArrayList()
    private lateinit var _mMap: GoogleMap
    override val LOG_TAG: String="MAPS_LOG"
    override val bindingInflater: (LayoutInflater) -> FragmentMapBinding=FragmentMapBinding::inflate
    private val _searchFragment = SearchFragment()

    override fun setup(){
        setupMap()
        initRecyclerView()
    }

    override fun addCallBack() {}

    private fun initRecyclerView(){
        // Add recyclerView to this fragment
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
        setFragment(detailsFragment)
    }
    private fun setFragment(fragment: Fragment) {
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        transaction?.apply {
            setCustomAnimations(
                R.anim.slide_from_right,
                R.anim.slideout_from_left,
                R.anim.slide_from_left,
                R.anim.slideout_from_right
            )
            add(R.id.home_container, fragment)
            addToBackStack(java.lang.String.valueOf(MapsFragments()))
            setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            commit()
        }
    }

    override fun onLocationClicked(city: City) {
        moveMapCamera(city)
    }


    private fun setupMap() {
        val mapFragment = childFragmentManager
            .findFragmentById(R.id.mapContainer) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    private fun moveMapCamera(city: City) {
        //to avoid null value in csv file
        try {
            val cameraPosition = CameraPosition.Builder()
                .target(LatLng(city.lat.toDouble(), city.lng.toDouble()))
                .tilt(20f)
                .zoom(10f)
                .bearing(0f)
                .build()
            _mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
        } catch (e: NullPointerException) {
            Log.e(LOG_TAG, "moveMapCamera: ${e.message}")
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        _mMap = googleMap
        try {
            googleMap.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                    requireActivity(), R.raw.mapstyle
                )
            )
        } catch (e: Resources.NotFoundException) {
            e.printStackTrace()
        }
        moveMapCamera(list[0])
    }
}