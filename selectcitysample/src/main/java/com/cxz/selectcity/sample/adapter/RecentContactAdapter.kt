package com.cxz.selectcity.sample.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.cxz.selectcity.sample.R
import com.cxz.selectcity.sample.bean.User

/**
 * @author chenxz
 * @date 2020/9/18
 * @desc
 */
class RecentContactAdapter : BaseQuickAdapter<User, BaseViewHolder>(R.layout.item_contact_list) {

    override fun convert(holder: BaseViewHolder, item: User) {
        holder.setGone(R.id.catalogTV, true)
            .setText(R.id.usernameTV, item.name)
    }

}