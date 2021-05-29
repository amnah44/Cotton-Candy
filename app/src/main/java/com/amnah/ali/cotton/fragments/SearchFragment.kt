package com.amnah.ali.cotton.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.amnah.ali.cotton.R
import com.amnah.ali.cotton.data.DataManager
import com.amnah.ali.cotton.databinding.FragmentSearchBinding
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable
import java.util.*


class SearchFragment : Fragment() {


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
                       createChips(query!!.lowercase(Locale.getDefault()))
                       if(query.isNullOrEmpty() || DataManager.getCurrentCountry(query)[query].isNullOrEmpty())
                             cardError.visibility = View.VISIBLE

                    return false
                }
                override fun onQueryTextChange(newText: String?): Boolean {
                    chipsCities.removeAllViews()
                    return false
                }
            })
        }
    }

    private fun createChips(country:String){

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
                    binding.chipsCities.addView(it)
                }
            }
            else{
                Toast.makeText(activity,"Not Exist" , Toast.LENGTH_LONG).show()
            }
        }
    }

}