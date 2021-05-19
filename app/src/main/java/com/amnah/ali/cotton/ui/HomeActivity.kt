package com.amnah.ali.cotton.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import com.amnah.ali.cotton.R
import com.amnah.ali.cotton.data.DataManager
import com.amnah.ali.cotton.data.domain.City
import com.amnah.ali.cotton.databinding.ActivityHomeBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds

class HomeActivity : BaseActivity<ActivityHomeBinding>(), OnMapReadyCallback {
    override val LOG_TAG: String = "MAIN_ACTIVITY"
    override val bindingInflater: (LayoutInflater) -> ActivityHomeBinding =
        ActivityHomeBinding::inflate

    private var mMap: GoogleMap? = null

    override fun setup() {
        setupMap()
        updateUi(DataManager.getCurrentCity())
    }


    override fun addCallbacks() {
        binding?.iconSearch!!.setOnClickListener {
            startActivity(Intent(this, SearchActivity::class.java))
        }

        binding?.iconNext?.setOnClickListener {
            updateUi(DataManager.getNextCity())
        }

        binding?.iconPrevious?.setOnClickListener {
            updateUi(DataManager.getPreviousCity())
        }

        binding?.seeMoreBtn?.setOnClickListener{
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("https://www.google.com/search?q=${DataManager.getCurrentCity().city}")
            startActivity(intent)
        }

    }

    @SuppressLint("SetTextI18n")
    private fun updateUi(city: City) {
        binding?.apply {
            this.city.text = city.city
            country.text = city.country
            capital.text = city.capital
            population.text = city.population
            seeMoreBtn.text = "more about ${city.city}"
        }
        if (mMap != null){

            val locationBounds = LatLng(city.lat.toDouble(), city.lng.toDouble())
            mMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(locationBounds, 10f))
        }
    }

    private fun moveMapCamera(lat: Double, lng: Double) {


    }


    private fun setupMap() {
//         Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.mapContainer) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
    }
}