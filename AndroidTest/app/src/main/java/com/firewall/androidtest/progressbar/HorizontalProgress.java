package com.firewall.androidtest.progressbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import com.firewall.androidtest.R;

/**
 * Created by Firewall on 2017/8/20.
 */

public class HorizontalProgress extends View {

    private Paint mPaint;
    /**
     * 进度最大值
     */
    private int max;
    /**
     * 当前进度值
     */
    private int progress;
    /**
     * 未完成的分段的标志
     */
    private Bitmap undoneIcon;
    /**
     * 已完成的分度标志
     */
    private Bitmap completedIcon;
    /**
     * 中间的活动标题与进度
     */
    private String title_progress;
    /**
     * 进度背景颜色
     */
    private int backgroundColor;
    /**
     * 进度颜色
     */
    private int progressColor;
    /**
     * 文字大小
     */
    private int mTextSize = 12;
    /**
     * 文字颜色
     */
    private int textColor;
    /**
     * 分段活动的分段数量
     */
    private int sectionCount;

    /**
     * 控件的宽和高
     */
    private int mWidth;
    private int mHeigt;

    public HorizontalProgress(Context context) {
        this(context,null);
    }

    public HorizontalProgress(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public HorizontalProgress(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,R.styleable.HorizontalProgress,defStyleAttr,0);
        int n = a.getIndexCount();
        for(int i=0;i<n;i++){
            int attr = a.getIndex(i);
            switch (attr){
                case R.styleable.HorizontalProgress_max:
                    max = a.getInt(attr,100);
                    break;
                case R.styleable.HorizontalProgress_progress:
                    progress = a.getInt(attr,0);
                    break;
                case R.styleable.HorizontalProgress_completedIcon:
                    completedIcon = BitmapFactory.decodeResource(getResources(),a.getResourceId(attr,0));
                    break;
                case R.styleable.HorizontalProgress_undoneIcon:
                    undoneIcon = BitmapFactory.decodeResource(getResources(),a.getResourceId(attr,0));
                    break;
                case R.styleable.HorizontalProgress_title_progress:
                    title_progress = a.getString(attr);
                    break;
                case R.styleable.HorizontalProgress_backgroundColor:
                    backgroundColor = a.getColor(attr,0xffffffff);
                    break;
                case R.styleable.HorizontalProgress_progressColor:
                    progressColor = a.getColor(attr,0xFFFC7B02);
                    break;
                case R.styleable.HorizontalProgress_sectionCount:
                    sectionCount = a.getInt(attr,1);
                    break;
                case R.styleable.HorizontalProgress_titleTextSize:
                    mTextSize = a.getDimensionPixelSize(attr,(int) TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_SP, 8, getResources().getDisplayMetrics()));
//                    mTextSize = a.getDimensionPixelSize(attr,18);
                    break;
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mWidth = getWidth();
        mHeigt = getHeight();
        float progressPrecent = (float) progress/(float)max;
        /**
         * 画底部背景矩形
         */
        mPaint.setAntiAlias(true);
        mPaint.setColor(backgroundColor);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawRect(0,0,mWidth,mHeigt,mPaint);

        /**
         * 画第二层颜色
         */
        mPaint.setColor(progressColor);
        canvas.drawRect(0,0,mWidth*(progressPrecent),mHeigt,mPaint);

        /**
         * 画Icon
         */
        RectF bitmapRect;
        int stage = mWidth/sectionCount;
        for(int i=1;i<=sectionCount;i++){
            if(mWidth*(progressPrecent)<stage*i) {
                bitmapRect = new RectF(stage * i - undoneIcon.getWidth()*((float)mHeigt/(float)undoneIcon.getHeight()), mHeigt / 2 - undoneIcon.getHeight() / 2, stage * i, mHeigt / 2 + undoneIcon.getHeight() / 2);
                canvas.drawBitmap(completedIcon, null, bitmapRect, mPaint);
                textColor = backgroundColor;
            }else{
                bitmapRect = new RectF(stage * i - undoneIcon.getWidth()*((float)mHeigt/(float)undoneIcon.getHeight()), mHeigt / 2 - undoneIcon.getHeight() / 2 + 2, stage * i, mHeigt / 2 + undoneIcon.getHeight() / 2 - 2);
                canvas.drawBitmap(undoneIcon, null, bitmapRect, mPaint);
                textColor = progressColor;
            }
        }

        /**
         * 画中间的文字
         */
        Rect textRect = new Rect();
        mPaint.setTextSize(mTextSize);
        mPaint.getTextBounds(title_progress,0,title_progress.length(),textRect);
        if(0<progressPrecent && progressPrecent<0.35){
            mPaint.setColor(progressColor);
            canvas.drawText(title_progress, mWidth / 2 - textRect.width() / 2, mHeigt / 2 + textRect.height() / 2, mPaint);
        }else if(progressPrecent>0.35 && progressPrecent<0.65){
            mPaint.setColor(backgroundColor);
            canvas.drawText(title_progress, stage / 2 - textRect.width() / 2, mHeigt / 2 + textRect.height() / 2, mPaint);
        }else{
            mPaint.setColor(backgroundColor);
            canvas.drawText(title_progress, mWidth / 2 - textRect.width() / 2, mHeigt / 2 + textRect.height() / 2, mPaint);
        }
        Log.e("111111","textSize==="+mTextSize);
    }

    public synchronized int getMax(){
        return max;
    }

    public synchronized void setMax(int max){
        if(max<0){
            throw new IllegalArgumentException("max not less than 0");
        }
        this.max = max;
    }

    /**
     * 获取进度,需要同步
     * @return
     */
    public synchronized int getProgress(){
        return progress;
    }

    public synchronized void setProgress(int progress){
        if(progress<0){
            throw new IllegalArgumentException("progress not less than 0");
        }
        if(progress>max){
            progress = max;
        }
        if(progress<=max){
            this.progress = progress;
            postInvalidate();
        }
    }

    public void setProgressColor(int color){
        this.progressColor = color;
    }

    public void setTextSize(int size){
        this.mTextSize = size;
    }

    public void setTitleProgress(String title){
        this.title_progress = title;
    }

}
