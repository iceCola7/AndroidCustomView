### ShadowImageView库
##### 用法
- 布局中使用
```
<com.cxz.shadowimageview.ShadowImageView
    android:id="@+id/iv_shadowd"
    android:layout_width="300dp"
    android:layout_height="300dp"
    android:layout_marginTop="20dp"
    app:shadowColor="@color/colorPrimary"
    app:shadowRound="5dp"
    app:shadowSrc="@mipmap/lotus" />
```
- 设置图片
```
shadow.setImageResource(resID); 
shadow.setImageDrawable(drawable); 
shadow.setImageBitmap(bitmap);
```
- 设置图片半径
```
shadow.setImageRadius(radius);
```
- 设置图片的阴影颜色
```
shadow.setImageShadowColor(color);
```

##### 效果
![](/screenshot/01.png)