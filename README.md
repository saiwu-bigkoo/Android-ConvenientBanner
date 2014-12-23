PageTurning
===========

Simple and convenient ViewPager
超简单的Viewpager控件，实现一行代码配置ViewPager，而且默认提供比较多的翻页动画效果，也可以自定义动画效果,自定循环翻页。用了你会爱上它，实在时太便利了。玲珑小巧！

## Demo

![](http://ww3.sinaimg.cn/mw690/610dc034jw1egzor66ojdg20950fknpe.gif)

```java
<com.pageturning.PageTurningView 
        android:id="@+id/pageTurningView "
        android:layout_width="match_parent"
        android:layout_height="200dp"/>
```
```java
pageTurningView.setItems(pageViews).setPageIndicator(new int[]{R.drawable.ic_page_indicator,R.drawable.ic_page_indicator_focused}).startTurning(5000).setPageTransformer(Transformer.AccordionTransformer);
```

以上例子图片是借的，因为本人很懒,效果差不多将就看吧
- [AndroidImageSlider](https://github.com/daimajia/AndroidImageSlider)

## Thanks

- [ViewPagerTransforms](https://github.com/ToxicBakery/ViewPagerTransforms)
