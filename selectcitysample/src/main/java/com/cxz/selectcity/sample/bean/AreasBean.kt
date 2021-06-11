package com.cxz.selectcity.sample.bean

/**
 * @author chenxz
 * @date 2020/9/17
 * @desc
 */
data class AreasBean(
    var id: Int,
    var name: String,
    var parentId: Int,
    var isHot: Int,
    var children: MutableList<ChildrenBeanX>
)

data class ChildrenBeanX(
    var id: Int,
    var name: String,
    var parentId: Int,
    var isHot: Int,
    var children: MutableList<ChildrenBean>
)

data class ChildrenBean(
    var id: Int,
    var name: String,
    var parentId: Int,
    var isHot: Int
)