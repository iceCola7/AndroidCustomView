package com.cxz.androidcustomview.activity

import android.util.Log
import com.cxz.androidcustomview.R
import com.cxz.androidcustomview.adapter.gift.HorizontalScrollAdapter
import com.cxz.androidcustomview.base.BaseActivity
import com.cxz.androidcustomview.bean.GiftItemBean
import kotlinx.android.synthetic.main.activity_gift.*

class GiftActivity : BaseActivity() {

    override fun attachLayoutRes(): Int {
        return R.layout.activity_gift
    }

    override fun initView() {
        val title = intent.getStringExtra("title")
        setToolbarTitle(title)

        val pageSize = 8
        val giftItemList = initData(pageSize)
        val horizontalScrollAdapter = HorizontalScrollAdapter(this, giftItemList)
        horizontalScrollAdapter.setOnItemClickListener {
            Log.e("cxz", "onCreate: ${it.content}")
        }
        viewPager.adapter = horizontalScrollAdapter
        indicator.setViewPager(viewPager)
    }

    private fun initData(pageSize: Int): List<List<GiftItemBean>> {
        val dataList: MutableList<GiftItemBean> = ArrayList()
        val maps: MutableList<List<GiftItemBean>> = ArrayList()

        for (i in 0 until 17) {
            dataList.add(GiftItemBean("gift: $i"))
        }

        var totalPage = dataList.size / pageSize
        if (dataList.size % pageSize != 0) {
            totalPage += 1
        }

        for (j in 0 until totalPage) {
            if (j == totalPage - 1) {
                maps.add(dataList.subList(pageSize * j, dataList.size))
            } else {
                maps.add(dataList.subList(pageSize * j, pageSize * (j + 1)))
            }
        }

        return maps
    }
}