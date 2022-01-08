package com.cxz.androidcustomview.activity.cardgame;

/**
 * @author chenxz
 * @date 2020/7/27
 * @description 卡片游戏item
 */
public class CardItem {

    // 卡牌id
    private int cardId;
    // 卡牌内容
    private String content;
    // 选中卡牌的姓名
    private String userName;
    // 是否打开卡牌
    private boolean isOpen = false;
    // 是否播放流光动画
    private boolean isPlayShineAnim = false;
    // 是否播放翻牌动画
    private boolean isPlayFlopAnim = false;

    public int getCardId() {
        return cardId;
    }

    public void setCardId(int cardId) {
        this.cardId = cardId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public boolean isPlayShineAnim() {
        return isPlayShineAnim;
    }

    public void setPlayShineAnim(boolean playShineAnim) {
        isPlayShineAnim = playShineAnim;
    }

    public boolean isPlayFlopAnim() {
        return isPlayFlopAnim;
    }

    public void setPlayFlopAnim(boolean playFlopAnim) {
        isPlayFlopAnim = playFlopAnim;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
