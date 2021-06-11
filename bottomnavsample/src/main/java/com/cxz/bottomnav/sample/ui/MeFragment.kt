package com.cxz.bottomnav.sample.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.cxz.bottomnav.sample.R
import com.cxz.bottomnav.sample.event.MineRedDotEvent
import org.greenrobot.eventbus.EventBus

/**
 * @author chenxz
 * @date 2020/10/24
 * @desc
 */
class MeFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_me, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        view?.findViewById<Button>(R.id.showRedBtn)?.setOnClickListener {
            EventBus.getDefault().post(MineRedDotEvent(true))
        }
        view?.findViewById<Button>(R.id.hideRedBtn)?.setOnClickListener {
            EventBus.getDefault().post(MineRedDotEvent(false))
        }
    }

}
