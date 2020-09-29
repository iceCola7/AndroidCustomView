package com.cxz.androidcustomview.adapter.gift

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.ViewGroup
import com.cxz.androidcustomview.bean.GiftItemBean

/**
 * @author chenxz
 * @date 2020/9/9
 * @description
 */
class HorizontalScrollAdapter : PagerAdapter {

    private var context: Context? = null

    private var maps: List<List<GiftItemBean>>? = null

    constructor (context: Context?, maps: List<List<GiftItemBean>>?) {
        this.context = context
        this.maps = maps
    }

    private var onItemClick: ((GiftItemBean) -> Unit)? = null

    fun setOnItemClickListener(onItemClick: ((GiftItemBean) -> Unit)?) {
        this.onItemClick = onItemClick
    }

    /**
     * 决定了有多少页
     *
     * @return
     */
    override fun getCount(): Int {
        return maps!!.size
    }

    override fun isViewFromObject(view: View, o: Any): Boolean {
        return view === o
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val recyclerView = RecyclerView(context!!)
        val layoutManager = GridLayoutManager(context, 4, GridLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = layoutManager
        val itemAdapter = HorizontalScrollItemAdapter()
        itemAdapter.setNewData(maps!![position])
        itemAdapter.setOnItemClickListener { adapter, view, position ->
            val item = adapter.data[position] as GiftItemBean
            Log.e("cxz", "instantiateItem: $position,${item.content}")
            onItemClick?.invoke(item)
        }
        recyclerView.adapter = itemAdapter
        // 将recyclerView作为子视图加入 container即为viewpager
        container.addView(recyclerView)
        return recyclerView
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}