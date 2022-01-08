package com.cxz.androidcustomview.activity.cardgame

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.cxz.androidcustomview.R
import com.cxz.androidcustomview.base.BaseActivity
import kotlinx.android.synthetic.main.activity_card_game.*

class CardGameActivity : BaseActivity() {

    // 选择的卡片ID
    private var selectCardId: Long = -1

    private val cardGameAdapter: CardGameAdapter by lazy {
        CardGameAdapter(R.layout.item_card_game_list)
    }

    override fun attachLayoutRes(): Int {
        return R.layout.activity_card_game
    }

    override fun initView() {
        val title = intent.getStringExtra("title")
        setToolbarTitle(title)

        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        val gridLayoutManager = GridLayoutManager(this, 4)
        cardGameRV.layoutManager = gridLayoutManager
        cardGameRV.adapter = cardGameAdapter

        cardGameAdapter.setList(getDataList())

        initListeners()
    }

    private fun initListeners() {
        cardGameAdapter.setOnItemClickListener { adapter, view, position ->
            val cardItem: CardItem = adapter.data[position] as CardItem
            selectCardId = cardItem.cardId.toLong()
            if (!cardItem.isOpen) {
                cardItem.isOpen = true
                cardItem.isPlayFlopAnim = true
                cardGameAdapter.notifyItemChanged(position)
            }
        }
    }

    private fun getDataList(): MutableList<CardItem> {
        val list = mutableListOf<CardItem>()
        for (i in 0..11) {
            val cardItem = CardItem().apply {
                cardId = i
                userName = "userName$i"
                content = "内容$i"
            }
            list.add(cardItem)
        }
        return list
    }

}