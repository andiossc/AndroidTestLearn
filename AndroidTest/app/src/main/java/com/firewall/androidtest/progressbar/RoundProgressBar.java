package com.firewall.androidtest.progressbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.firewall.androidtest.R;

/**
 * Created by Firewall on 2017/8/20.
 */

public class RoundProgressBar extends View {
    /**
     * 画笔对象的引用
     */
    private Paint paint;
    /**
     * 圆环的颜色
     */
    private int ringColor;
    /**
     * 圆环进度的颜色
     */
    private int ringProgressColor;
    /**
     * 设置圆心进度条中间的背景色
     */
    private int centreColor;
    /**
     * 中间进度百分比的字符串的颜色
     */
    private int textColor;
    /**
     * 中间进度百分比的字符串的字体
     */
    private float textSize;
    /**
     * 圆环的宽度
     */
    private float ringWidth;
    /**
     * 最大进度
     */
    private int max;
    /**
     * 当前进度
     */
    private int progress;
    /**
     * 进度开始的角度数
     */
    private int startAngle;
    /**
     * 是否显示中间的数字进度
     */
    private boolean textIsDisplayable;
    /**
     * 进度的风格,实心或者空心
     */
    private int style;

    public static final int STROKE = 0;
    public static final int FILL = 1;

    public RoundProgressBar(Context context) {
        this(context,null);
    }

    public RoundProgressBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public RoundProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint();
//        TypedArray mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.RoundProgressBar);
//        ringColor = mTypedArray.getColor(R.styleable.RoundProgressBar_ringColor,0xff50c0e9);
//        ringProgressColor = mTypedArray.getColor(R.styleable.RoundProgressBar_ringProgressColor,0xffffc641);
//        textColor = mTypedArray.getColor(R.styleable.RoundProgressBar_textColor,0xffff5f5f);
//        textSize = mTypedArray.getDimension(R.styleable.RoundProgressBar_textSize,25);
//        ringWidth = mTypedArray.getDimension(R.styleable.RoundProgressBar_ringWidth,10);
//        max = mTypedArray.getInteger(R.styleable.RoundProgressBar_max,100);
//        progress = mTypedArray.getInt(R.styleable.RoundProgressBar_progress,0);
//        textIsDisplayable = mTypedArray.getBoolean(R.styleable.RoundProgressBar_textIsDisplayable,true);
//        style  =mTypedArray.getInt(R.styleable.RoundProgressBar_style,0);
//        startAngle = mTypedArray.getInt(R.styleable.RoundProgressBar_startAngle,-90);
//        centreColor = mTypedArray.getColor(R.styleable.RoundProgressBar_centreColor,0);
//        mTypedArray.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int centre = getWidth()/2;
        int radius = (int) (centre - ringWidth/2);

        /**
         * 画中心的颜色
         */
        if(centreColor != 0){
            paint.setAntiAlias(true);
            paint.setColor(centreColor);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawCircle(centre,centre,radius,paint);
        }

        /**
         * 画最外层的大圆环
         */
        paint.setColor(ringColor);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(ringWidth);
        paint.setAntiAlias(true);
        canvas.drawCircle(centre,centre,radius,paint);

        /**
         * 画圆弧,画圆弧的进度
         */
        paint.setStrokeWidth(ringWidth);
        paint.setColor(ringProgressColor);
        RectF oval =new RectF(centre-radius,centre-radius,centre+radius,centre+radius);
        switch (style){
            case STROKE:
                paint.setStyle(Paint.Style.STROKE);
                /*第二个参数是进度开始的角度，-90表示从12点方向开始走进度，如果是0表示从三点钟方向走进度，依次类推
                 *public void drawArc(RectF oval, float startAngle, float sweepAngle, boolean useCenter, Paint paint)
                    oval :指定圆弧的外轮廓矩形区域。
                    startAngle: 圆弧起始角度，单位为度。
                    sweepAngle: 圆弧扫过的角度，顺时针方向，单位为度。
                    useCenter: 如果为True时，在绘制圆弧时将圆心包括在内，通常用来绘制扇形。
                    paint: 绘制圆弧的画板属性，如颜色，是否填充等
                */
                canvas.drawArc(oval,startAngle,360*progress/max,false,paint);
                break;
            case FILL:
                paint.setStyle(Paint.Style.FILL_AND_STROKE);
                if(progress!=0){
                    canvas.drawArc(oval,startAngle,360*progress/max,true,paint);
                }
                break;
        }

        /**
         * 画进度百分比
         */
        paint.setStrokeWidth(0);
        paint.setColor(textColor);
        paint.setTextSize(textSize);
        paint.setTypeface(Typeface.DEFAULT_BOLD);//设置字体
        int percent = (int)(((float)progress/(float)max)*100);
        //测量字体宽度，我们需要根据字体的宽度设置在圆环中间
        float textWidth = paint.measureText(percent+"%");
        if(textIsDisplayable && percent!=0 && style == STROKE){
            canvas.drawText(percent+"%",centre-textWidth/2,centre+textSize/2,paint);
        }
    }

    public synchronized int getMax(){
        return max;
    }

    /**
     * 设置进度的最大值
     * @param max
     */
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

    public int getCircleColor(){
        return ringColor;
    }

    public void setCircleColor(int CircleColor){
        this.ringColor = CircleColor;
    }

    public int getCircleProgressColor(){
        return ringProgressColor;
    }

    public void setCircleProgressColor(int CircleProgressColor){
        this.ringProgressColor = CircleProgressColor;
    }

    public int getTextColor(){
        return textColor;
    }

    public void setTextColor(int textColor){
        this.textColor = textColor;
    }
}
