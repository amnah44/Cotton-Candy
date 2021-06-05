package com.amnah.ali.cotton.ui

import com.amnah.ali.cotton.data.domain.City

interface CitiesInteractionListener {

    fun onClickItem(city:City)
    fun onLocationClicked(city: City)
}