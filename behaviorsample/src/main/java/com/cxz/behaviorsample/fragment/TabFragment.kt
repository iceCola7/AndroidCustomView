package com.cxz.behaviorsample.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cxz.behaviorsample.R
import kotlinx.android.synthetic.main.fragment_tab.*

/**
 * @author chenxz
 * @date 2020/9/11
 * @desc
 */
class TabFragment : Fragment() {

    private var name: String = ""

    companion object {
        fun newInstance(name: String): Fragment {
            val args = Bundle()
            args.putString("data", name)
            val fragment = TabFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        name = arguments?.getString("data") ?: ""
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_tab, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tv_fragment_tab_text.text = name
    }
}