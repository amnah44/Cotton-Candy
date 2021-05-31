package com.amnah.ali.cotton.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding


abstract class BaseFragment<VB:ViewBinding>:Fragment() {
    abstract val LOG_TAG: String
    abstract  val bindingInflater: (LayoutInflater) -> VB
     var _binding: ViewBinding? = null
     var binding: VB?
        get() = _binding as VB?
    set(value) = TODO()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup()
        addCallBack()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bindingInflater(layoutInflater)
        return _binding?.root
    }


    abstract  fun setup()
    abstract  fun addCallBack()
}