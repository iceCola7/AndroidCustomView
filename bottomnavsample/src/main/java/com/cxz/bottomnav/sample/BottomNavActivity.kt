package com.cxz.bottomnav.sample

import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.forEach
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.airbnb.lottie.LottieCompositionFactory
import com.airbnb.lottie.LottieDrawable
import com.cxz.bottomnav.sample.constants.LottieAnimation
import com.cxz.bottomnav.sample.event.MessageRedDotEvent
import com.cxz.bottomnav.sample.event.MineRedDotEvent
import com.cxz.bottomnav.sample.ui.HomeFragment
import com.cxz.bottomnav.sample.ui.MeFragment
import com.cxz.bottomnav.sample.ui.MessageFragment
import com.cxz.bottomnav.sample.ui.PartyFragment
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_bottom_nav.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class BottomNavActivity : AppCompatActivity() {

    private val fragmentList = arrayListOf(
        HomeFragment(),
        PartyFragment(),
        MessageFragment(),
        MeFragment()
    )

    private var mPreClickPosition = 0

    private val navigationAnimationList = arrayListOf(
        LottieAnimation.LIVE,
        LottieAnimation.GAME,
        LottieAnimation.MESSAGE,
        LottieAnimation.MINE
    )

    private val navigationTitleList = arrayListOf(
        "直播",
        "派对",
        "消息",
        "我的"
    )

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bottom_nav)

        val title = intent.getStringExtra("title")
        setSupportActionBar(toolbar)
        toolbar.title = ""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        title_tv.text = title

        initViewPager()

        initBottomNavigationView()
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    /**
     * 初始化ViewPager
     */
    private fun initViewPager() {
        viewPager2.offscreenPageLimit = navigationTitleList.size
        // 是否禁止横向滑动
        viewPager2.isUserInputEnabled = true
        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                bottomNavBar.selectedItemId = position
            }
        })
        viewPager2.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount(): Int {
                return fragmentList.size
            }

            override fun createFragment(position: Int): Fragment {
                return fragmentList[position]
            }
        }
    }

    /**
     * 初始化底部BottomNavigationView
     */
    private fun initBottomNavigationView() {
        bottomNavBar.menu.apply {
            for (i in 0 until navigationTitleList.size) {
                add(Menu.NONE, i, Menu.NONE, navigationTitleList[i])
            }
            setLottieDrawable(getLottieAnimationList())
        }
        initListeners()
    }

    /**
     * 初始化监听事件
     */
    private fun initListeners() {
        bottomNavBar.setOnNavigationItemSelectedListener {
            handleNavigationItem(it)
            // 联动 ViewPager2
            viewPager2.setCurrentItem(it.itemId, true)
            return@setOnNavigationItemSelectedListener true
        }
        bottomNavBar.setOnNavigationItemReselectedListener {
            handleNavigationItem(it)
        }
        // 默认选中第一个
        bottomNavBar.selectedItemId = 0
        // 处理长按 MenuItem 提示 TooltipText
        bottomNavBar.menu.forEach {
            val menuItemView = bottomNavBar.findViewById(it.itemId) as BottomNavigationItemView
            menuItemView.setOnLongClickListener {
                true
            }
        }
    }

    private fun Menu.setLottieDrawable(lottieAnimationList: ArrayList<LottieAnimation>) {
        for (i in 0 until navigationTitleList.size) {
            val item = findItem(i)
            item.icon = getLottieDrawable(lottieAnimationList[i], bottomNavBar)
        }
    }

    private fun handleNavigationItem(item: MenuItem) {
        handlePlayLottieAnimation(item)
        mPreClickPosition = item.itemId
    }

    private fun handlePlayLottieAnimation(item: MenuItem) {
        val currentIcon = item.icon as? LottieDrawable
        currentIcon?.apply {
            playAnimation()
        }
        // 处理 tab 切换，icon 对应调整
        if (item.itemId != mPreClickPosition) {
            bottomNavBar.menu.findItem(mPreClickPosition).icon =
                getLottieDrawable(getLottieAnimationList()[mPreClickPosition], bottomNavBar)
        }
    }

    /**
     * 获取 Lottie Drawable
     */
    private fun getLottieDrawable(
        animation: LottieAnimation,
        bottomNavigationView: BottomNavigationView
    ): LottieDrawable {
        return LottieDrawable().apply {
            val result = LottieCompositionFactory.fromAssetSync(
                bottomNavigationView.context.applicationContext, animation.value
            )
            callback = bottomNavigationView
            composition = result.value
        }
    }

    /**
     * 获取不同模式下 Lottie json 文件
     */
    private fun getLottieAnimationList(): ArrayList<LottieAnimation> {
        return navigationAnimationList
    }

    /**
     * 展示底部消息Item角标
     */
    private fun showMsgDot(count: Int) {
        val badge = bottomNavBar.getOrCreateBadge(2)
        badge.badgeGravity = BadgeDrawable.TOP_END
        badge.backgroundColor = Color.RED
        badge.verticalOffset = 26
        badge.horizontalOffset = 20
        badge.maxCharacterCount = 3
        badge.number = count
        badge.isVisible = count > 0
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public fun showMsgDotEvent(event: MessageRedDotEvent) {
        showMsgDot(event.count)
    }

    /**
     * 是否展示我的Item角标
     * @param isShow Boolean 是否显示
     */
    private fun showMineDot(isShow: Boolean) {
        val badge = bottomNavBar.getOrCreateBadge(3)
        badge.badgeGravity = BadgeDrawable.TOP_END
        badge.backgroundColor = Color.RED
        badge.verticalOffset = 26
        badge.horizontalOffset = 20
        badge.maxCharacterCount = 3
        badge.isVisible = isShow
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public fun showMineDotEvent(event: MineRedDotEvent) {
        showMineDot(event.isShow)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

}