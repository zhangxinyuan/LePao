package com.project.graduation.jackben.pedometer.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.project.graduation.jackben.pedometer.R;

/**
 * 自定义显示刻度的View
 * User: XinYuan(1054344254@qq.com)
 * Date: 2015-11-07
 * Time: 10:51
 */
public class ShowPercentView extends View {

    private int percent;
    private int allLineColor;
    private int percentLineColorLow;
    private Paint percentPaint;

    private int height;
    private int width;
    private int pointX;
    private int pointY;
    private int allLineWidth = 8;
    private int percentLineWidth = 10;
    private int lineHeight = 30;
        private int targetStepCount=1000;
    public ShowPercentView(Context context) {
        super(context);
        init(null, 0);
    }

    public ShowPercentView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public ShowPercentView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr);
    }

    private void init(AttributeSet attrs, int defStyleAttr) {

        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ShowPercentView, defStyleAttr, 0);

        percent = typedArray.getInt(R.styleable.ShowPercentView_percent, 0);
        allLineColor = typedArray.getColor(R.styleable.ShowPercentView_allLineColor, Color.GRAY);
        percentLineColorLow = typedArray.getColor(R.styleable.ShowPercentView_percentLineColorLow, Color.GREEN);

        typedArray.recycle();

        initPaint();

    }

    private void initPaint() {
        //初始化比例画笔
        percentPaint = new Paint();
        percentPaint.setAntiAlias(true);//防锯齿


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        initPositionData();//定位置
        drawPercent(canvas);//绘百分图
    }

    private void drawPercent(Canvas canvas) {
        canvas.save();
        percentPaint.setColor(allLineColor);
        percentPaint.setStrokeWidth(allLineWidth);

        float degrees = (float) (360.0 / 100);

        canvas.translate(0, pointY);
        canvas.rotate(90, pointX, 0);

        for (int i = 0; i < 100; i++) {

            canvas.drawLine(0, 0, lineHeight, 0, percentPaint);
            canvas.rotate(degrees, pointX, 0);


        }
        canvas.restore();
        percentPaint.setColor(percentLineColorLow);
        percentPaint.setStrokeWidth(percentLineWidth);
        canvas.save();

        canvas.translate(0, pointY);
        canvas.rotate(90, pointX, 0);
        float currentPercent = (Float.valueOf(percent) / Float.valueOf(targetStepCount/100));
        for (int i = 0; i < currentPercent; i++) {
            canvas.drawLine(0, 0, lineHeight, 0, percentPaint);
            canvas.rotate(degrees, pointX, 0);

        }
        canvas.restore();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int d = (width >= height) ? height : width;
        setMeasuredDimension(d, d);
    }


    public void setPercent(int percent) {
        this.percent = percent;
        postInvalidate();
    }


    private void initPositionData() {
        height = getMeasuredHeight();//父窗体的高度
        width = getMeasuredWidth();//父窗体的宽度
        pointX = width / 2;
        pointY = height / 2;
    }
}
