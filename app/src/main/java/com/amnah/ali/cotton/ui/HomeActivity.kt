package com.amnah.ali.cotton.ui

import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import com.amnah.ali.cotton.R
import com.amnah.ali.cotton.data.DataManager
import com.amnah.ali.cotton.databinding.ActivityHomeBinding
import com.amnah.ali.cotton.fragments.MapsFragments
import com.amnah.ali.cotton.util.CsvParser
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader


class HomeActivity : BaseActivity<ActivityHomeBinding>() {
    //type the content after make override
    override val LOG_TAG: String = "MAIN_ACTIVITY"
    val mapFragment = MapsFragments()

    override val bindingInflater: (LayoutInflater) -> ActivityHomeBinding =
        ActivityHomeBinding::inflate


    override fun setup() {
        parseCsvFile()
        //add bottom navigation bar and link it with fragments
        addBottomNavigationBar()

    }

    private fun parseCsvFile() {
        val inputStream: InputStream = assets.open("worldcities.csv")
        val buffer = BufferedReader(InputStreamReader(inputStream))
        val parser = CsvParser()
        buffer.forEachLine { city ->
            val currentCity = parser.parse(city)
            DataManager.addCity(currentCity)
        }
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


    }





}