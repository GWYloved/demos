package com.pc.demos.constraintlayout;

import android.app.Activity;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.pc.demos.R;
import com.pc.demos.activity.BaseActivity;
import com.pc.demos.adapter.SimpleOriginAdapter;
import com.pc.demos.callback.OneSimpleAction;
import java.util.HashMap;
import java.util.Map;

public class ConstraintHostActivity extends BaseActivity {
    @BindView(R.id.rv_list)
    RecyclerView mRecyclerView;

    private Map<String, OneSimpleAction> map = new HashMap<>();

    public static void start(Activity activity){
        Intent intent = new Intent(activity, ConstraintHostActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void init() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        map.put("Flow", () -> ConstraintFlowActivity.start(ConstraintHostActivity.this));
        mRecyclerView.setAdapter(new SimpleOriginAdapter(map));
    }

    @Override
    protected int getLayoutInflaterId() {
        return R.layout.constraint_host;
    }
}
