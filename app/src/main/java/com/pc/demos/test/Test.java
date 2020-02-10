package com.pc.demos.test;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.constraintlayout.widget.ConstraintLayout;
import butterknife.BindView;
import com.pc.demos.R;
import com.pc.demos.activity.BaseActivity;

public class Test extends BaseActivity {
    @BindView(R.id.root)
    ConstraintLayout mRootView;
    @Override
    protected int getLayoutInflaterId() {
        return R.layout.activity_test;
    }

    @Override
    protected void init() {
        mRootView.addView(new HorizontalSc(this));
    }
}
