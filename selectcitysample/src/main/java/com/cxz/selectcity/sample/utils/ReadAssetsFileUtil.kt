package com.cxz.selectcity.sample.utils

import android.content.Context
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.nio.charset.Charset

/**
 * @author chenxz
 * @date 2020/9/17
 * @desc 读取Assets目录下的文件工具类
 */
object ReadAssetsFileUtil {
    /**
     * 读取assets下的txt文件，返回utf-8 String
     *
     * @param context
     * @param fileName 不包括后缀
     * @return
     */
    fun readAssetsTxt(context: Context, fileName: String): String? {
        try {
            //Return an AssetManager instance for your application's package
            val `is` = context.assets.open("$fileName.txt")
            val size = `is`.available()
            // Read the entire asset into a local byte buffer.
            val buffer = ByteArray(size)
            `is`.read(buffer)
            `is`.close()
            // Convert the buffer into a string.
            // Finally stick the string into the text view.
            return buffer.toString(Charset.forName("utf-8"))
        } catch (e: IOException) {
            // Should never happen!
            e.printStackTrace()
        }
        return ""
    }

    /**
     * 读取方法
     * @param context Context
     * @param fileName String?
     * @return String?
     */
    fun getJson(context: Context, fileName: String?): String? {
        val stringBuilder = StringBuilder()
        try {
            val assetManager = context.assets
            val bf =
                BufferedReader(InputStreamReader(assetManager.open(fileName!!)))
            var line: String?
            while (bf.readLine().also { line = it } != null) {
                stringBuilder.append(line)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return stringBuilder.toString()
    }
}