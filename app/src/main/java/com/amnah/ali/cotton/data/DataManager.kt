package com.amnah.ali.cotton.data

import com.amnah.ali.cotton.data.domain.City

object DataManager {
    private val cityList: MutableList<City> = mutableListOf<City>()


    fun getCityList() : MutableList<City> = cityList

    //Add items to list
    fun addCity(city: City){
        cityList.add(city)
    }


}