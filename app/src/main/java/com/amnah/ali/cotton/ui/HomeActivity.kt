package com.amnah.ali.cotton.ui

import android.view.LayoutInflater
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.amnah.ali.cotton.R
import com.amnah.ali.cotton.data.DataManager
import com.amnah.ali.cotton.data.domain.City
import com.amnah.ali.cotton.databinding.ActivityHomeBinding
import com.amnah.ali.cotton.fragments.MapsFragments
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng


class HomeActivity : BaseActivity<ActivityHomeBinding>(), OnMapReadyCallback {
    //type the content after make override
    override val LOG_TAG: String = "MAIN_ACTIVITY"
    val mapFragment = MapsFragments()

    override val bindingInflater: (LayoutInflater) -> ActivityHomeBinding =
        ActivityHomeBinding::inflate

    private var _mMap: GoogleMap? = null

    override fun setup() {
        //add bottom navigation bar and link it with fragments
        addBottomNavigationBar()
//        setupMap()
//        updateUi(DataManager.getCurrentCity())
    }
    private fun addBottomNavigationBar(){
        replaceFragments(mapFragment)
        binding?.bottomNavigation?.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.map_fragment ->{
                    replaceFragments(mapFragment)
                    true
                }
                else -> false
            }
        }

    }

    private fun replaceFragments(fragment: Fragment){
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container, fragment)
            commit()
        }
    }

    override fun addCallbacks() {
//        binding?.iconSearch!!.setOnClickListener {
//            startActivity(Intent(this, SearchActivity::class.java))
//        }

//        binding?.iconNext?.setOnClickListener {
//            updateUi(DataManager.getNextCity())
//        }
//
//        binding?.iconPrevious!!.setOnClickListener {
//            updateUi(DataManager.getPreviousCity())
//        }
//
//        binding?.seeMoreBtn?.setOnClickListener{
//            val intent = Intent(Intent.ACTION_VIEW)
//            intent.data = Uri.parse("https://www.google.com/search?q=${DataManager.getCurrentCity().city}")
//            startActivity(intent)
//        }

    }

//    @SuppressLint("SetTextI18n")
//    private fun updateUi(city: City) {
//        binding?.apply {
//            this.city.text = city.city
//            country.text = city.country
//            capital.text = city.capital
//            population.text = city.population
//            seeMoreBtn.text = "more about ${city.city}"
//        }
//        moveMapCamera(city)
//    }

    private fun moveMapCamera(city: City) {
        //to avoid null value in csv file
        try {
            val cameraPosition = CameraPosition.Builder()
                .target(LatLng(city.lat.toDouble(), city.lng.toDouble()))
                .tilt(20f)
                .zoom(10f)
                .bearing(0f)
                .build()
            _mMap?.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
        }catch (e: NullPointerException){
            Toast.makeText(this, e.message,Toast.LENGTH_SHORT).show()
        }


    }

   //make inflate to map fragment
    private fun setupMap() {
//        val mapFragment = supportFragmentManager
//            .findFragmentById(R.id.mapContainer) as SupportMapFragment
//        mapFragment.getMapAsync(this)
    }

    //make move to address on map
    override fun onMapReady(googleMap: GoogleMap) {
        _mMap = googleMap
        moveMapCamera(DataManager.getCurrentCity())

    }
}