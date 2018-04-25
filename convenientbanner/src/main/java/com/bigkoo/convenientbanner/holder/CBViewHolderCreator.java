package com.bigkoo.convenientbanner.holder;

import android.view.View;

/**
 * @ClassName :  ViewHolderCreator 
 * @Description : 
 * @Author Sai
 * @Date 2014年11月30日 下午3:29:34
 */
public interface CBViewHolderCreator {
	Holder createHolder(View itemView);
	int getLayoutId();
}
