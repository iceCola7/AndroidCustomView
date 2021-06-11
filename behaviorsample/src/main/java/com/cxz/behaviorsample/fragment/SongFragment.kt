package com.cxz.behaviorsample.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.cxz.behaviorsample.R
import com.cxz.behaviorsample.adapter.SongAdapter
import kotlinx.android.synthetic.main.fragment_song.*

/**
 * @author chenxz
 * @date 2020/9/11
 * @desc
 */
class SongFragment : Fragment() {

    private var songAdapter: SongAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_song, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun initView() {
        songAdapter = SongAdapter()
        songAdapter?.setList(initData())
        recyclerView?.layoutManager = LinearLayoutManager(requireContext())
        recyclerView?.adapter = songAdapter
    }

    private fun initData(): MutableList<Int> {
        val list = mutableListOf<Int>()
        for (i in 0..32) {
            list.add(i)
        }
        return list
    }
}