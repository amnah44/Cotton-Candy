package com.amnah.ali.cotton.data

import com.amnah.ali.cotton.data.domain.City

object DataManager {
    private val cityList: MutableList<City> = mutableListOf<City>()
    private var index = 0
    fun getCityList() : MutableList<City> = cityList
    fun addCity(city: City){
        cityList.add(city)
    }

    fun getCurrentCity(): City = cityList[index]

    fun getNextCity():City{
        if (index == cityList.size-1){
            index = 0
            return cityList[index]
        }
        index++
        return cityList[index]
    }

    fun getPreviousCity(): City{
        if (index == 0){
            index = cityList.size-1
            return cityList[index]
        }
        index--
        return cityList[index]
    }
}