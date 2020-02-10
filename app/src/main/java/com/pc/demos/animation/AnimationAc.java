package com.pc.demos.animation;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import butterknife.BindView;
import butterknife.OnClick;
import com.pc.demos.R;
import com.pc.demos.activity.BaseActivity;

public class AnimationAc extends BaseActivity {

    @BindView(R.id.button)
    Button button;
    @BindView(R.id.button1)
    Button button1;

    private Context context;

    public static void start(Activity activity) {
        Intent intent = new Intent(activity, AnimationAc.class);
        activity.startActivity(intent);
    }

    @Override
    protected int getLayoutInflaterId() {
        return R.layout.activity_animation;
    }

    @Override
    protected void init() {
        context = this;
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
//                mBuilder = new AlertDialog.Builder(context);
//                mAlertDialog = mBuilder.create();
//                View view = LayoutInflater.from(context).inflate(R.layout.dialog, null);
//                ImageView imageView = view.findViewById(R.id.image);
//                Window window = mAlertDialog.getWindow();
//                window.getDecorView().setPadding(0, 0, 0, 0);
//                WindowManager windowManager = getWindowManager();
//                Display display = windowManager.getDefaultDisplay();
//                WindowManager.LayoutParams lp = mAlertDialog.getWindow().getAttributes();
//                lp.height = (int)(display.getHeight()); //设置宽度
//                lp.width = (int)(display.getWidth()); //设置宽度
//                window.setAttributes(lp);
//                mAlertDialog.show();
//                imageView.setOnClickListener(new OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        mAlertDialog.dismiss();
//                    }
//                });
//                imageView.setBackgroundResource(R.drawable.ani);
//                AnimationDrawable animation = (AnimationDrawable) imageView.getBackground();
//                mAlertDialog.setContentView(view);
//                animation.start();
//                MyPopup popup = new MyPopup(AnimationAc.this);
//                popup.showMoreWindow(AnimationAc.this.getWindow().getDecorView().findViewById(android.R.id.content));
                Log.e("mikilangkilo", "AnimationAc/onClick: ");
                MyPopupSurface popupSurface = new MyPopupSurface(AnimationAc.this, R.array.ani_11, 3000, 10);
                popupSurface.enqueue(AnimationAc.this);
                MyPopupSurface popupSurface1 = new MyPopupSurface(AnimationAc.this, R.array.ani_11, 3000, 20);

                popupSurface1.enqueue(AnimationAc.this);
                MyPopupSurface popupSurface2 = new MyPopupSurface(AnimationAc.this, R.array.ani_11, 3000, 30);

                popupSurface2.enqueue(AnimationAc.this);

            }
        });
        button1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                PopManager.getInstance().removeAll();
            }
        });
    }

    AlertDialog.Builder mBuilder;
    AlertDialog mAlertDialog;

}
