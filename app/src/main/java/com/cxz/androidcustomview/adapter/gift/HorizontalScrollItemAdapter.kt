package com.cxz.androidcustomview.adapter.gift

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.cxz.androidcustomview.R
import com.cxz.androidcustomview.bean.GiftItemBean

/**
 * @author chenxz
 * @date 2020/9/9
 * @description
 */
class HorizontalScrollItemAdapter : BaseQuickAdapter<GiftItemBean, BaseViewHolder>(R.layout.item_gift_list) {

    override fun convert(holder: BaseViewHolder, item: GiftItemBean) {
        holder.setText(R.id.contentTV, item.content)
    }
}