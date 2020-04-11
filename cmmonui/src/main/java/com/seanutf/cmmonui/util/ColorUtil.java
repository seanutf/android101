package com.seanutf.cmmonui.util;

import android.graphics.Color;

public class ColorUtil {

    /**
     * 根据百分比改变颜色透明度
     */
    public static int changeAlpha(int color, Float fraction) {
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        int alpha = (int) (Color.alpha(color) * fraction);
        return Color.argb(alpha, red, green, blue);
    }
}
