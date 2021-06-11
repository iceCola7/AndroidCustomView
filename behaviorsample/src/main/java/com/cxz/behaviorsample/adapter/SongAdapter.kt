package com.cxz.behaviorsample.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.cxz.behaviorsample.R

/**
 * @author chenxz
 * @date 2020/9/11
 * @desc
 */
class SongAdapter : BaseQuickAdapter<Int, BaseViewHolder>(R.layout.item_song_list) {

    override fun convert(holder: BaseViewHolder, item: Int) {
        holder.setText(R.id.tv_item_muisc_no, "${item + 1}")
    }

}