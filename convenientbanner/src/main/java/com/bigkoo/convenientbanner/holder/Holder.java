package com.bigkoo.convenientbanner.holder;

import android.content.Context;
import android.view.View;

public interface Holder<T> {
    View createView(Context context);

    void updateUI(Context context, int position, T data);
}