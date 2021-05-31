package com.amnah.ali.cotton.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import com.amnah.ali.cotton.data.domain.City
import com.amnah.ali.cotton.databinding.FragmentDetailsBinding
import com.amnah.ali.cotton.util.Constants
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate

class DetailsFragment() : BaseFragment<FragmentDetailsBinding>() {
    override val LOG_TAG: String = "DETAILS_LOG"
    lateinit var city: City
    override val bindingInflater: (LayoutInflater) -> FragmentDetailsBinding =
        FragmentDetailsBinding::inflate

    lateinit var pieChart: PieChart

    override fun setup() {
        loadPieChart()
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
//            populationBox.text = population
            longitude.text = lng
            latitude.text = lat
        }

    override fun addCallBack() {

    }

    private fun loadPieChart(){

       val arrayListChart:ArrayList<PieEntry> = ArrayList()
        arrayListChart.add(PieEntry(45f ,"baghdad"))
        arrayListChart.add(PieEntry(100f ,"Iraq"))

        val dataSet =
            PieDataSet(arrayListChart , "Population")

        dataSet.setColors(ColorTemplate.PASTEL_COLORS,240)
        dataSet.valueTextSize = 18f
        val piaData = PieData(dataSet)


        binding!!.pieChart.apply {
            data = piaData
            description.isEnabled = true
            description.text = "City Population"
            description.setTextSize(17f)
            setEntryLabelColor(Color.BLACK)
            setCenterText("Population")
            animate()

        }

    }


}