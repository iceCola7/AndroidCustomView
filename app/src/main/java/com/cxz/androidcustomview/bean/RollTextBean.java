package com.cxz.androidcustomview.bean;

/**
 * @author chenxz
 * @date 2018/10/22
 * @desc
 */
public class RollTextBean {

    public String title;
    public String url;
    public String tag;

    public RollTextBean(String title, String url) {
        this.title = title;
        this.url = url;
    }

    public RollTextBean(String title, String url, String tag) {
        this.title = title;
        this.url = url;
        this.tag = tag;
    }
}
