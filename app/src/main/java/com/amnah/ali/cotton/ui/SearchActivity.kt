package com.amnah.ali.cotton.ui

import android.view.LayoutInflater
import com.amnah.ali.cotton.databinding.ActivitySearchBinding

class SearchActivity : BaseActivity<ActivitySearchBinding>() {
    override val LOG_TAG: String = "SEARCH_ACTIVITY"
    override val bindingInflater: (LayoutInflater) -> ActivitySearchBinding =
        ActivitySearchBinding::inflate

    override fun setup() {
    }

    override fun addCallbacks() {
    }
}