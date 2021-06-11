package com.cxz.androidcustomview.activity.bottomdialog;

/**
 * @author chenxz
 * @date 2018/9/10
 * @desc
 */
public class ItemBean {

    private String text;
    private int iconId;

    public ItemBean(String text, int iconId) {
        this.text = text;
        this.iconId = iconId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }
}
