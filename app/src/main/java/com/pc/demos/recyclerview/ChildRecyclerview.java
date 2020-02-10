package com.pc.demos.recyclerview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by YinPengcheng on 2019-12-26 16:38 email: mikilangkilo.yin@ximalaya.com
 * 子recyclerview屏蔽父recyclerview方法
 */
public class ChildRecyclerview extends RecyclerView {

    public ChildRecyclerview(@NonNull Context context) {
        super(context);
    }

    public ChildRecyclerview(@NonNull Context context,
        @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        //父view不要拦截点击事件
        getParent().requestDisallowInterceptTouchEvent(true);
        return super.dispatchTouchEvent(ev);
    }
}
