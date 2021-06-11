package com.cxz.bottomnav.sample.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.cxz.bottomnav.sample.R
import com.cxz.bottomnav.sample.event.MessageRedDotEvent
import org.greenrobot.eventbus.EventBus

/**
 * @author chenxz
 * @date 2020/10/24
 * @desc
 */
class MessageFragment : Fragment() {

    private var msgCount = 90

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_message, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        view?.findViewById<Button>(R.id.showRedBtn)?.setOnClickListener {
            EventBus.getDefault().post(MessageRedDotEvent(msgCount++))
        }
        view?.findViewById<Button>(R.id.hideRedBtn)?.setOnClickListener {
            msgCount = 90
            EventBus.getDefault().post(MessageRedDotEvent(-1))
        }
    }

}