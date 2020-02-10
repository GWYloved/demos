package com.pc.demos.constraintlayout;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.constraintlayout.helper.widget.Flow;
import butterknife.BindView;
import butterknife.OnClick;
import com.pc.demos.R;
import com.pc.demos.activity.BaseActivity;

public class ConstraintFlowActivity extends BaseActivity {
    @BindView(R.id.flow)
    Flow mFlow;


    public static void start(Activity activity){
        Intent intent = new Intent(activity, ConstraintFlowActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected int getLayoutInflaterId() {
        return R.layout.activity_constraint_flow;
    }

    @Override
    protected void init() {

    }

    @OnClick(R.id.KR)
    void onKRClick(){
    }
}
