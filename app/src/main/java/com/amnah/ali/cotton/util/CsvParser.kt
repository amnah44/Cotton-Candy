package com.amnah.ali.cotton.util

import com.amnah.ali.cotton.data.domain.City

class CsvParser {
    fun parse(line : String) : City {
        val tokens = line.split(",")
        return City(
            city = tokens[Constants.ColumnIndex.CITY],
            country   = tokens[Constants.ColumnIndex.COUNTRY],
            capital  = tokens[Constants.ColumnIndex.CAPITAL],
            population  = tokens[Constants.ColumnIndex.POPULATION],
            lat = tokens[Constants.ColumnIndex.LAT],
            lng = tokens[Constants.ColumnIndex.LNG]
        )
    }

}