package com.amnah.ali.cotton.data

import com.amnah.ali.cotton.data.domain.City

object DataManager {
    val _cityList: MutableList<City> = mutableListOf<City>()
    private var _index = 0
    fun getCityList() : MutableList<City> = _cityList

    //Add items to list
    fun addCity(city: City){
        _cityList.add(city)
    }

    fun getCurrentCity(): City = _cityList[_index]

    //check to get next item in list
    fun getNextCity():City{
        if (_index == _cityList.size-1){
            _index = 0
//            return _cityList[_index]
        }
        _index++
        return _cityList[_index]
    }

    //check to get previous item in list
    fun getPreviousCity(): City{
        if (_index == 0){
            _index = _cityList.size-1
            return _cityList[_index]
        }
        _index--
        return _cityList[_index]
    }
}