package com.rius.coinunion.binding

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.rius.coinunion.R
import javax.inject.Inject

class FragmentBindingAdapter @Inject constructor(val fragment: Fragment) {

    @BindingAdapter(value = ["imgUrl", "placeHolder"], requireAll = false)
    fun bindImage(iv: ImageView, url: String?, placeHolder: Drawable?) {
        val holder = placeHolder ?: fragment.resources.getDrawable(
            R.mipmap.ic_launcher,
            fragment.requireContext().theme
        )
        Glide.with(fragment).load(url).placeholder(holder).into(iv)
    }
}

