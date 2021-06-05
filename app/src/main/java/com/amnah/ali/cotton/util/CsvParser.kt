package com.amnah.ali.cotton.util

import com.amnah.ali.cotton.data.domain.City

class CsvParser {
    //split values to be as a table
    fun parse(line : String) : City {
        val tokens = line.split(",")
        return City(
            city = tokens[Constants.ColumnIndex.CITY],
            country   = tokens[Constants.ColumnIndex.COUNTRY],
            capital  = tokens[Constants.ColumnIndex.CAPITAL],
            population  = tokens[Constants.ColumnIndex.POPULATION].toIntOrNull(),
            lat = tokens[Constants.ColumnIndex.LAT],
            lng = tokens[Constants.ColumnIndex.LNG],
            ios2 = tokens[Constants.ColumnIndex.IOS2],
            ios3 = tokens[Constants.ColumnIndex.IOS3]
        )
    }

}