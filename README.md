[![Android Gems](http://www.android-gems.com/badge/saiwu-bigkoo/Android-ConvenientBanner.svg?branch=master)](http://www.android-gems.com/lib/saiwu-bigkoo/Android-ConvenientBanner)

ConvenientBanner
===========

ͨ�õĹ�����ؼ�����������ʵ�ֹ��ͷЧ����֧������ѭ�������������Զ���ҳ��ʱ��(���ҷǳ����ܣ���ָ��������ͣ��ҳ���뿪�Զ���ʼ��ҳ����Ҳ���������ڽ���onPause��ʱ�򲻽����Զ���ҳ��onResume֮������Զ���ҳ)�������ṩ���ַ�ҳ��Ч��
�Ա�����������ؼ�����඼��Ҫ��Դ����иĶ����ܼ�������ͼƬ�����߰��㼯�ɲ���������Ҫ��ͼƬ����⡣������������д�������㻶ϲ������Ҫ�Կ�Դ������޸���Ϳ���ʹ���κ���ϲ��������ͼƬ�������ϡ�

## Demo
��ģ�������е�Ч������겦����ģ����̫����ԭ��ʵ��Ч����Ч��ͼ����Ŷ����
![](https://github.com/saiwu-bigkoo/Android-ConvenientBanner/blob/master/preview/convenientbannerdemo.gif)

- [demo�����뿴������](https://github.com/saiwu-bigkoo/Android-ConvenientBanner/blob/master/app/src/main/java/com/bigkoo/convenientbannerdemo/MainActivity.java)

demo����Module��ʽ��������Ҳ����ʹ��gradle ����:
```java
    implementation 'com.bigkoo:convenientbanner:2.1.5'//��ַ��Сд�ˣ������
    implementation 'androidx.recyclerview:recyclerview:1.0.0+'

//   compile 'com.bigkoo:ConvenientBanner:2.1.4'//��ַ��ConvenientBanner ��д�ˣ������
//compile 'com.bigkoo:convenientbanner:2.0.5'�ɰ�
```


##### Config in xml

```xml
<com.bigkoo.convenientbanner.ConvenientBanner
        xmlns:app="http://schemas.android.com/apk/res-auto"
        android:id="@+id/convenientBanner"
        android:layout_width="match_parent"
        android:layout_height="200dp"
        app:canLoop="true" //����ѭ�����
/>
```

### config in java code

```java
//�Զ������Holder��ʵ�ָ��ิ�ӵĽ��棬��һ����ͼƬ��ҳ�������κοؼ���ҳ��ɡ�
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
                //����������ͼƬ��Ϊ��ҳָʾ������������û��ָʾ�������Ը����Լ�������������Լ���ָʾ��,����ҪԲ��ָʾ�����ò���
//                .setPageIndicator(new int[]{R.drawable.ic_page_indicator, R.drawable.ic_page_indicator_focused})
                .setOnItemClickListener(this);
                //����ָʾ���ķ���
//                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT)
//                .setOnPageChangeListener(this)//������ҳ�¼�
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

>## ����˵��

>v2.1.5
 -  androidx<br />

>v2.1.4
 -  ����Ϊ�յ�ʱ���쳣����<br />

>v2.1.3
 -  notifyDataSetChange��ʱ�򣬻ص�0��λ�á�<br />


>v2.1.2
 -  ����setlayoutManager�ˡ�<br />

>v2.1.1
 -  �ڲ�ʵ�ָ�Ϊ���Զ����LinearSnapHelper��ΪPagerSnapHelper��<br />

>v2.1.0
 -  �޸�������ָʾ���ͱ�������<br />

>v2.0.9
 -  ��¶canLoop��API��̬����<br />

>v2.0.8
 - ������Ϊ�б���header demo���޸�loopģʽ�µ�һ�ε�ָʾ��ͼ�겻ѡ������ <br />

>v2.0.7
 - ������PhotoView��Fresco��ϱ�Ϊ����ͼƬԤ���ؼ����ӣ�������ȡ�����õ�ǰposition api <br />

>v2.0.6
 - ʹ��RecycleView��������ʵ�֣�api�������ı䣬����xml���÷�ҳʱ�� <br />

>v2.0.5
 - ViewPagerʵ�ַ�ʽ <br />
