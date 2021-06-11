package com.cxz.selectcity.sample.utils

import android.text.TextUtils
import net.sourceforge.pinyin4j.PinyinHelper
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType
import java.util.regex.Pattern

/**
 * @author chenxz
 * @date 2020/9/17
 * @desc
 */
object PinyinUtil {

    /**
     * 获取拼音的首字母（大写）
     * @param pinyin String
     */
    fun getFirstLetter(pinyin: String): String {
        if (TextUtils.isEmpty(pinyin)) return "定位"
        val c = pinyin.substring(0, 1)
        val pattern = Pattern.compile("^[A-Za-z]+$")
        if (pattern.matcher(c).matches()) {
            return c.toUpperCase()
        } else if ("0" == c) {
            return "定位"
        } else if ("1" == c) {
            return "热门"
        }
        return "定位"
    }

    var sb = StringBuffer()

    /**
     * 获取汉字字符串的首字母，英文字符不变
     * 例如：阿飞→af
     * @param chines String
     * @return String
     */
    fun getPinYinHeadChar(chines: String): String {
        sb.setLength(0)
        val chars = chines.toCharArray()
        val defaultFormat = HanyuPinyinOutputFormat()
        defaultFormat.caseType = HanyuPinyinCaseType.LOWERCASE
        defaultFormat.toneType = HanyuPinyinToneType.WITHOUT_TONE
        for (i in chars.indices) {
            if (chars[i] > 128.toChar()) {
                try {
                    sb.append(PinyinHelper.toHanyuPinyinStringArray(chars[i], defaultFormat)[0][0])
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            } else {
                sb.append(chars[i])
            }
        }
        return sb.toString()
    }

    /**
     * 获取汉字字符串的第一个字母
     */
    fun getPinYinFirstLetter(str: String): String {
        sb.setLength(0)
        val c = str[0]
        val pinyinArray = PinyinHelper.toHanyuPinyinStringArray(c)
        if (pinyinArray != null) {
            sb.append(pinyinArray[0][0])
        } else {
            sb.append(c)
        }
        return sb.toString()
    }

    /**
     * 获取汉字字符串的汉语拼音，英文字符不变
     */
    fun getPinYin(chines: String): String {
        sb.setLength(0)
        val nameChar = chines.toCharArray()
        val defaultFormat = HanyuPinyinOutputFormat()
        defaultFormat.caseType = HanyuPinyinCaseType.LOWERCASE
        defaultFormat.toneType = HanyuPinyinToneType.WITHOUT_TONE
        for (i in nameChar.indices) {
            if (nameChar[i] > 128.toChar()) {
                try {
                    sb.append(PinyinHelper.toHanyuPinyinStringArray(nameChar[i], defaultFormat)[0])
                } catch (e: java.lang.Exception) {
                    // e.printStackTrace()
                }
            } else {
                sb.append(nameChar[i])
            }
        }
        return sb.toString()
    }
}