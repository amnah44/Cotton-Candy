package com.amnah.ali.cotton.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SearchView
import android.widget.Toast
import androidx.core.view.isVisible
import com.amnah.ali.cotton.data.DataManager
import com.amnah.ali.cotton.data.domain.City
import com.amnah.ali.cotton.databinding.ActivitySearchBinding
import com.amnah.ali.cotton.util.MyValueFormaatter1
import com.amnah.ali.cotton.util.MyValueFormatter
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry

class SearchActivity : BaseActivity<ActivitySearchBinding>() {
    override val LOG_TAG: String = "SEARCH_ACTIVITY"
    override val bindingInflater: (LayoutInflater) -> ActivitySearchBinding =
        ActivitySearchBinding::inflate

    //get list and array for values of search
    var listOfCountryName = mutableListOf<String>()
    private var _cityListItem = arrayListOf<String>()
    private var _populationList = mutableListOf<String>()
    var adapter: ArrayAdapter<String>? = null
    private val _populationDataList = arrayListOf<BarEntry>()
    private var _cityList: MutableList<City> = mutableListOf<City>()

    override fun setup() {
        //disappear for list if there is no value in search box
        binding?.listView?.isVisible = false
        _cityList = DataManager.getCityList()

        //make loop to check all item
        _cityList.forEach {
            listOfCountryName.add(it.country)
        }
        adapter =
            ArrayAdapter(this, android.R.layout.simple_list_item_1, listOfCountryName.distinct())
        binding?.listView?.adapter = adapter

        showChart()
    }


    //clear old value after finish the search
    fun clearLists() {
        //clear lists
        _cityListItem.clear()
        _populationDataList.clear()
    }

    //show chart for population of country
    fun showChart() {
        binding?.listView?.onItemClickListener =
            AdapterView.OnItemClickListener { adapterView, _, i, _ ->
                clearLists()

                //make filter to make values as table
                val x = _cityList.filter {
                    it.country == adapterView.getItemAtPosition(i).toString()
                }
                x.forEach {
                    _cityListItem.add(it.city)
                    _populationList.add(it.population)
                    //set the select country in search view
                    binding?.searchView?.setQuery(it.country, false)

                }
                //show chart and hide list after execute the search function
                binding?.listView?.isVisible = false
                binding?.barChart?.isVisible = true

                chart()
            }

    }

    @SuppressLint("ClickableViewAccessibility")
    override fun addCallbacks() {
        binding?.apply {
            //add set query listener to search box
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    searchView.clearFocus()

                    return if (listOfCountryName.contains(query)) {
                        adapter?.filter?.filter(query)

                        true
                    } else {
                        Toast.makeText(applicationContext, "Country not found", Toast.LENGTH_LONG)
                            .show()
                        false
                    }
                    return true
                }
                //add query text change to this listener
                override fun onQueryTextChange(newText: String?): Boolean {
                    listView.isVisible = true
                    barChart.isVisible = false
                    adapter?.filter?.filter(newText)
                    if (newText == "") listView.isVisible = false
                    return false
                }
            })
        }
    }
    //get all population for cities in some country
    fun getPopulation() {
        // solve the wasted data in population
        for (i in 0 until _cityListItem.size) {
            if (_populationList[i].trim().isNotEmpty()) {
                _populationDataList.add(BarEntry(i.toFloat() + 1, _populationList[i].toFloat()))
            } else {
                _populationList[i] = "0"
                _populationDataList.add(BarEntry(i.toFloat() + 1, _populationList[i].toFloat()))
                Toast.makeText(this, "the 0 in some city mean data not fond", Toast.LENGTH_SHORT)
                    .show()
            }
        }

    }

    //add Chart
    private fun chart() {
        getPopulation()
        val barDataSet = BarDataSet(_populationDataList, "no. of Population")
        barDataSet.valueFormatter = MyValueFormaatter1.MyValueFormatter1()

        barDataSet.valueTextSize = 5f
        val barData = BarData(barDataSet)
        val left: YAxis = binding?.barChart!!.axisLeft
        left.axisMinimum = 0f

        binding?.apply {
            barChart.animateY(500)
            barChart.data = barData
            barChart.description.text = ""
        }


        val bottomAxis: XAxis = binding?.barChart!!.xAxis

        binding?.barChart!!.xAxis.setDrawGridLines(false)

        val rightYAxis: YAxis = binding?.barChart!!.axisRight
        rightYAxis.isEnabled = false
        rightYAxis.setDrawGridLines(false)

        val liftYAxis: YAxis = binding?.barChart!!.axisLeft
        liftYAxis.isEnabled = false
        liftYAxis.setDrawGridLines(false)

        bottomAxis.setLabelCount(_populationDataList.size, true)
        bottomAxis.position = XAxis.XAxisPosition.BOTTOM

        bottomAxis.valueFormatter = MyValueFormatter.MyValueFormatter(_cityListItem)


    }


}