package com.amnah.ali.cotton.fragments

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.SearchView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.amnah.ali.cotton.R
import com.amnah.ali.cotton.data.DataManager
import com.amnah.ali.cotton.databinding.FragmentSearchBinding
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable
import java.util.*


class SearchFragment : Fragment() {
    private var _populationList= mutableListOf<Int>()
    var avg=0.0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    lateinit private var binding: FragmentSearchBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binded = FragmentSearchBinding.bind(view)
        binding = binded
        addCallbacks()


    }


    fun addCallbacks() {
        binding!!.apply {
            searchViewCountry.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

                override fun onQueryTextSubmit(query: String?): Boolean {
                       cardError.visibility = View.GONE
                        changeVisibility(true)
                       createChips(query!!.lowercase(Locale.getDefault()))
//                    getDataOfCountry(query!!.lowercase(Locale.getDefault()))
                       if(query.isNullOrEmpty() || DataManager.getCurrentCountry(query)[query].isNullOrEmpty())
                             cardError.visibility = View.VISIBLE


                    return false
                }
                override fun onQueryTextChange(newText: String?): Boolean {
                    chipsCities.removeAllViews()
                    changeVisibility(false)
                    return false
                }
            })
        }

    }

    private fun createChips(country:String){
        _populationList.clear()

        DataManager.getCurrentCountry(country)[country]?.forEach { itForCity ->
            if(itForCity.city.isNotEmpty()) {

                Chip(activity).let {
                    val chipDraw = ChipDrawable.createFromAttributes((activity)!!, null, 0, R.style.Widget_MaterialComponents_Chip_Entry)
                    it.setChipDrawable(chipDraw)
                    it.isCheckable = false
                    it.isClickable = false
                    it.iconStartPadding = 2f
                    it.setPadding(60, 20, 60, 20)
                    it.setTextColor(Color.BLACK)
                    it.setChipBackgroundColorResource(R.color.white)
                    it.setOnCloseIconClickListener {
                        binding.chipsCities.removeView(it)
                    }
                    it.text = itForCity.city
                    //ADD THE SUM OF POPULATION

                    if (itForCity.population.trim().isNotEmpty()) {
                        _populationList.add(itForCity.population.toInt())
                    }
                    binding.chipsCities.addView(it)
                }
            }
            else{
                Toast.makeText(activity,"Not Exist" , Toast.LENGTH_LONG).show()
            }
        }
        //add sum,large, min population in  country
        addSumOfPopulation()


    }
    fun addSumOfPopulation() {
        _populationList.size.lazyLog()

        var sum=  _populationList!!.sum()//sum
        binding.sumPop.text= sum.toString().chunked(3).joinToString (",")
//        var max : Int? =   _populationList.maxOrNull()//max
//        var min : Int? =   _populationList.minOrNull()//min
//        var showMax=   avg(sum.toDouble(),max!!.toDouble())
//        var showMin= avg(sum.toDouble(),min!!.toDouble())
           var percentage= avg(sum.toDouble())
       percentage.toInt().let { binding.minProgressBar?.setProgressWithAnimation(it.toFloat(),1000) }
//       showMax.toInt().let { binding.maxProgressBar?.setProgressWithAnimation(it.toFloat(),1000) }
        percentage=String.format("%.3f", percentage).toDouble()
        binding?.txtPercentage?.text = "$percentage %"

    }

 fun changeVisibility( state:Boolean){
     binding.apply {
         txtNote.isVisible=state
         txtPercentage.isVisible=state
         txtPop.isVisible=state
         sumPop.isVisible=state
         minProgressBar.isVisible=state
         txt.isVisible=!state
         imageView.isVisible=!state

     }
 }
//    fun  getDataOfCountry(country:String) {
//        DataManager.getCurrentCountry(country)[country]?.forEach { itForCountry ->
//            if (itForCountry.country.isNotEmpty()) {
//                itForCountry.city.lazyLog()
//            }
//        }
//    }
    fun avg( count:Double)= ((count?.toDouble()?.div(7000000000
    )))?.times(100)!!.also { avg = it }

    fun <T> T.lazyLog(tag: String = "LAZY_LOG"): T {
        Log.i(tag, toString())
        return this
    }

}