package com.rius.coinunion.binding

import androidx.databinding.DataBindingComponent
import androidx.fragment.app.Fragment

class FragmentBindingComponent(fragment: Fragment) : DataBindingComponent {

    private val adapter = FragmentBindingAdapter(fragment)

    override fun getFragmentBindingAdapter(): FragmentBindingAdapter = adapter
}