package com.pc.demos.activity;

import android.app.Activity;
import android.os.Bundle;
import androidx.annotation.Nullable;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by YinPengcheng on 2019-12-14 15:03 email: mikilangkilo.yin@ximalaya.com
 */
public abstract class BaseActivity extends Activity {

    protected abstract int getLayoutInflaterId();
    protected abstract void init();

    private Unbinder mUnBinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutInflaterId());
        mUnBinder = ButterKnife.bind(this);
        init();
    }


    @Override
    protected void onDestroy() {
        mUnBinder.unbind();
        super.onDestroy();
    }
}
