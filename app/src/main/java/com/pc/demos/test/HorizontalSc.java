package com.pc.demos.test;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.HorizontalScrollView;
import android.widget.TextView;
import com.pc.demos.R;

/**
 * Created by YinPengcheng on 2019-12-17 17:53 email: mikilangkilo.yin@ximalaya.com
 */
public class HorizontalSc extends HorizontalScrollView {
    private TextView mTextView;
    public HorizontalSc(Context context) {
        super(context);
        init(context);
    }

    public HorizontalSc(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public HorizontalSc(Context context, AttributeSet attrs,
        int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        LayoutInflater.from(context).inflate(R.layout.t1, this, true);
        mTextView = findViewById(R.id.text);
        mTextView.setText("gggggg");
    }

}
