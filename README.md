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
   compile 'com.bigkoo:convenientbanner:1.0.6'
```


##### Config in xml

```xml
<com.bigkoo.convenientbanner.ConvenientBanner
        android:id="@+id/convenientBanner"
        android:layout_width="match_parent"
        android:layout_height="200dp"/>
```

### config in java code

```java
//自定义你的Holder，实现更多复杂的界面，不一定是图片翻页，其他任何控件翻页亦可。
convenientBanner.setPages(
                new CBViewHolderCreator<LocalImageHolderView>() {
                    @Override
                    public LocalImageHolderView createHolder() {
                        return new LocalImageHolderView();
                    }
                }, localImages)
                //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                .setPageIndicator(new int[]{R.drawable.ic_page_indicator, R.drawable.ic_page_indicator_focused})
                //设置翻页的效果，不需要翻页效果可用不设
                .setPageTransformer(Transformer.DefaultTransformer);
        
public class LocalImageHolderView implements CBPageAdapter.Holder<Integer>{
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
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //点击事件
                Toast.makeText(view.getContext(),"点击了第"+(position+1)+"图片",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
```


## Thanks

- [ViewPagerTransforms](https://github.com/ToxicBakery/ViewPagerTransforms)
- [salvage](https://github.com/JakeWharton/salvage)
- [LoopingViewPager](https://github.com/imbryk/LoopingViewPager)
