package com.amnah.ali.cotton.data

import com.amnah.ali.cotton.data.domain.City
import java.util.Locale.*

object DataManager {
    val _cityList: MutableList<City> = mutableListOf<City>()
    private var _index = 0
    fun getCityList() : MutableList<City> = _cityList

    //Add items to list
    fun addCity(city: City){
        _cityList.add(city)
    }

    fun getCurrentCountry(country:String): Map<String, MutableList<City>> =
        _cityList.let {
            var listOfObjCity = it.filter { it.country.lowercase(getDefault())
                .equals(country.lowercase(getDefault()), ignoreCase = true) }.toMutableList()
            it.associateBy(keySelector = { country.lowercase(getDefault()) }, valueTransform = { listOfObjCity }) }

    fun getPopulationOfCountry(country:String): Float {
        return  getCurrentCountry(country)[country]
            ?.filter { it.population != null }?.sumOf {
            it.population!!
        }!!.toFloat()
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
//    fun getPreviousCity(): City{
//        if (_index == 0){
//            _index = _cityList.size-1
//            return _cityList[_index]
//        }
//        _index--
//        return _cityList[_index]
//    }
}