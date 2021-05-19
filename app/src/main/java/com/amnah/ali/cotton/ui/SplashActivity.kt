package com.amnah.ali.cotton.ui

import android.view.LayoutInflater
import com.amnah.ali.cotton.databinding.ActivitySplashBinding


class SplashActivity : BaseActivity<ActivitySplashBinding>() {
    override val LOG_TAG: String = "SEARCH_ACTIVITY"
    override val bindingInflater: (LayoutInflater) -> ActivitySplashBinding =
        ActivitySplashBinding::inflate

    override fun setup() {
    }

    override fun addCallbacks() {
    }
}