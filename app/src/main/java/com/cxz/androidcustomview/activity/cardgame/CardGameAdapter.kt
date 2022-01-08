package com.cxz.androidcustomview.activity.cardgame

import android.animation.Animator
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.airbnb.lottie.LottieAnimationView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.cxz.androidcustomview.R
import com.cxz.androidcustomview.util.CardGameUtil

/**
 * @author chenxz
 * @date 2020/8/5
 * @description 卡牌游戏Adapter
 */
class CardGameAdapter : BaseQuickAdapter<CardItem, BaseViewHolder> {

    constructor(layoutResId: Int) : super(layoutResId)

    constructor(layoutResId: Int, data: MutableList<CardItem>?) : super(layoutResId, data)

    var audioStartOrStopAction: (Boolean) -> Unit = {}

    override fun convert(holder: BaseViewHolder, item: CardItem) {
        val cardIV = holder.getView<ImageView>(R.id.cardItemIV)
        val contentTV = holder.getView<TextView>(R.id.cardContentTV)
        holder.setText(R.id.cardContentTV, item.content)
        holder.setText(R.id.userNameTV, item.userName)
        if (item.isOpen) {
            if (item.isPlayFlopAnim) {
//                AudioPlayer.playAssetsMusic(context, "card_flop.mp3", start = {
//                    audioStartOrStopAction.invoke(true)
//                }, completionOrError = {
//                    audioStartOrStopAction.invoke(false)
//                })
                CardGameUtil.rotateCardView(context, cardIV, contentTV)
            } else {
                cardIV.setImageResource(R.mipmap.icon_card_front_bg)
                contentTV.visibility = View.VISIBLE
            }
        } else {
            contentTV.visibility = View.GONE
            cardIV.setImageResource(R.mipmap.icon_card_back_bg)
        }
        val lottieAnimView = holder.getView<LottieAnimationView>(R.id.lottieAnimView)
        if (item.isPlayShineAnim) {
            lottieAnimView.visibility = View.VISIBLE
            lottieAnimView.resumeAnimation()
            lottieAnimView.addAnimatorListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator) {}
                override fun onAnimationEnd(animation: Animator) {
                    item.isPlayShineAnim = false
                    lottieAnimView.pauseAnimation()
                    lottieAnimView.cancelAnimation()
                    lottieAnimView.clearAnimation()
                    lottieAnimView.visibility = View.GONE
                }

                override fun onAnimationCancel(animation: Animator) {}
                override fun onAnimationRepeat(animation: Animator) {}
            })
        } else {
            lottieAnimView.visibility = View.GONE
        }
    }
}