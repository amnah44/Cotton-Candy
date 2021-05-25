package com.amnah.ali.cotton.ui

import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import com.amnah.ali.cotton.R
import com.amnah.ali.cotton.databinding.ActivityHomeBinding
import com.amnah.ali.cotton.fragments.MapsFragments


class HomeActivity : BaseActivity<ActivityHomeBinding>() {
    //type the content after make override
    override val LOG_TAG: String = "MAIN_ACTIVITY"
    val mapFragment = MapsFragments()

    override val bindingInflater: (LayoutInflater) -> ActivityHomeBinding =
        ActivityHomeBinding::inflate


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



}