package com.pc.demos.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.pc.demos.R;
import com.pc.demos.Utils.MapUtils;
import com.pc.demos.callback.OneSimpleAction;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by YinPengcheng on 2019-12-14 14:00 email: mikilangkilo.yin@ximalaya.com simple and easy
 * to write {@link RecyclerView}`s adapter
 */
public class SimpleOriginAdapter extends RecyclerView.Adapter {

    private Map<String, OneSimpleAction> maps = new HashMap<>();
    private List<String> keyList = new ArrayList<>();

    public SimpleOriginAdapter(Map<String, OneSimpleAction> titles) {
        this.maps = titles;
        this.keyList = MapUtils.getMapKeyList(titles);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.simple_origin_adapter_item, parent, false);
        return new SimpleOriginViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SimpleOriginViewHolder viewHolder = (SimpleOriginViewHolder) holder;
        viewHolder.title.setText(keyList.get(position));
        viewHolder.action = maps.get(keyList.get(position));
        viewHolder.title.setOnClickListener(viewHolder);
    }

    @Override
    public int getItemCount() {
        return this.maps.size();
    }

    public class SimpleOriginViewHolder extends ViewHolder implements OnClickListener {

        @BindView(R.id.tv_title)
        TextView title;

        private OneSimpleAction action;

        @Override
        public void onClick(View v) {
            if (action != null) {
                action.action();
            }
        }

        public SimpleOriginViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
