package com.cxz.selectcity.sample.bean

/**
 * @author chenxz
 * @date 2020/9/17
 * @desc
 */
data class CityPickerBean(
    var data: DataBean
): BaseBean()

data class DataBean(
    var areas: MutableList<AreasBean>
)