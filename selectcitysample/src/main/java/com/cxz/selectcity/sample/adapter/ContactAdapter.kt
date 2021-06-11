package com.cxz.selectcity.sample.adapter

import android.graphics.Color
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.cxz.selectcity.sample.R
import com.cxz.selectcity.sample.bean.User

/**
 * @author chenxz
 * @date 2020/9/17
 * @desc
 */
class ContactAdapter : BaseQuickAdapter<User, BaseViewHolder>(R.layout.item_contact_list) {

    override fun convert(holder: BaseViewHolder, item: User) {
        val catalog = item.firstLetter
        if (getItemPosition(item) == getPositionForSection(catalog)) {
            if (item.isSelected) {
                holder.setTextColor(R.id.catalogTV, Color.parseColor("#14D7DC"))
            } else {
                holder.setTextColor(R.id.catalogTV, Color.parseColor("#0F0028"))
            }
            holder.setGone(R.id.catalogTV, false)
                .setText(R.id.catalogTV, catalog)
        } else {
            holder.setGone(R.id.catalogTV, true)
        }
        holder.setText(R.id.usernameTV, item.name)
    }

    /**
     * 获取catalog首次出现位置
     */
    fun getPositionForSection(catalog: String): Int {
        var count = itemCount
        if (hasHeaderLayout()) {
            count -= headerLayoutCount
        }
        for (i in 0 until count) {
            val sortStr: String = data[i].firstLetter
            if (catalog.equals(sortStr, ignoreCase = true)) {
                return i
            }
        }
        return -1
    }

    fun changeSelectedText(position: Int) {
        data.forEachIndexed { index, user ->
            user.isSelected = position == index
        }
        notifyDataSetChanged()
    }

}