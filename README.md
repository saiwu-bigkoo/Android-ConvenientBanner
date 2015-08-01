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
   compile 'com.bigkoo:convenientbanner:1.0.0'
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
//不需要圆点指示器可用不设，不需要翻页效果可用不设
convenientBanner.setPageItemUpdateListener(this)//CBPageItemUpdateListener 在 pageItemUpdate里面返回第几页对应的图片即可
        //只需要把页数传进来
        .setItemSize(images.length)
         //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器
        .setPageIndicator(new int[]{R.drawable.ic_page_indicator, R.drawable.ic_page_indicator_focused})
        //设置翻页的效果
        .setPageTransformer(Transformer.DefaultTransformer);
        
public Object pageItemUpdate(ViewGroup container, int position) {
        //网络图片例子,结合常用的图片缓存库UIL,你可以根据自己需求自己换其他网络图片库
//        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder().
//                showImageForEmptyUri(R.drawable.ic_default_adimage)
//                .cacheInMemory(true).cacheOnDisk(true).build();
//
//        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
//                getApplicationContext()).defaultDisplayImageOptions(defaultOptions)
//                .threadPriority(Thread.NORM_PRIORITY - 2)
//                .denyCacheImageMultipleSizesInMemory()
//                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
//                .tasksProcessingOrder(QueueProcessingType.LIFO).build();
//        ImageLoader.getInstance().init(config);
//
//        ImageView imageView = new ImageView(this);
//        imageView.setImageResource(R.drawable.ic_default_adimage);
//        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
//        ImageLoader.getInstance().displayImage(images[position],imageView);

        //本地图片例子
        ImageView imageView = new ImageView(this);
        imageView.setImageResource(getResId("ic_test_"+position, R.drawable.class));
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"点击图片",Toast.LENGTH_SHORT).show();
            }
        });

        return imageView;
    }
```


## Thanks

- [ViewPagerTransforms](https://github.com/ToxicBakery/ViewPagerTransforms)
