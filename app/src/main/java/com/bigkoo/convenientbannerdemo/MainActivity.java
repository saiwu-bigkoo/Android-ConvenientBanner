package com.bigkoo.convenientbannerdemo;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.bigkoo.convenientbanner.CBPageItemUpdateListener;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.ConvenientBanner.Transformer;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * Created by Sai on 15/7/30.
 * convenientbanner 控件 的 demo
 */
public class MainActivity extends ActionBarActivity implements AdapterView.OnItemClickListener, CBPageItemUpdateListener {
    private ConvenientBanner convenientBanner;//顶部广告栏控件
    private String[] images = {"http://img2.imgtn.bdimg.com/it/u=3093785514,1341050958&fm=21&gp=0.jpg",
            "http://img2.3lian.com/2014/f2/37/d/40.jpg",
            "http://d.3987.com/sqmy_131219/001.jpg",
            "http://img2.3lian.com/2014/f2/37/d/39.jpg",
            "http://www.8kmm.com/UploadFiles/2012/8/201208140920132659.jpg",
            "http://f.hiphotos.baidu.com/image/h%3D200/sign=1478eb74d5a20cf45990f9df460b4b0c/d058ccbf6c81800a5422e5fdb43533fa838b4779.jpg",
            "http://jyimg1.meimei22.com/pic/jingyan/2015-6-5/1/7.jpg"
    };

    private ListView listView;
    private ArrayAdapter transformerArrayAdapter;
    private ArrayList<String> transformerList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        init();
    }

    private void initViews() {
        convenientBanner = (ConvenientBanner) findViewById(R.id.convenientBanner);
        listView = (ListView) findViewById(R.id.listView);
        transformerArrayAdapter = new ArrayAdapter(this,R.layout.adapter_transformer,transformerList);
        listView.setAdapter(transformerArrayAdapter);
        listView.setOnItemClickListener(this);
    }

    private void init(){
        loadTestDatas();
        //不需要圆点指示器可用不设，不需要翻页效果可用不设
        convenientBanner.setPageItemUpdateListener(this).setItemSize(images.length)
                //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器
                .setPageIndicator(new int[]{R.drawable.ic_page_indicator, R.drawable.ic_page_indicator_focused})
                //设置翻页的效果
                .setPageTransformer(Transformer.DefaultTransformer);
    }

    /*
    加入测试Views
    * */
    private void loadTestDatas() {

        //各种翻页效果
        transformerList.add(Transformer.DefaultTransformer.getClassName());
        transformerList.add(Transformer.AccordionTransformer.getClassName());
        transformerList.add(Transformer.BackgroundToForegroundTransformer.getClassName());
        transformerList.add(Transformer.CubeInTransformer.getClassName());
        transformerList.add(Transformer.CubeOutTransformer.getClassName());
        transformerList.add(Transformer.DepthPageTransformer.getClassName());
        transformerList.add(Transformer.FlipHorizontalTransformer.getClassName());
        transformerList.add(Transformer.FlipVerticalTransformer.getClassName());
        transformerList.add(Transformer.ForegroundToBackgroundTransformer.getClassName());
        transformerList.add(Transformer.RotateDownTransformer.getClassName());
        transformerList.add(Transformer.RotateUpTransformer.getClassName());
        transformerList.add(Transformer.StackTransformer.getClassName());
        transformerList.add(Transformer.ZoomInTransformer.getClassName());
        transformerList.add(Transformer.ZoomOutTranformer.getClassName());

        transformerArrayAdapter.notifyDataSetChanged();
    }

    /**
     * 通过文件名获取资源id 例子：getResId("icon", R.drawable.class);
     *
     * @param variableName
     * @param c
     * @return
     */
    public static int getResId(String variableName, Class<?> c) {
        try {
            Field idField = c.getDeclaredField(variableName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    // 开始自动翻页
    @Override
    protected void onResume() {
        super.onResume();
        //开始自动翻页
        convenientBanner.startTurning(5000);
    }

     // 停止自动翻页
    @Override
    protected void onPause() {
        super.onPause();
        //停止翻页
        convenientBanner.stopTurning();
    }

    //点击切换效果
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        String name = transformerList.get(position);
        Transformer transformer = Transformer.valueOf(name);
        convenientBanner.setPageTransformer(transformer);
    }

    /**
     * 创建子Item
     * @param container
     * @param position
     * @return
     */
    @Override
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
}
