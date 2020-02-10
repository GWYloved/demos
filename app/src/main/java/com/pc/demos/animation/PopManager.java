package com.pc.demos.animation;

import android.app.Activity;
import com.pc.demos.animation.MyPopupSurface.OnDismiss;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by YinPengcheng on 2020-02-09 14:13 email: mikilangkilo.yin@ximalaya.com
 */
public class PopManager implements OnDismiss{
    private static PopManager popManager;
    private Queue<MyPopupSurface> list = new LinkedList<>();
    private MyPopupSurface cache;
    private Activity activity;
    public static PopManager getInstance(){
        synchronized (PopManager.class){
            if (popManager == null){
                synchronized (PopManager.class){
                    popManager = new PopManager();
                }
            }
            return popManager;
        }
    }

    public PopManager() {
    }

    public void add(Activity activity, MyPopupSurface popupSurface){
        list.add(popupSurface);
        this.activity = activity;
        popupSurface.setOnDismiss(this);
        if (list.size() == 1){
            cache = popupSurface;
            popupSurface.showMoreWindow(activity);
        }
    }

    public void removeAll(){
        list.clear();
        if (cache != null && cache.isShowing()){
            cache.dismiss();
            cache = null;
        }
    }

    @Override
    public void dismiss() {
        removeShowing();
        showNext();
    }

    private void removeShowing(){
        if (cache == null){
            return;
        }
        cache.dismiss();
        list.remove(cache);
        if (list.size() == 0){
            cache = null;
        }
    }

    private void showNext(){
        if (list.size() == 0){
            return;
        }
        MyPopupSurface surface = list.element();
        if (surface == null)
            return;
        cache = surface;
        cache.showMoreWindow(activity);
    }
}
