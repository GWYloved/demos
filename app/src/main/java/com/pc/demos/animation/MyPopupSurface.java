package com.pc.demos.animation;

import android.app.Activity;
import android.content.res.TypedArray;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import com.pc.demos.R;
import com.pc.demos.animation.JSurfaceView.OnFrameFinishedListener;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by YinPengcheng on 2020-02-03 18:10 email: mikilangkilo.yin@ximalaya.com
 */
public class MyPopupSurface extends PopupWindow implements View.OnClickListener {
    private View rootView;
    private RelativeLayout contentView;
    private Activity mContext;
    private JSurfaceView mSurfaceView;
    private int mResourceId;
    private int mGap;
    private long mDuration;
    public interface OnDismiss {
        void dismiss();
    }
    private OnDismiss dismiss;
    private Runnable mDismissRunnable = new Runnable() {
        @Override
        public void run() {
            if (MyPopupSurface.this.isShowing()) {
                MyPopupSurface.this.dismiss();
            }
        }
    };
    public MyPopupSurface(Activity context, int resourceId,long duration, int gap) {
        super(context);
        this.mContext = context;
        this.mResourceId = resourceId;
        this.mDuration = duration;
        this.mGap = gap;
    }

    public static void setPopupWindowTouchModal(PopupWindow popupWindow,
        boolean touchModal) {
        if (null == popupWindow) {
            return;
        }
        Method method;
        try {
            method = PopupWindow.class.getDeclaredMethod("setTouchModal",
                boolean.class);
            method.setAccessible(true);
            method.invoke(popupWindow, touchModal);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void showMoreWindow(Activity anchor) {
        Log.e("mikilangkilo", "MyPopup/showMoreWindow: 1");
        LayoutInflater inflater = LayoutInflater.from(mContext);
        Log.e("mikilangkilo", "MyPopup/showMoreWindow: 2");
        rootView = inflater.inflate(R.layout.dialog, null);
        Log.e("mikilangkilo", "MyPopup/showMoreWindow: 3");
        rootView.setOnClickListener(this);
        Log.e("mikilangkilo", "MyPopup/showMoreWindow: 4");
        initView();
        Log.e("mikilangkilo", "MyPopup/showMoreWindow: 5");
        showAtLocation(anchor.getWindow().getDecorView().findViewById(android.R.id.content), Gravity.BOTTOM, 0, 0);
    }

    public void enqueue(Activity anchor){
        PopManager.getInstance().add(anchor,this);
    }

    public void setOnDismiss(OnDismiss dismiss){
        this.dismiss = dismiss;
    }

    private void initView() {
        Log.e("mikilangkilo", "MyPopup/initView: 1");
        setContentView(rootView);
        Log.e("mikilangkilo", "MyPopup/initView: 2");
        setWidth(mContext.getWindowManager().getDefaultDisplay().getWidth());
        setHeight(mContext.getWindowManager().getDefaultDisplay().getHeight());
        Log.e("mikilangkilo", "MyPopup/initView: 3");
        setBackgroundDrawable(new BitmapDrawable());
        Log.e("mikilangkilo", "MyPopup/initView: 4");
        setFocusable(true);
        Log.e("mikilangkilo", "MyPopup/initView: 5");
        setClippingEnabled(false);
        Log.e("mikilangkilo", "MyPopup/initView: 6");
        this.mSurfaceView = rootView.findViewById(R.id.image);
        Log.e("mikilangkilo", "MyPopup/initView: 7");
        this.mSurfaceView.setGapTime(mGap);
        Log.e("mikilangkilo", "MyPopup/initView: 8");
        this.mSurfaceView.setBitmapResoursID(getData(mResourceId));
        Log.e("mikilangkilo", "MyPopup/initView: 9");
        setTouchable(false);
        this.mSurfaceView.setOnFrameFinisedListener(new OnFrameFinishedListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onStop() {
                ((Activity)mContext).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (dismiss != null){
                            Log.e("mikilangkilo", "MyPopupSurface/run: nowdismiss");
                            dismiss.dismiss();
                        }
                    }
                });
            }
        });
        this.mSurfaceView.start();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            try {
                Field mLayoutInScreen = PopupWindow.class.getDeclaredField("mLayoutInScreen");
                mLayoutInScreen.setAccessible(true);
                mLayoutInScreen.set(this, true);
                Log.e("mikilangkilo", "MyPopup/initView: 10");
            } catch (NoSuchFieldException e) {
                Log.e("mikilangkilo", "MyPopup/initView: 11");
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                Log.e("mikilangkilo", "MyPopup/initView: 12");
                e.printStackTrace();
            }
        }
        setFocusable(true);
        rootView.setFocusableInTouchMode(true);
        rootView.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK)
                    return true;
                return false;
            }
        });
    }

    @Override
    public void onClick(View v) {
//        this.dismiss();
    }

    public int[] getData(int resId) {
        TypedArray array = mContext.getResources().obtainTypedArray(resId);

        int len = array.length();
        int[] intArray = new int[array.length()];

        for (int i = 0; i < len; i++) {
            intArray[i] = array.getResourceId(i, 0);
        }
        array.recycle();
        return intArray;
    }
}
