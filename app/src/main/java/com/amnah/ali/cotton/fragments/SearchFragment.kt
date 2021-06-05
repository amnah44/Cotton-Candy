package com.amnah.ali.cotton.fragments

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.core.view.isVisible
import com.amnah.ali.cotton.R
import com.amnah.ali.cotton.data.DataManager
import com.amnah.ali.cotton.data.domain.ChartData
import com.amnah.ali.cotton.databinding.FragmentSearchBinding
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.utils.ColorTemplate
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable
import java.util.*
import kotlin.collections.ArrayList


class SearchFragment : BaseFragment<FragmentSearchBinding>() {
    private var _populationList = mutableListOf<Int>()
    var avg = 0.0
    override val LOG_TAG: String = "search_log"
    override val bindingInflater: (LayoutInflater) -> FragmentSearchBinding =
        FragmentSearchBinding::inflate
    private val chartDataList = mutableListOf<ChartData>()

    protected lateinit var barListChart: ArrayList<BarEntry>
    protected lateinit var arrayListChart: ArrayList<String>
    private lateinit var barDataChart: BarData
    private lateinit var barDataSetChart: BarDataSet


    override fun addCallBack() {
        changeVisibility(false)
    }

    override fun setup() {
        binding!!.apply {
            searchViewCountry.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

                override fun onQueryTextSubmit(query: String?): Boolean {
                    changeVisibility(true)
                    createChips(query!!.lowercase(Locale.getDefault()))
//                    getDataOfCountry(query!!.lowercase(Locale.getDefault()))
                    if (query.isNullOrEmpty() ||
                        DataManager.getCurrentCountry(query.lowercase(Locale.getDefault()))[query.lowercase(
                            Locale.getDefault()
                        )].isNullOrEmpty()
                    ) {
                        error.visibility = View.VISIBLE
                        changeVisibility(false)
                    }
                    imgSearch.isVisible = false
                    anyChartViewSearch.isVisible = true
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    chipsCities.removeAllViews()
                    error.visibility = View.GONE
                    changeVisibility(false)
                    imgSearch.isVisible = true
                    anyChartViewSearch.isVisible = false
                    return false
                }

            })
        }

    }

    private fun createChips(country: String) {
        _populationList.clear()

        DataManager.getCurrentCountry(country)[country]?.forEach { city ->
            if (city.city.isNotEmpty()) {

                Chip(activity).let {
                    val chipDraw =
                        ChipDrawable.createFromAttributes(
                            (activity)!!,
                            null,
                            0,
                            R.style.Widget_MaterialComponents_Chip_Choice
                        )
                    it.setChipDrawable(chipDraw)
                    it.isCheckable = false
                    it.isClickable = false
                    it.iconStartPadding = 2f
                    it.setPadding(60, 20, 60, 20)
                    it.setTextColor(Color.BLACK)
                    it.setChipBackgroundColorResource(R.color.white)
                    it.setOnCloseIconClickListener {
                        binding?.chipsCities?.removeView(it)
                    }
                    it.text = city.city
                    //ADD THE SUM OF POPULATION

                    if (city.population.toString().trim().isNotEmpty()) {
                        _populationList.add(city.population!!)
                    }
                    binding?.chipsCities?.addView(it)
                }
            } else {
                Toast.makeText(activity, "Not Exist", Toast.LENGTH_LONG).show()
            }
        }
        //add sum,large, min population in  country
        addSumOfPopulation()
        viewBarChart(country )
    }

    private fun viewBarChart(countryName: String) {
            arrayListChart = ArrayList()

        barListChart = ArrayList()
            DataManager.getCurrentCountry(countryName.lowercase(Locale.getDefault()))[countryName.lowercase(
                Locale.getDefault()
            )]!!.takeWhile { it.population != 0 }
                .forEachIndexed { i , it ->
                    arrayListChart.add(it.city)
                    barListChart.add(BarEntry(it.population?.toFloat()?:0f, i))
                }
            initChart(countryName , binding!!.anyChartViewSearch)
        }

        private fun initChart(countryName: String, barChart: BarChart) {
            barDataSetChart = BarDataSet(barListChart, countryName)
            barDataChart = BarData(arrayListChart,barDataSetChart)
            barChart.data = barDataChart
            barDataSetChart.setColors(ColorTemplate.PASTEL_COLORS, 240)
            barDataSetChart.valueTextSize = 15f

            barChart.animateXY(2000,2000)

            // define zoom settings
            barChart.setVisibleXRangeMaximum(2f)
        }

    fun addSumOfPopulation() {
        _populationList.size.lazyLog()

        val sum = _populationList.sum()//sum
        binding?.sumPop?.text = sum.toString().chunked(3).joinToString(",")
//        var max : Int? =   _populationList.maxOrNull()//max
//        var min : Int? =   _populationList.minOrNull()//min
//        var showMax=   avg(sum.toDouble(),max!!.toDouble())
//        var showMin= avg(sum.toDouble(),min!!.toDouble())
        var percentage = avg(sum.toDouble())
        percentage.toInt()
            .let { binding?.minProgressBar?.setProgressWithAnimation(it.toFloat(), 500) }
//       showMax.toInt().let { binding.maxProgressBar?.setProgressWithAnimation(it.toFloat(),1000) }
        percentage = String.format("%.3f", percentage).toDouble()
        binding?.txtPercentage?.text = "$percentage %"

    }
    fun changeVisibility( state:Boolean){
        binding?.apply {
            cardId.isVisible = state
            cardPopulation.isVisible = state
            cardProgressBar.isVisible = state
            imgSearch.isVisible = !state
            anyChartViewSearch.isVisible = state

        }
    }


    fun avg(count: Double) = ((count.div(
        7000000000
    ))).times(100).also { avg = it }

    fun <T> T.lazyLog(tag: String = "LAZY_LOG"): T {
        Log.i(tag, toString())
        return this
    }


}