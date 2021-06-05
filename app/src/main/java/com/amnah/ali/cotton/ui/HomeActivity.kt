package com.amnah.ali.cotton.ui

import android.content.Intent
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import com.amnah.ali.cotton.R
import com.amnah.ali.cotton.data.DataManager
import com.amnah.ali.cotton.databinding.ActivityHomeBinding
import com.amnah.ali.cotton.fragments.MapsFragments
import com.amnah.ali.cotton.fragments.ProfileFragment
import com.amnah.ali.cotton.fragments.SearchFragment
import com.amnah.ali.cotton.util.CsvParser
import com.google.android.gms.maps.*
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader


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
        parsCsvFile()
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

    private fun parsCsvFile() {
        val inputStream: InputStream = assets.open("worldcities.csv")
        val buffer = BufferedReader(InputStreamReader(inputStream))
        val parser = CsvParser()
        buffer.forEachLine { city ->
            val currentCity = parser.parse(city)
            DataManager.addCity(currentCity)
        }
    }
}