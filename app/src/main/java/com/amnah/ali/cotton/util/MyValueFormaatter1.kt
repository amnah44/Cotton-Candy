package com.amnah.ali.cotton.util

import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import java.text.DecimalFormat

class MyValueFormaatter1 {
    class MyValueFormatter1 : ValueFormatter() {
        private val format = DecimalFormat("#.##")

        override fun getBarLabel(barEntry: BarEntry?): String {
            return format.format(barEntry?.y)
        }
    }



}