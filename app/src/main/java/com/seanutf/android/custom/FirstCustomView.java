package com.seanutf.android.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class FirstCustomView extends View {

    public FirstCustomView(Context context) {
        super(context);
    }

    public FirstCustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public FirstCustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //设置画笔的基本属性
        Paint paint = new Paint();

        //1.画了一个红环
        paint.setColor(Color.RED);  //设置画笔的颜色
        paint.setStyle(Paint.Style.STROKE); //设置填充样式,描边
        paint.setStrokeWidth(50); //设置画笔宽度，单位px
        //画图
        //canvas.drawCircle(190,200, 150, paint);

        //2.画了一大一小不同颜色的同心圆
        paint.setColor(0xFFFF0000);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(50);
        //canvas.drawCircle(190,200,150, paint);

        paint.setColor(0x7EFFFF00);
        //canvas.drawCircle(190,200, 100, paint);

        /*
        3.Paint基本Api
        setStyle()
        设置填充样式
        3种模式：仅填充内部，仅描边，填充内部和描边
        setStrokeWidth()
        设置描边宽度
        * */

        /*Canvas使用基础
        //设置背景
        drawColor(), drawARGB(), drawRGB()
         */

        //1.设置画布背景为紫色
        canvas.drawRGB(255, 0, 255);
        //同效果，其他形式实现
        //canvas.drawRGB(0xFF, 0x00, 0xFF);
        //canvas.drawColor(0xFFFF00FF);
        //canvas.drawARGB(0xFF, 0xFF, 0, 0xFF);

    }
}
