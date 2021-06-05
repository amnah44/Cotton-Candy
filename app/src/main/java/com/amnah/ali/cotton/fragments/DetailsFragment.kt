package com.amnah.ali.cotton.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.amnah.ali.cotton.data.DataManager
import com.amnah.ali.cotton.data.domain.City
import com.amnah.ali.cotton.databinding.FragmentDetailsBinding
import com.amnah.ali.cotton.util.Constants
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.anychart.AnyChart
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.anychart.enums.Align
import com.anychart.enums.LegendLayout
import org.json.JSONException
import org.json.JSONObject
import java.text.DecimalFormat
import java.util.*


class DetailsFragment() : BaseFragment<FragmentDetailsBinding>() {
    override val LOG_TAG: String = "DETAILS_LOG"
    lateinit var city: City
    override val bindingInflater: (LayoutInflater) -> FragmentDetailsBinding =
        FragmentDetailsBinding::inflate


    private val url = "http://api.openweathermap.org/data/2.5/weather"
    private val appid = "b6826f3094b97c57aefce72d798e1ada"
    var df = DecimalFormat("#.##")

    // var pie = AnyChart.pie()


    override fun setup() {
        binding!!.back.setOnClickListener { backToHomeFragment(MapsFragments()) }
    }
    private fun backToHomeFragment(fragment: Fragment) {
        requireActivity().onBackPressed()
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
            getWeatherDetails(country!!.lowercase(Locale.ROOT))
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
            //loadPieChart(country, city,population )
        }

    override fun addCallBack() {
    }

//    private fun loadPieChart(
//        country: String?,
//        city: String?,
//        population: String?,
//    ){
//        val data: MutableList<DataEntry> = ArrayList()
//        data.add(ValueDataEntry(city, population!!.toInt()))
//        data.add(ValueDataEntry(country, DataManager.getPopulationOfCountry(country!!)))
//
//        pie.data(data)
//        pie.title("Population")
//
////        pie.labels().position("outside")
//        pie.legend().title().enabled(true)
//        pie.legend().title()
//            .text("Retail channels")
//            .padding(0.0, 0.0, 10.0, 0.0)
//
//        pie.legend()
//            .position("center-bottom")
//            .itemsLayout(LegendLayout.HORIZONTAL)
//            .align(Align.CENTER)
//
//        binding!!.anyChartView.setChart(pie)
//    }

    fun getWeatherDetails(city: String) {
        val tempUrl = "$url?q=$city&appid=$appid"

        val stringRequest = StringRequest(Request.Method.POST,
            tempUrl, { response ->
                try {
                    val jsonObjectMain: JSONObject = JSONObject(response).getJSONObject("main")
                    val temp: Double = jsonObjectMain.getDouble("temp") - 273.15

                    binding!!.temp.text = df.format(temp)

                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }, {
                Toast.makeText((activity), it.toString(), Toast.LENGTH_LONG).show()
            })

        val requestQueue: RequestQueue = Volley.newRequestQueue((activity)!!.applicationContext)
        requestQueue.add(stringRequest)
    }

}