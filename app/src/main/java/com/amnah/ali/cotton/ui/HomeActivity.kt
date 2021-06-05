package com.amnah.ali.cotton.ui

import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import com.amnah.ali.cotton.R
import com.amnah.ali.cotton.databinding.ActivityHomeBinding
import com.amnah.ali.cotton.fragments.MapsFragments
import com.amnah.ali.cotton.fragments.ProfileFragment
import com.amnah.ali.cotton.fragments.SearchFragment
import com.google.android.gms.maps.*


class HomeActivity : BaseActivity<ActivityHomeBinding>() {
    //type the content after make override
    override val LOG_TAG: String = "MAIN_ACTIVITY"
    private val _mapFragment = MapsFragments()
    private val _searchFragment = SearchFragment()
    private val _profileFragment = ProfileFragment()

    override val bindingInflater: (LayoutInflater) -> ActivityHomeBinding =
        ActivityHomeBinding::inflate

    override fun setup() {
        addBottomNavigationBar()
    }
    private fun addBottomNavigationBar(){
       replaceFragments(_mapFragment)

        binding?.bottomNav?.setOnItemSelectedListener { item->
            when(item){
                0->{
                    replaceFragments(_mapFragment)
                    true
                }
                1->{
                    replaceFragments(_searchFragment)
                    true
                }
                2->{
                    replaceFragments(_profileFragment)
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


    }

}