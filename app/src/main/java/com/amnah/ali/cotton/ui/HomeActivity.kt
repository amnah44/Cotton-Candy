package com.amnah.ali.cotton.ui

import android.view.LayoutInflater
import com.amnah.ali.cotton.databinding.ActivityHomeBinding
import java.io.BufferedReader
import java.io.InputStreamReader

class HomeActivity : BaseActivity<ActivityHomeBinding>() {
    override val LOG_TAG: String = "MAIN_ACTIVITY"
    override val bindingInflater: (LayoutInflater) -> ActivityHomeBinding =
        ActivityHomeBinding::inflate

    override fun setup() {

    }

    override fun addCallbacks() {
    }


}