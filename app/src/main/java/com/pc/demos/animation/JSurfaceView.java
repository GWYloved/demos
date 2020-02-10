package com.pc.demos.animation;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by YinPengcheng on 2020-02-05 15:10 email: mikilangkilo.yin@ximalaya.com
 */
public class JSurfaceView extends SurfaceView implements Callback , Runnable{
    private String TAG = "SurfaceViewAnimation";

    private SurfaceHolder mSurfaceHolder;

    private boolean mIsThreadRunning = true; // 线程运行开关
    public static boolean mIsDestroy = false;// 是否已经销毁

    private int[] mBitmapResourceIds;// 用于播放动画的图片资源id数组
    private int totalCount;//资源总数 用来判断动画循环
    private Canvas mCanvas; // 画图片
    private Bitmap mBitmap;// 显示的图片的bitmap

    private int mCurrentIndext;// 当前动画播放的位置
    private int mGapTime = 50;// 每帧动画持续存在的时间
    private boolean mIsRepeat = false;
    private List<String> mBitmapResourcePaths;

    private OnFrameFinishedListener mOnFrameFinishedListener;// 动画监听事件
    private Thread thread;

    Rect mSrcRect, mDestRect;

    public JSurfaceView(Context context) {
        this(context, null);
        initView();
    }

    public JSurfaceView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
    }

    public JSurfaceView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        initView();

    }

    private void initView() {

        mSurfaceHolder = this.getHolder();
        mSurfaceHolder.addCallback(this);
        mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_HARDWARE);

        //设置透明背景
        //setZOrderOnTop(true) 必须在setFormat方法之前，不然png的透明效果不生效
        setZOrderOnTop(true);
        mSurfaceHolder.setFormat(PixelFormat.TRANSLUCENT);

        mBitmapResourceIds = new int[1];
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        // 根据实际需要是利用start()函数外部开启调用开启动画线程，还是显示控件就开启动画
        mIsThreadRunning = true;
        new Thread(this).start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        destroy();
    }

    /**
     * 制图方法
     */
    private void drawView() {
        // 无资源文件退出
        Log.e("mikilangkilo", "JSurfaceView/drawView: "+mCurrentIndext +", length = "+mBitmapResourceIds.length);
        if (mBitmapResourceIds == null && mBitmapResourcePaths == null && mCurrentIndext >=mBitmapResourceIds.length) {
            Log.e("frameview", "the bitmapsrcIDs is null");
            mIsThreadRunning = false;

            return;
        }
        if (mCurrentIndext >= mBitmapResourceIds.length - 1){
            mIsThreadRunning = false;
            return;
        }

        Log.d(TAG, "drawView: mCurrentIndext=" + mCurrentIndext);
        Log.d(TAG, "drawView: Thread id = " + Thread.currentThread().getId());

        //防止是获取不到Canvas
        SurfaceHolder surfaceHolder = mSurfaceHolder;
        // 锁定画布
        synchronized (surfaceHolder) {
            if (surfaceHolder != null) {
                mCanvas = surfaceHolder.lockCanvas();
                Log.d(TAG, "drawView: mCanvas= " + mCanvas);
                if (mCanvas == null) {
                    return;
                }
            }
            try {

                if (surfaceHolder != null && mCanvas != null) {

                    synchronized (mBitmapResourceIds) {
                        if (mBitmapResourceIds != null && mBitmapResourceIds.length > 0) {
                            mBitmap = decodeSampledBitmapFromResource(getResources(), mBitmapResourceIds[mCurrentIndext], getWidth(), getHeight());
                        } else if (mBitmapResourcePaths != null && mBitmapResourcePaths.size() > 0) {
                            mBitmap = BitmapFactory.decodeFile(mBitmapResourcePaths.get(mCurrentIndext));

                        }
                    }
                    mBitmap.setHasAlpha(true);

                    if (mBitmap == null) {
                        return;
                    }

                    Paint paint = new Paint();
                    paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
                    mCanvas.drawPaint(paint);
                    paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
                    paint.setAntiAlias(true);
                    paint.setStyle(Paint.Style.STROKE);

                    mSrcRect = new Rect(0, 0, mBitmap.getWidth(), mBitmap.getHeight());
                    mDestRect = new Rect(0, 0, getWidth(), getHeight());
                    mCanvas.drawBitmap(mBitmap, mSrcRect, mDestRect, paint);

                    // 播放到最后一张图片
                    if (mCurrentIndext == totalCount - 1) {
                        //TODO 设置重复播放
                        //播放到最后一张，当前index置零
                        mCurrentIndext = 0;
                    }

                }

            } catch (Exception e) {
                Log.d(TAG, "drawView: e =" + e.toString());
                e.printStackTrace();
            } finally {

                mCurrentIndext++;

                if (mCurrentIndext >= totalCount) {
                    mCurrentIndext = 0;
                }
                if (mCanvas != null) {
                    // 将画布解锁并显示在屏幕上
                    if (getHolder() != null) {
                        surfaceHolder.unlockCanvasAndPost(mCanvas);
                    }
                }

                if (mBitmap != null) {
                    // 收回图片
                    mBitmap.recycle();
                }
            }
        }
    }

    @Override
    public void run() {
        if (mOnFrameFinishedListener != null) {
            mOnFrameFinishedListener.onStart();
        }
        Log.d(TAG, "run: mIsThreadRunning=" + mIsThreadRunning);
        // 每隔mGapTimems刷新屏幕
        while (mIsThreadRunning) {
            drawView();
            try {
                Thread.sleep(mGapTime);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (mOnFrameFinishedListener != null) {
            Log.e("mikilangkilo", "JSurfaceView/run: stop");
            mOnFrameFinishedListener.onStop();
        }
    }

    /**
     * 开始动画
     */
    public void start() {
        if (!mIsDestroy) {
            mCurrentIndext = 0;
            mIsThreadRunning = true;
            thread = new Thread(this);
            thread.start();
        } else {
            // 如果SurfaceHolder已经销毁抛出该异常
            try {
                throw new Exception("IllegalArgumentException:Are you sure the SurfaceHolder is not destroyed");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 防止内存泄漏
     */
    private void destroy() {
        //当surfaceView销毁时, 停止线程的运行. 避免surfaceView销毁了线程还在运行而报错.
        mIsThreadRunning = false;
        try {
            Thread.sleep(mGapTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        mIsDestroy = true;

        thread.interrupt();
        thread = null;

        if (mBitmap != null) {
            mBitmap.recycle();
            mBitmap = null;
        }

        if (mSurfaceHolder != null) {
            mSurfaceHolder.addCallback(null);
        }

        if (mOnFrameFinishedListener != null) {
            mOnFrameFinishedListener = null;
        }
    }

    /**
     * 设置动画播放素材的id
     *
     * @param bitmapResourceIds 图片资源id
     */
    public void setBitmapResoursID(int[] bitmapResourceIds) {
        // 防止主线程和子线程同时操作mBitmapResourceIds资源造成死锁
        synchronized (mBitmapResourceIds) {
            this.mBitmapResourceIds = bitmapResourceIds;
            totalCount = bitmapResourceIds.length;
        }
    }

    /**
     * 设置动画播放素材的路径
     *
     * @param bitmapResourcePaths
     */
    public void setmBitmapResourcePath(ArrayList bitmapResourcePaths) {
        this.mBitmapResourcePaths = bitmapResourcePaths;
        totalCount = bitmapResourcePaths.size();
    }

    /**
     * 设置每帧时间
     */
    public void setGapTime(int gapTime) {
        this.mGapTime = gapTime;
    }

    /**
     * 结束动画
     */
    public void stop() {
        mIsThreadRunning = false;
    }

    /**
     * 继续动画
     */
    public void reStart() {
        mIsThreadRunning = false;
    }

    /**
     * 设置动画监听器
     */
    public void setOnFrameFinisedListener(OnFrameFinishedListener onFrameFinishedListener) {
        this.mOnFrameFinishedListener = onFrameFinishedListener;
    }

    /**
     * 动画监听器
     *
     * @author qike
     */
    public interface OnFrameFinishedListener {

        /**
         * 动画开始
         */
        void onStart();

        /**
         * 动画结束
         */
        void onStop();
    }

    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        //加载图片
        BitmapFactory.decodeResource(res, resId, options);
        //计算缩放比
        options.inSampleSize = calculateInSampleSize(options, reqHeight, reqWidth);
        //重新加载图片
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    private static int calculateInSampleSize(BitmapFactory.Options options, int reqHeight, int reqWidth) {
        int height = options.outHeight;
        int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            int halfHeight = height / 2;
            int halfWidth = width / 2;
            //计算缩放比，是2的指数
            while ((halfHeight / inSampleSize) >= reqHeight && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }
}
