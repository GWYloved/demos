package com.pc.demos;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import com.pc.demos.activity.BaseActivity;
import com.pc.demos.adapter.SimpleOriginAdapter;
import com.pc.demos.animation.AnimationAc;
import com.pc.demos.callback.OneSimpleAction;
import com.pc.demos.constraintlayout.ConstraintHostActivity;
import com.pc.demos.recyclerview.RecyclerviewActivity;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends BaseActivity {
    @BindView(R.id.rv_list)
    RecyclerView mRecyclerView;

    @Override
    protected void init() {
        Map map = new HashMap<String, OneSimpleAction>();
        map.put("constraintlayout",
            (OneSimpleAction) () -> ConstraintHostActivity.start(MainActivity.this));
        map.put("recyclerview",(OneSimpleAction) () -> RecyclerviewActivity.start(MainActivity.this));
        map.put("animation",(OneSimpleAction)  () -> AnimationAc.start(MainActivity.this));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(new SimpleOriginAdapter(map));
    }

    @Override
    protected int getLayoutInflaterId() {
        return R.layout.activity_main;
    }
}
