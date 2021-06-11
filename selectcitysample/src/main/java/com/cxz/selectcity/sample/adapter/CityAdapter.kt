package com.cxz.selectcity.sample.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.cxz.selectcity.sample.R
import com.cxz.selectcity.sample.bean.City

/**
 * @author chenxz
 * @date 2020/9/17
 * @desc
 */
class CityAdapter : BaseQuickAdapter<City, BaseViewHolder>(R.layout.item_city_list) {

    override fun convert(holder: BaseViewHolder, item: City) {
        holder.setText(R.id.cityNameTV, item.name)
    }

}