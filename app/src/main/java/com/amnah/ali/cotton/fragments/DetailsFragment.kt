package com.amnah.ali.cotton.fragments

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import com.amnah.ali.cotton.data.DataManager
import com.amnah.ali.cotton.data.domain.City
import com.amnah.ali.cotton.databinding.FragmentDetailsBinding
import com.amnah.ali.cotton.util.Constants
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import java.util.*
import kotlin.collections.ArrayList

class DetailsFragment() : BaseFragment<FragmentDetailsBinding>() {
    override val LOG_TAG: String = "DETAILS_LOG"
    lateinit var city: City
    override val bindingInflater: (LayoutInflater) -> FragmentDetailsBinding =
        FragmentDetailsBinding::inflate

    override fun setup() {
    }

    override fun onStart() {
        super.onStart()
        //use try/catch to avoid crash after click on card view in maps fragments
        try {
            getArguments(arguments)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    //get data from cardView in MapsFragments
    private fun getArguments(arg: Bundle?) =
        arg?.let {
            val city = it.getString(Constants.Key.CITY)
            val country = it.getString(Constants.Key.COUNTRY)
            val population = it.getString(Constants.Key.POPULATION)
            val lat = it.getString(Constants.Key.LAT)
            val lng = it.getString(Constants.Key.LNG)
            bindCities(city, country, population, lat, lng)
        }

    //select data for all views
    private fun bindCities(
        city: String?,
        country: String?,
        population: String?,
        lat: String?,
        lng: String?
    ) =
        binding?.apply {
            citiesBox.text = city
            countryBox.text = country
            populationBox.text = population!!.chunked(3).joinToString (",")
            longitude.text = lng
            latitude.text = lat
            loadPieChart(country, city,population )
        }

    override fun addCallBack() {  }

    private fun loadPieChart(
        country: String?,
        city: String?,
        population: String?,
    ){
       val arrayListChart:ArrayList<PieEntry> = ArrayList()
        arrayListChart.add(PieEntry(DataManager.getPopulationOfCountry(country!!.lowercase(Locale.getDefault())),country))
        arrayListChart.add(PieEntry(population!!.toFloat() ,city))

        val dataSet =
            PieDataSet(arrayListChart , "Population")

        dataSet.setColors(Color.rgb(102, 179, 255),Color.rgb(255, 194, 153),240)
        dataSet.valueTextSize = 10f
        dataSet.valueTextColor = Color.DKGRAY
        val piaData = PieData(dataSet)

        binding!!.pieChart.apply {
            data = piaData
            description.isEnabled = false
//            description.text = "City Population"
//            description.textColor = Color.DKGRAY
//            legend.textColor = Color.DKGRAY
            legend.textSize = 10f
            description.setTextSize(12f)
            setEntryLabelColor(Color.DKGRAY)
            setEntryLabelTextSize(12f)
            setCenterTextColor(Color.DKGRAY)
            setCenterText("Population")
            animate()

        }

    }


}