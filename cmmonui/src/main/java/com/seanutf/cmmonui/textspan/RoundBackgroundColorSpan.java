package com.seanutf.cmmonui.textspan;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.style.ReplacementSpan;

import com.seanutf.cmmonui.util.DensityUtil;

public class RoundBackgroundColorSpan extends ReplacementSpan {
    private int bgColor;
    private int textColor;
    private int firstBottom;


    public RoundBackgroundColorSpan(int bgColor, int textColor) {
        super();
        this.bgColor = bgColor;
        this.textColor = textColor;
    }

    @Override
    public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
        //设置文字的总宽度   在以前的基础上添加  15dp
        return ((int) paint.measureText(text, start, end) + DensityUtil.dp2px(12));
    }

    @Override
    public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {

        int color1 = paint.getColor();
        paint.setColor(this.bgColor);
        RectF rectF = new RectF(x, top, x + ((int) paint.measureText(text, start, end) + DensityUtil.dp2px(10)), bottom - DensityUtil.dp2px(1));
        canvas.drawRoundRect(rectF, DensityUtil.dp2px(3), DensityUtil.dp2px(3), paint);
        paint.setColor(this.textColor);
        canvas.drawText(text, start, end, x + DensityUtil.dp2px(5), y - DensityUtil.dp2px(2f), paint);
        paint.setColor(color1);
    }
}
