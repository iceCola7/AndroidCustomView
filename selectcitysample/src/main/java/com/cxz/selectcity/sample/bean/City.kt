package com.cxz.selectcity.sample.bean

/**
 * @author chenxz
 * @date 2020/9/17
 * @desc
 */
data class City(
    var id: Int,
    var name: String,
    var pinyin: String,
    var isHot: Boolean = false
)