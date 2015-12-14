package com.bigkoo.convenientbannerdemo.recyclerviewdemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbannerdemo.NetworkImageHolderView;
import com.bigkoo.convenientbannerdemo.R;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Sai on 15/8/13.
 * 这个是RecyclerView配合ConvenientBanner作为header的例子
 * 有issue反馈说RecyclerView刷新会出现空白图片，于是写了这个例子进行测试，也提供给对RecyclerView使用不熟悉的开发者进行参考吧。
 */
public class ADRecyclerViewDemo extends Activity implements SwipeRefreshLayout.OnRefreshListener {
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView listView;
    private ArrayList<String> mDatas = new ArrayList<String>();
    private RecyclerViewHFAdapter adapter;
    private ConvenientBanner convenientBanner;

    private List<String> networkImages;
    private String[] images = {"http://img2.imgtn.bdimg.com/it/u=3093785514,1341050958&fm=21&gp=0.jpg",
            "http://img2.3lian.com/2014/f2/37/d/40.jpg",
            "http://d.3987.com/sqmy_131219/001.jpg",
            "http://img2.3lian.com/2014/f2/37/d/39.jpg",
            "http://www.8kmm.com/UploadFiles/2012/8/201208140920132659.jpg",
            "http://f.hiphotos.baidu.com/image/h%3D200/sign=1478eb74d5a20cf45990f9df460b4b0c/d058ccbf6c81800a5422e5fdb43533fa838b4779.jpg",
            "http://f.hiphotos.baidu.com/image/pic/item/09fa513d269759ee50f1971ab6fb43166c22dfba.jpg"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
        init();
        initEvents();
    }

    private void initViews() {
        setContentView(R.layout.acitvity_adrecyclerviewdemo);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refreshLayout);
        convenientBanner = (ConvenientBanner) LayoutInflater.from(this).inflate(R.layout.adapter_header_cb,null);
        convenientBanner.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,600));
        listView = (RecyclerView) findViewById(R.id.recyclerView);
    }

    private void init(){
        initImageLoader();

        listView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        listView.setLayoutManager(layoutManager);
        adapter = new RecyclerViewHFAdapter(mDatas);
        listView.setAdapter(adapter);

        networkImages= Arrays.asList(images);
        convenientBanner.setPages(new CBViewHolderCreator<NetworkImageHolderView>() {
            @Override
            public NetworkImageHolderView createHolder() {
                return new NetworkImageHolderView();
            }
        },networkImages)
        .setPageIndicator(new int[]{R.drawable.ic_page_indicator, R.drawable.ic_page_indicator_focused});

        adapter.addHeader(convenientBanner);
        loadTestDatas();
    }
    private void initEvents() {
        refreshLayout.setOnRefreshListener(this);
    }
    //初始化网络图片缓存库
    private void initImageLoader(){
        //网络图片例子,结合常用的图片缓存库UIL,你可以根据自己需求自己换其他网络图片库
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder().
                showImageForEmptyUri(R.drawable.ic_default_adimage)
                .cacheInMemory(true).cacheOnDisk(true).build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                getApplicationContext()).defaultDisplayImageOptions(defaultOptions)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO).build();
        ImageLoader.getInstance().init(config);
    }
    /*
    加入测试Views
    * */
    private void loadTestDatas() {

        mDatas.add("test＝＝＝＝＝＝＝＝＝＝＝");
        adapter.notifyDataSetChanged();
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

    @Override
    public void onRefresh() {

//        mDatas.add("dsafdsf");
//        adapter.notifyDataSetChanged();
        //跟上面注释的效果一样
        adapter.addData("onRefresh  ===test========");
        adapter.addData("onRefresh  ===test========");
        adapter.addData("onRefresh  ===test========");
        adapter.addData("onRefresh  ===test========");
        adapter.addData("onRefresh  ===test========");
        adapter.addData("onRefresh  ===test========");
        adapter.addData("onRefresh  ===test========");
        adapter.addData("onRefresh  ===test========");
        adapter.addData("onRefresh  ===test========");
        adapter.addData("onRefresh  ===test========");
        adapter.addData("onRefresh  ===test========");
        adapter.addData("onRefresh  ===test========");
        adapter.addData("onRefresh  ===test========");
        adapter.addData("onRefresh  ===test========");
        adapter.addData("onRefresh  ===test========");
        adapter.addData("onRefresh  ===test========");
        adapter.addData("onRefresh  ===test========");
        adapter.addData("onRefresh  ===test========");
        adapter.addData("onRefresh  ===test========");
        adapter.addData("onRefresh  ===test========");
        adapter.addData("onRefresh  ===test========");
        adapter.addData("onRefresh  ===test========");
        adapter.addData("onRefresh  ===test========");
        adapter.addData("onRefresh  ===test========");
        adapter.addData("onRefresh  ===test========");
        adapter.addData("onRefresh  ===test========");
        adapter.addData("onRefresh  ===test========");
        adapter.addData("onRefresh  ===test========");
        adapter.addData("onRefresh  ===test========");
        adapter.addData("onRefresh  ===test========");
        adapter.addData("onRefresh  ===test========");
        adapter.addData("onRefresh  ===test========");
        adapter.addData("onRefresh  ===test========");
        adapter.addData("onRefresh  ===test========");
        adapter.addData("onRefresh  ===test========");
        adapter.addData("onRefresh  ===test========");
        adapter.addData("onRefresh  ===test========");
        adapter.addData("onRefresh  ===test========");
        adapter.addData("onRefresh  ===test========");
        adapter.addData("onRefresh  ===test========");
        adapter.addData("onRefresh  ===test========");
        adapter.addData("onRefresh  ===test========");
        adapter.addData("onRefresh  ===test========");
        adapter.addData("onRefresh  ===test========");
        adapter.addData("onRefresh  ===test========");
        adapter.addData("onRefresh  ===test========");
        adapter.addData("onRefresh  ===test========");
        adapter.addData("onRefresh  ===test========");
        adapter.addData("onRefresh  ===test========");
        adapter.addData("onRefresh  ===test========");
        adapter.addData("onRefresh  ===test========");
        adapter.addData("onRefresh  ===test========");
        adapter.addData("onRefresh  ===test========");
        adapter.addData("onRefresh  ===test========");
        adapter.addData("onRefresh  ===test========");
        adapter.addData("onRefresh  ===test========");
        adapter.addData("onRefresh  ===test========");
        adapter.addData("onRefresh  ===test========");
        adapter.addData("onRefresh  ===test========");
        adapter.addData("onRefresh  ===test========");
        adapter.addData("onRefresh  ===test========");
        adapter.addData("onRefresh  ===test========");
        adapter.addData("onRefresh  ===test========");
        adapter.addData("onRefresh  ===test========");
        adapter.addData("onRefresh  ===test========");
        adapter.addData("onRefresh  ===test========");
        adapter.addData("onRefresh  ===test========");
        adapter.addData("onRefresh  ===test========");
        adapter.addData("onRefresh  ===test========");
        adapter.addData("onRefresh  ===test========");
        adapter.addData("onRefresh  ===test========");
        adapter.addData("onRefresh  ===test========");
        adapter.addData("onRefresh  ===test========");
        adapter.addData("onRefresh  ===test========");
        adapter.addData("onRefresh  ===test========");
        adapter.addData("onRefresh  ===test========");
        adapter.addData("onRefresh  ===test========");
        adapter.addData("onRefresh  ===test========");
        adapter.addData("onRefresh  ===test========");
        adapter.addData("onRefresh  ===test========");
        adapter.addData("onRefresh  ===test========");
        adapter.addData("onRefresh  ===test========");
        adapter.addData("onRefresh  ===test========");
        adapter.addData("onRefresh  ===test========");
        adapter.addData("onRefresh  ===test========");
        adapter.addData("onRefresh  ===test========");
        adapter.addData("onRefresh  ===test========");
        adapter.addData("onRefresh  ===test========");
        adapter.addData("onRefresh  ===test========");
        adapter.addData("onRefresh  ===test========");
        adapter.addData("onRefresh  ===test========");
        adapter.addData("onRefresh  ===test========");
        adapter.addData("onRefresh  ===test========");
        adapter.addData("onRefresh  ===test========");
        adapter.addData("onRefresh  ===test========");
        adapter.addData("onRefresh  ===test========");
        adapter.addData("onRefresh  ===test========");
        adapter.addData("onRefresh  ===test========");
        adapter.addData("onRefresh  ===test========");
        adapter.addData("onRefresh  ===test========");
        refreshLayout.setRefreshing(false);
    }
}
