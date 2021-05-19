package com.amnah.ali.cotton.ui

import android.content.Intent
import android.view.LayoutInflater
import com.amnah.ali.cotton.data.DataManager
import com.amnah.ali.cotton.databinding.ActivitySplashBinding
import com.amnah.ali.cotton.util.CsvParser
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader


class SplashActivity : BaseActivity<ActivitySplashBinding>() {
    override val LOG_TAG: String = "SEARCH_ACTIVITY"
    override val bindingInflater: (LayoutInflater) -> ActivitySplashBinding =
        ActivitySplashBinding::inflate

    override fun setup() {
        parsCsvFile()
    }

    private fun parsCsvFile() {
        val inputStream: InputStream = assets.open("worldcities.csv")
        val buffer  = BufferedReader(InputStreamReader(inputStream))
        val parser = CsvParser()
        buffer.forEachLine {city ->
            val currentCity = parser.parse(city)
            DataManager.addCity(currentCity)
            log(city)
        }
        log("Done")
//        Thread.sleep(1000)
        startActivity(Intent(this, HomeActivity::class.java))
        this.finish()
    }

    override fun addCallbacks() {
    }
}