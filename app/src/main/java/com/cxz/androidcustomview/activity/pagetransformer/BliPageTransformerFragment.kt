package com.cxz.androidcustomview.activity.pagetransformer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cxz.androidcustomview.R
import kotlinx.android.synthetic.main.fragment_bli_page_transformer.*

class BliPageTransformerFragment(private var type: Int) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_bli_page_transformer, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        when (type) {
            0 -> ll_content.setBackgroundColor(resources.getColor(R.color.teal_200))
            1 -> ll_content.setBackgroundColor(resources.getColor(R.color.teal_700))
            2 -> ll_content.setBackgroundColor(resources.getColor(R.color.purple_200))
            3 -> ll_content.setBackgroundColor(resources.getColor(R.color.purple_700))
        }

    }

}