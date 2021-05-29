package com.amnah.ali.cotton.fragments

import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.amnah.ali.cotton.R
import com.amnah.ali.cotton.adapter.CitiesAdapter
import com.amnah.ali.cotton.data.DataManager
import com.amnah.ali.cotton.data.domain.City
import com.amnah.ali.cotton.databinding.FragmentMapBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions


class MapsFragments : Fragment() {
    //use binding instead findViewById to easy process
    private lateinit var binding: FragmentMapBinding
    private lateinit var _mMap: GoogleMap
    private val LOG_TAG: String = "MAPS_FRAGMENT"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMapBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setupMap()


        //activate recyclerView to be seen
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false)
            adapter = CitiesAdapter(DataManager.getCityList() as ArrayList<City>)
        }

    }

    private fun setupMap() {
        val mapFragment = childFragmentManager
            .findFragmentById(R.id.mapContainer) as SupportMapFragment
        mapFragment.getMapAsync(callback)
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

    private val callback: OnMapReadyCallback = OnMapReadyCallback { googleMap ->
        _mMap = googleMap
        try {
            googleMap.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                    requireActivity(), R.raw.map_style
                )
            )
        } catch (e: Resources.NotFoundException) {
            e.printStackTrace()
        }
    }



}