package com.cxz.selectcity.sample

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cxz.selectcity.sample.adapter.ContactAdapter
import com.cxz.selectcity.sample.adapter.RecentContactAdapter
import com.cxz.selectcity.sample.bean.User
import kotlinx.android.synthetic.main.activity_contact_list.*
import kotlinx.android.synthetic.main.contract_list_header.view.*

class ContactListActivity : AppCompatActivity() {

    private var contactList = mutableListOf<User>()

    private var recentContactList = mutableListOf<User>()

    private val mContactAdapter by lazy {
        ContactAdapter()
    }

    private val mRecentContactAdapter by lazy {
        RecentContactAdapter()
    }

    private var headerView: View? = null

    private var recentHeaderView: View? = null
    private var recentFooterView: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_list)

        initData()
        initView()
    }

    private fun initView() {

        recentHeaderView = View.inflate(this, R.layout.recent_contact_list_header, null)
        recentFooterView = View.inflate(this, R.layout.recent_contact_list_footer, null)

        headerView = View.inflate(this, R.layout.contract_list_header, null)
        headerView?.let {
            it.recentContractRV.layoutManager = LinearLayoutManager(this)
            it.recentContractRV.adapter = mRecentContactAdapter
        }
        mRecentContactAdapter.let {
            it.addHeaderView(recentHeaderView!!, 0)
            // 列表数据大于3个，只显示3个
            if (recentContactList.size > 3) {
                it.setList(recentContactList.subList(0, 3))
                it.addFooterView(recentFooterView!!)
            } else {
                it.setList(recentContactList)
            }
        }

        recentFooterView?.setOnClickListener {
            // 点击底部显示全部，则显示全部数据，并移除底部Footer
            mRecentContactAdapter.setList(recentContactList)
            if (mRecentContactAdapter.hasFooterLayout()) {
                mRecentContactAdapter.removeFooterView(recentFooterView!!)
            }
        }

        val linearLayoutManager = LinearLayoutManager(this)
        contactRV.layoutManager = linearLayoutManager
        contactRV.adapter = mContactAdapter
        mContactAdapter.setList(contactList)
        mContactAdapter.addHeaderView(headerView!!, 0)

        contactRV.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                contactRV.postDelayed({
                    var firstPosition = linearLayoutManager.findFirstVisibleItemPosition()
                    if (mContactAdapter.hasHeaderLayout()) {
                        if (firstPosition <= 0) {
                            // sideLetterBar.changeLetterView("最近")
                            return@postDelayed
                        } else {
                            firstPosition -= mContactAdapter.headerLayoutCount
                        }
                    }
                    mContactAdapter.changeSelectedText(firstPosition)
                    if (mContactAdapter.data.size > 0) {
                        val letter = mContactAdapter.data[firstPosition].firstLetter
                        sideLetterBar.changeLetterView(letter)
                    }
                }, 10)
            }
        })

        sideLetterBar.setOverlay(letterOverlayTV)
        sideLetterBar.setOnLetterChangedListener {
            var index = mContactAdapter.getPositionForSection(it)
            if (index != -1) {
                if (mContactAdapter.hasHeaderLayout()) {
                    index += mContactAdapter.headerLayoutCount
                }
                mContactAdapter.changeSelectedText(index)
                contactRV.smoothScrollToPosition(index)
                (contactRV.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(index, 0)
            } else {
                if (it == "*") {
                    (contactRV.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(0, 0)
                }
            }
        }

    }

    private fun initData() {
        contactList.add(User("亳州")) // 亳[bó]属于不常见的二级汉字
        contactList.add(User("大娃"))
        contactList.add(User("二娃"))
        contactList.add(User("三娃"))
        contactList.add(User("四娃"))
        contactList.add(User("五娃"))
        contactList.add(User("六娃"))
        contactList.add(User("七娃"))
        contactList.add(User("喜羊羊"))
        contactList.add(User("美羊羊"))
        contactList.add(User("懒羊羊"))
        contactList.add(User("沸羊羊"))
        contactList.add(User("暖羊羊"))
        contactList.add(User("慢羊羊"))
        contactList.add(User("灰太狼"))
        contactList.add(User("红太狼"))
        contactList.add(User("孙悟空"))
        contactList.add(User("黑猫警长"))
        contactList.add(User("舒克"))
        contactList.add(User("贝塔"))
        contactList.add(User("海尔"))
        contactList.add(User("阿凡提"))
        contactList.add(User("邋遢大王"))
        contactList.add(User("哪吒"))
        contactList.add(User("没头脑"))
        contactList.add(User("不高兴"))
        contactList.add(User("蓝皮鼠"))
        contactList.add(User("大脸猫"))
        contactList.add(User("大头儿子"))
        contactList.add(User("小头爸爸"))
        contactList.add(User("蓝猫"))
        contactList.add(User("淘气"))
        contactList.add(User("叶峰"))
        contactList.add(User("楚天歌"))
        contactList.add(User("江流儿"))
        contactList.add(User("Tom"))
        contactList.add(User("Jerry"))
        contactList.add(User("12345"))
        contactList.add(User("54321"))
        contactList.add(User("_(:з」∠)_"))
        contactList.add(User("……%￥#￥%#"))
        // 对list进行排序，需要让User实现Comparable接口重写compareTo方法
        contactList.sort()

        recentContactList.add(User("喜羊羊"))
        recentContactList.add(User("美羊羊"))
        recentContactList.add(User("懒羊羊"))
        recentContactList.add(User("沸羊羊"))
        recentContactList.add(User("暖羊羊"))
        recentContactList.add(User("慢羊羊"))
        recentContactList.add(User("灰太狼"))
    }

}