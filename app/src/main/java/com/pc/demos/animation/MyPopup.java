package com.pc.demos.animation;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import com.pc.demos.R;
import java.lang.reflect.Field;

/**
 * Created by YinPengcheng on 2020-02-03 18:10 email: mikilangkilo.yin@ximalaya.com
 */
public class MyPopup extends PopupWindow implements View.OnClickListener {
    private View rootView;
    private RelativeLayout contentView;
    private Activity mContext;
    private ImageView mImageView;
    public MyPopup(Activity context) {
        super(context);
        this.mContext = context;
    }


    public void showMoreWindow(View anchor) {
        LayoutInflater inflater = (LayoutInflater) mContext
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        rootView = inflater.inflate(R.layout.dialog, null);
        rootView.setOnClickListener(this);
        initView();
        showAtLocation(anchor, Gravity.BOTTOM, 0, 0);
    }

    private void initView() {
        setContentView(rootView);
        setWidth(mContext.getWindowManager().getDefaultDisplay().getWidth());
        setHeight(mContext.getWindowManager().getDefaultDisplay().getHeight());
        setBackgroundDrawable(new BitmapDrawable());
        setFocusable(true);
        setClippingEnabled(false);
        this.mImageView = rootView.findViewById(R.id.image);
        mImageView.setImageResource(R.drawable.ani);
        AnimationDrawable animation = (AnimationDrawable) mImageView.getDrawable();
        animation.start();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            try {
                Field mLayoutInScreen = PopupWindow.class.getDeclaredField("mLayoutInScreen");
                mLayoutInScreen.setAccessible(true);
                mLayoutInScreen.set(this, true);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onClick(View v) {
        this.dismiss();
    }
}
