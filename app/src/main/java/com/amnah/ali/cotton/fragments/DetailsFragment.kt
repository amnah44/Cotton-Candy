package com.amnah.ali.cotton.fragments

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.amnah.ali.cotton.R
import com.amnah.ali.cotton.data.DataManager
import com.amnah.ali.cotton.data.domain.City
import com.amnah.ali.cotton.databinding.FragmentDetailsBinding
import com.amnah.ali.cotton.util.Constants
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.cronet.CronetHttpStack
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.text.DecimalFormat
import java.util.*
import kotlin.collections.ArrayList

@Suppress("DEPRECATION")
class DetailsFragment() : BaseFragment<FragmentDetailsBinding>() {
    override val LOG_TAG: String = "DETAILS_LOG"
    lateinit var city: City
    override val bindingInflater: (LayoutInflater) -> FragmentDetailsBinding =
        FragmentDetailsBinding::inflate


    private val url = "http://api.openweathermap.org/data/2.5/weather"
    private val appid = "b6826f3094b97c57aefce72d798e1ada"
    var df = DecimalFormat("#.##")

    override fun setup() {  }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
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
            loadPieChart(country, city,population )
        }

    override fun addCallBack() {
        binding!!.apply {
            back.setOnClickListener{
                loadFragments(MapsFragments())

            }
        }
    }

    private fun loadFragments(fragment: Fragment) {
        (activity)!!.supportFragmentManager.beginTransaction().apply {
            add(R.id.fragment_container, fragment).addToBackStack(null)
            commit()
        }
    }
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

        dataSet.setColors(Color.rgb(75, 162, 247),Color.rgb(243, 164, 111),250)
        dataSet.valueTextSize = 12f
        dataSet.valueTextColor = Color.DKGRAY
        val piaData = PieData(dataSet)

        binding!!.pieChart.apply {
            data = piaData
            description.isEnabled = false
//            description.text = "City Population"
//            description.setTextSize(12f)
//            description.textColor = Color.DKGRAY
//            legend.textColor = Color.DKGRAY
            legend.textSize = 12f
            setEntryLabelColor(Color.DKGRAY)
//            setEntryLabelTextSize(12f)
//            setCenterTextColor(Color.DKGRAY)
//            setCenterText("Population")
            animate()
        }
    }

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