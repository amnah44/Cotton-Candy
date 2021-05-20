package com.amnah.ali.cotton.ui

import android.view.LayoutInflater
import android.widget.ArrayAdapter
import android.widget.SearchView
import android.widget.Toast
import androidx.core.view.isVisible
import com.amnah.ali.cotton.data.DataManager
import com.amnah.ali.cotton.databinding.ActivitySearchBinding

class SearchActivity : BaseActivity<ActivitySearchBinding>() {
    override val LOG_TAG: String = "SEARCH_ACTIVITY"
    override val bindingInflater: (LayoutInflater) -> ActivitySearchBinding =
        ActivitySearchBinding::inflate
    var listOfCountryName = mutableListOf<String>()
    var adapter : ArrayAdapter<String>? =null

    override fun setup() {
        binding?.listView?.isVisible =false

        DataManager.cityList.forEach{
            listOfCountryName.add(it.country)
        }

         adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1,listOfCountryName.distinct())
        binding?.listView?.adapter = adapter

    }

    override fun addCallbacks() {
        binding?.apply{
            searchView.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchView.clearFocus()
                if(listOfCountryName.contains(query)){
                    adapter?.filter?.filter(query)
                }
                else Toast.makeText(applicationContext,"Country not found",Toast.LENGTH_LONG).show()
                return false
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


}