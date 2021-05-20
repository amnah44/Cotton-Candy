package com.amnah.ali.cotton.ui

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.widget.*
import androidx.core.view.isVisible
import com.amnah.ali.cotton.data.DataManager
import com.amnah.ali.cotton.data.domain.City
import com.amnah.ali.cotton.databinding.ActivitySearchBinding
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import java.text.DecimalFormat

class SearchActivity : BaseActivity<ActivitySearchBinding>() {
    override val LOG_TAG: String = "SEARCH_ACTIVITY"
    override val bindingInflater: (LayoutInflater) -> ActivitySearchBinding =
        ActivitySearchBinding::inflate
    var listOfCountryName = mutableListOf<String>()
    var cityListItem  = arrayListOf<String>()
    var populationList = mutableListOf<String>()
    var adapter : ArrayAdapter<String>? =null
    private var cityList: MutableList<City> = mutableListOf<City>()

    override fun setup() {
        binding?.listView?.isVisible =false
        cityList = DataManager.getCityList()
        cityList.forEach{
            listOfCountryName.add(it.country)
        }
//       var t = DataManager.cityList[1]
         adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1,listOfCountryName.distinct())
        binding?.listView?.adapter = adapter

        showChart()
    }



    fun showChart(){
        binding?.listView?.onItemClickListener = AdapterView.OnItemClickListener { adapterView, _, i, _ ->

//            Log.i("tag", adapterView.getItemAtPosition(i).toString())
            var x = cityList.filter{
                it.country == adapterView.getItemAtPosition(i).toString()

            }
            Log.i("tag", x.toString())
            x.forEach{
                 cityListItem.add(it.city)
                 populationList.add(it.population)
            }
            binding?.listView?.isVisible = false

            chart()
        }

    }




    @SuppressLint("ClickableViewAccessibility")
    override fun addCallbacks() {
        binding?.apply{
            searchView.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchView.clearFocus()

                if(listOfCountryName.contains(query)){
                     adapter?.filter?.filter(query)

                    return true
                }
                else {
                    Toast.makeText(applicationContext,"Country not found",Toast.LENGTH_LONG).show()
                    return false
                }
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                listView.isVisible = true
                adapter?.filter?.filter(newText)
                if (newText == "") listView.isVisible = false
                return false
            }
        })


        }
    }







    /////add Chart

    private fun chart() {

        val populationDataList = arrayListOf<BarEntry>()


        for (i in 0 until cityListItem.size){
            populationDataList.add(BarEntry(i.toFloat()+1,populationList[i].toFloat()))
        }

        val barDataSet = BarDataSet(populationDataList, "no. of Population")
        barDataSet.valueFormatter = MyValueFormatter1()

        barDataSet.valueTextSize = 11f
        //       barDataSet.setColors(*ColorTemplate.MATERIAL_COLORS)
        val barData = BarData(barDataSet)
        val left: YAxis = binding?.barChart!!.getAxisLeft()
        left.axisMinimum = 0f

        binding?.apply {
            barChart.animateY(500)
          //  barChart.setFitBars(true)
            barChart.data = barData
//            barChart.setDrawValueAboveBar(false)
            barChart.description.text = ""
        }




        val bottomAxis: XAxis = binding?.barChart!!.getXAxis()

        binding?.barChart!!.getXAxis().setDrawGridLines(false)

        val rightYAxis: YAxis = binding?.barChart!!.getAxisRight()
        rightYAxis.isEnabled = false
        rightYAxis.setDrawGridLines(false)

        val liftYAxis: YAxis = binding?.barChart!!.axisLeft
        liftYAxis.isEnabled = false
        liftYAxis.setDrawGridLines(false)

        bottomAxis.setLabelCount(populationDataList.size, true)
        bottomAxis.position = XAxis.XAxisPosition.BOTTOM

        bottomAxis.valueFormatter = MyValueFormatter(cityListItem)
        cityListItem.clear()
        populationList.clear()

}
class MyValueFormatter(val xValsDateLabel: ArrayList<String>) : ValueFormatter() {
    override fun getFormattedValue(value: Float): String {
        return value.toString()
    }
    override fun getAxisLabel(value: Float, axis: AxisBase): String {
        if (value.toInt() >= 0 && value.toInt() <= xValsDateLabel.size - 1) {
            return xValsDateLabel[value.toInt()]
        } else {
            return ("").toString()
        }
    }
}

class MyValueFormatter1 : ValueFormatter() {
    private val format = DecimalFormat("#.##")

    override fun getBarLabel(barEntry: BarEntry?): String {
        return format.format(barEntry?.y)
    }
}


}