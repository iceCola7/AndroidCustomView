package com.cxz.androidcustomview.bean;

/**
 * Created by chenxz on 2018/8/3.
 */
public class MainBean {

    private int type;
    private String title;

    public MainBean(int type, String title) {
        this.type = type;
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String gettitle() {
        return title;
    }

    public void settitle(String title) {
        this.title = title;
    }
}
