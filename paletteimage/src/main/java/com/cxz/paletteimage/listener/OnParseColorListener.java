package com.cxz.paletteimage.listener;

import com.cxz.paletteimage.PaletteImageView;

/**
 * Created by chenxz on 2017/11/8.
 */

public interface OnParseColorListener {

    void onComplete(PaletteImageView paletteImageView);

    void onFail();

}
