package com.cxz.bottomnav.sample.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cxz.bottomnav.sample.R

/**
 * @author chenxz
 * @date 2020/10/24
 * @desc
 */
class PartyFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_party, container, false)
    }

}