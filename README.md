[![Android Gems](http://www.android-gems.com/badge/saiwu-bigkoo/Android-ConvenientBanner.svg?branch=master)](http://www.android-gems.com/lib/saiwu-bigkoo/Android-ConvenientBanner)

ConvenientBanner
===========

通用的广告栏控件，让你轻松实现广告头效果。支持无限循环，可以设置自动翻页和时间(而且非常智能，手指触碰则暂停翻页，离开自动开始翻页。你也可以设置在界面onPause的时候不进行自动翻页，onResume之后继续自动翻页)，并且提供多种翻页特效。
对比其他广告栏控件，大多都需要对源码进行改动才能加载网络图片，或者帮你集成不是你所需要的图片缓存库。而这个库能让有代码洁癖的你欢喜，不需要对库源码进行修改你就可以使用任何你喜欢的网络图片库进行配合。

## Demo
用模拟器运行的效果，鼠标拨动和模拟器太卡等原因，实际效果比效果图更炫哦～～
![](https://github.com/saiwu-bigkoo/Android-ConvenientBanner/blob/master/preview/convenientbannerdemo.gif)

- [demo代码请看戳这里](https://github.com/saiwu-bigkoo/Android-ConvenientBanner/blob/master/app/src/main/java/com/bigkoo/convenientbannerdemo/MainActivity.java)

demo是用Module方式依赖，你也可以使用gradle 依赖:
```java
   compile 'com.bigkoo:ConvenientBanner:2.1.4'//地址变ConvenientBanner 大写了，额。。。
//compile 'com.bigkoo:convenientbanner:2.0.5'旧版
```


##### Config in xml

```xml
<com.bigkoo.convenientbanner.ConvenientBanner
        xmlns:app="http://schemas.android.com/apk/res-auto"
        android:id="@+id/convenientBanner"
        android:layout_width="match_parent"
        android:layout_height="200dp"
        app:canLoop="true" //控制循环与否
/>
```

### config in java code

```java
//自定义你的Holder，实现更多复杂的界面，不一定是图片翻页，其他任何控件翻页亦可。
convenientBanner.setPages(
                new CBViewHolderCreator() {
                    @Override
                    public LocalImageHolderView createHolder(View itemView) {
                        return new LocalImageHolderView(itemView);
                    }

                    @Override
                    public int getLayoutId() {
                        return R.layout.item_localimage;
                    }
                }, localImages)
                //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
//                .setPageIndicator(new int[]{R.drawable.ic_page_indicator, R.drawable.ic_page_indicator_focused})
                .setOnItemClickListener(this);
                //设置指示器的方向
//                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT)
//                .setOnPageChangeListener(this)//监听翻页事件
                ;

public class LocalImageHolderView implements Holder<Integer>{
    private ImageView imageView;
    @Override
    public View createView(Context context) {
        imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        return imageView;
    }

    @Override
    public void UpdateUI(Context context, final int position, Integer data) {
        imageView.setImageResource(data);
    }
}
```

## Thanks

- [ViewPagerTransforms](https://github.com/ToxicBakery/ViewPagerTransforms)
- [salvage](https://github.com/JakeWharton/salvage)
- [LoopingViewPager](https://github.com/imbryk/LoopingViewPager)
- [RecyclerViewCardGallery](https://github.com/zjw-swun/RecyclerViewCardGallery)

>## 更新说明

>v2.1.4
 -  数据为空的时候异常处理。<br />

>v2.1.3
 -  notifyDataSetChange的时候，回到0的位置。<br />


>v2.1.2
 -  可以setlayoutManager了。<br />

>v2.1.1
 -  内部实现改为由自定义的LinearSnapHelper改为PagerSnapHelper。<br />

>v2.1.0
 -  修复不设置指示器就报错问题<br />

>v2.0.9
 -  暴露canLoop的API动态控制<br />

>v2.0.8
 - 加入作为列表顶部header demo，修复loop模式下第一次的指示器图标不选中问题 <br />

>v2.0.7
 - 加入与PhotoView，Fresco配合变为网络图片预览控件例子，新增获取和设置当前position api <br />

>v2.0.6
 - 使用RecycleView进行重新实现，api基本不改变，加入xml配置翻页时间 <br />

>v2.0.5
 - ViewPager实现方式 <br />
