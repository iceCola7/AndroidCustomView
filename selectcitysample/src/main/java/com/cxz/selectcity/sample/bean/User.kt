package com.cxz.selectcity.sample.bean

import com.cxz.selectcity.sample.utils.PinyinUtil

/**
 * @author chenxz
 * @date 2020/9/17
 * @desc
 */
open class User : Comparable<User> {

    var name: String = ""
    var pinyin: String = ""

    // 拼音的首字母
    var firstLetter: String = ""
    var isSelected: Boolean = false

    constructor(name: String) {
        this.name = name
        this.pinyin = PinyinUtil.getPinYin(name)
        this.firstLetter = this.pinyin.substring(0, 1).toUpperCase()
        if (!this.firstLetter.matches(Regex("[A-Z]"))) {
            // 如果不在A-Z中则默认为“#”
            this.firstLetter = "#"
        }
    }

    override fun compareTo(other: User): Int {
        return if (firstLetter == "#" && other.firstLetter != "#") {
            1
        } else if (firstLetter != "#" && other.firstLetter == "#") {
            -1
        } else {
            pinyin.compareTo(other.pinyin, ignoreCase = true)
        }
    }
}
