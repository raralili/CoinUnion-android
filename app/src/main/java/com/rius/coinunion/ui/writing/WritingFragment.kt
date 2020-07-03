package com.rius.coinunion.ui.writing

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.rius.coinunion.R
import javax.inject.Inject

class WritingFragment : Fragment() {

    @Inject lateinit var viewModel: WritingViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.writing_fragment, container, false)
    }
}
