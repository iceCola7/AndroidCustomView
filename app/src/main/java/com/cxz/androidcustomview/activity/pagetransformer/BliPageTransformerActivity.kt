package com.cxz.androidcustomview.activity.pagetransformer

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.cxz.androidcustomview.R
import com.cxz.androidcustomview.base.BaseActivity
import com.cxz.androidcustomview.widget.BliPageTransformer
import kotlinx.android.synthetic.main.activity_bli_page_transformer.*

class BliPageTransformerActivity : BaseActivity() {

    override fun attachLayoutRes(): Int {
        return R.layout.activity_bli_page_transformer
    }

    override fun initView() {
        val title = intent.getStringExtra("title")
        setToolbarTitle(title)

        val fragmentList = mutableListOf<Fragment>()
        fragmentList.add(BliPageTransformerFragment(0))
        fragmentList.add(BliPageTransformerFragment(1))
        fragmentList.add(BliPageTransformerFragment(2))
        fragmentList.add(BliPageTransformerFragment(3))

        viewPager.apply {
            adapter = BliPageAdapter(supportFragmentManager, fragmentList)
            setPageTransformer(false, BliPageTransformer())
        }
    }

    class BliPageAdapter : FragmentStatePagerAdapter {

        private var fragmentList = mutableListOf<Fragment>()

        constructor(fm: FragmentManager, fragmentList: MutableList<Fragment>)
                : super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
            this.fragmentList = fragmentList
        }

        override fun getItem(position: Int): Fragment {
            return fragmentList[position]
        }

        override fun getCount(): Int {
            return fragmentList.size
        }

    }

}