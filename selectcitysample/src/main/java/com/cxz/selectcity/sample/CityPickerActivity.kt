package com.cxz.selectcity.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.cxz.selectcity.sample.adapter.CityAdapter
import com.cxz.selectcity.sample.bean.City
import com.cxz.selectcity.sample.bean.CityPickerBean
import com.cxz.selectcity.sample.utils.PinyinUtil
import com.cxz.selectcity.sample.utils.ReadAssetsFileUtil
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_city_picker.*
import java.util.*
import kotlin.collections.HashSet

class CityPickerActivity : AppCompatActivity() {

    private val mCityAdapter: CityAdapter by lazy {
        CityAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_city_picker)

        initView()
        initData()
    }

    private fun initView() {
        cityRV.layoutManager = LinearLayoutManager(this)
        cityRV.adapter = mCityAdapter
        sideLetterBar.setOverlay(letterOverlayTV)
        sideLetterBar.setOnLetterChangedListener {

        }
    }

    private fun initData() {
        getCityData()
    }

    private fun getCityData() {
        val json = ReadAssetsFileUtil.getJson(this, "city.json")
        val bean = Gson().fromJson<CityPickerBean>(json, CityPickerBean::class.java)
        val hashSet = HashSet<City>()
        bean.data.areas.forEach {
            val name = it.name.replace("  ", "")
            hashSet.add(City(it.id, name, PinyinUtil.getPinYin(name) ?: "", it.isHot == 1))
            for (childrenBeanX in it.children) {
                hashSet.add(
                    City(
                        childrenBeanX.id,
                        childrenBeanX.name,
                        PinyinUtil.getPinYin(childrenBeanX.name) ?: "",
                        childrenBeanX.isHot == 1
                    )
                )
            }
        }

        //set转换list
        val cities: ArrayList<City> = ArrayList(hashSet)
        //按照字母排序
        cities.sortWith(Comparator { p0, p1 -> p0.pinyin.compareTo(p1.pinyin) })
//        cities.add(0,City(-1, "定位", "0"))
//        cities.add(1,City(-1, "热门", "1"))
        mCityAdapter.setList(cities)
    }

}