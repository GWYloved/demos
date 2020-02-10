package com.pc.demos.recyclerview;

import android.app.Activity;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import com.pc.demos.R;

public class RecyclerviewActivity extends AppCompatActivity {
    @BindView(R.id.rv_list)
    RecyclerView mRecyclerView;

    public static void start(Activity activity){
        Intent intent = new Intent(activity, RecyclerviewActivity.class);
        activity.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
