package com.amnah.ali.cotton.ui

import android.view.LayoutInflater
import com.amnah.ali.cotton.R
import com.amnah.ali.cotton.databinding.ActivityHomeBinding
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import java.io.BufferedReader
import java.io.InputStreamReader

class HomeActivity : BaseActivity<ActivityHomeBinding>(), OnMapReadyCallback {
    override val LOG_TAG: String = "MAIN_ACTIVITY"
    override val bindingInflater: (LayoutInflater) -> ActivityHomeBinding =
        ActivityHomeBinding::inflate

    private lateinit var mMap: GoogleMap

    override fun setup() {
        setupMap()
    }


    override fun addCallbacks() {
    }


    private fun setupMap() {
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
//        val mapFragment = supportFragmentManager
//            .findFragmentById(R.id.mapContainer) as SupportMapFragment
//        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
    }
}