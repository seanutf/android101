package com.seanutf.cmmonui.textspan;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.style.ImageSpan;

import androidx.annotation.NonNull;

/**
 * 一个可以和文本垂直居中的ImageSpan
 */
public class CenteredImageSpan extends ImageSpan {
    public CenteredImageSpan(@NonNull Context context, int resourceId) {
        super(context, resourceId);
    }

    public CenteredImageSpan(Context context, final Bitmap bitmap) {
        super(context, bitmap);
    }

    @Override
    public void draw(@NonNull Canvas canvas, CharSequence text,
                     int start, int end, float x,
                     int top, int y, int bottom, @NonNull Paint paint) {
        // image to draw
        Drawable b = getDrawable();
        // font metrics of text to be replaced
        Paint.FontMetricsInt fm = paint.getFontMetricsInt();
        int transY = (y + fm.descent + y + fm.ascent) / 2
                - b.getBounds().bottom / 2;

        canvas.save();
        canvas.translate(x, transY);
        b.draw(canvas);
        canvas.restore();
    }
}
