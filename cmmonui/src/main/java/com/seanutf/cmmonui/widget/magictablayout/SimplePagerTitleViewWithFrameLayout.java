package com.seanutf.cmmonui.widget.magictablayout;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.seanutf.cmmonui.util.DensityUtil;


/**
 * 带文本的指示器标题
 * 外面包了一层FrameLayout 底部对齐
 */
public class SimplePagerTitleViewWithFrameLayout extends FrameLayout implements IMeasurablePagerTitleView {
    protected int mSelectedColor;
    protected int mNormalColor;
    protected TextView tv;
    protected int paddingHorizontal = 3;
    private Context context;
    private Typeface selectedTypeface;
    private Typeface unSelectedTypeface;

    public SimplePagerTitleViewWithFrameLayout(Context context) {
        super(context, null);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        //        tv=((AppCompatTextView) View.inflate(context, R.layout.common_textview, null));
        tv = new TextView(context);
        LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.gravity = Gravity.CENTER;
        tv.setLayoutParams(lp);
        //        setGravity(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL);

        int paddingHorizontal = DensityUtil.dp2px(this.paddingHorizontal);
        setPadding(paddingHorizontal, 0, paddingHorizontal, 0);
        tv.setSingleLine();
        tv.setEllipsize(TextUtils.TruncateAt.END);
        tv.setIncludeFontPadding(false);
        addView(tv);
    }

    public void setPaddingHorizontal(int p) {
        this.paddingHorizontal = p;
        int paddingHorizontal = DensityUtil.dp2px(p);
        setPadding(paddingHorizontal, 0, paddingHorizontal, 0);

    }

    //    @Override
    //    public void setScaleX(float scaleX) {
    //        tv.setScaleX(scaleX);
    //    }
    //
    //    @Override
    //    public void setScaleY(float scaleY) {
    //    tv.setScaleY(scaleY);
    //    }
    //
    //    @Override
    //    public void setY(float y) {
    //    tv.setY(y);
    //    }

    public void setLayoutGravity(int gravity) {
        ((FrameLayout.LayoutParams) tv.getLayoutParams()).gravity = gravity;
        tv.requestLayout();
    }

    public void setGravity(int gravity) {
        tv.setGravity(gravity);
    }

    public void setTextColor(int color) {
        tv.setTextColor(color);
    }

    public void setTextSize(float size) {
        tv.setTextSize(size);
    }

    public void setTextSize(int unit, float size) {
        tv.setTextSize(unit, size);
    }

    public void setTypeface(Typeface tf) {
        tv.setTypeface(tf);
    }

    public void setSelectedTypeface(Typeface tf) {
        this.selectedTypeface = tf;
    }

    public void setUnSelectedTypeface(Typeface tf) {
        this.unSelectedTypeface = tf;
    }


    public void setText(CharSequence text) {
        tv.setText(text);
    }

    @Override
    public void onSelected(int index, int totalCount) {
        tv.setTextColor(mSelectedColor);
        if (selectedTypeface != null) {
            tv.setTypeface(selectedTypeface);
        }
    }

    @Override
    public void onDeselected(int index, int totalCount) {
        tv.setTextColor(mNormalColor);
        if (unSelectedTypeface != null) {
            tv.setTypeface(unSelectedTypeface);
        }
    }

    @Override
    public void onLeave(int index, int totalCount, float leavePercent, boolean leftToRight) {
    }

    @Override
    public void onEnter(int index, int totalCount, float enterPercent, boolean leftToRight) {
    }

    @Override
    public int getContentLeft() {
        Rect bound = new Rect();
        String longestString = "";
        if (tv.getText().toString().contains("\n")) {
            String[] brokenStrings = tv.getText().toString().split("\\n");
            for (String each : brokenStrings) {
                if (each.length() > longestString.length())
                    longestString = each;
            }
        } else {
            longestString = tv.getText().toString();
        }
        tv.getPaint().getTextBounds(longestString, 0, longestString.length(), bound);
        int contentWidth = bound.width();
        return getLeft() + getWidth() / 2 - contentWidth / 2;
    }

    @Override
    public int getContentTop() {
        Paint.FontMetrics metrics = tv.getPaint().getFontMetrics();
        float contentHeight = metrics.bottom - metrics.top;
        return (int) (getHeight() / 2 - contentHeight / 2);
    }

    @Override
    public int getContentRight() {
        Rect bound = new Rect();
        String longestString = "";
        if (tv.getText().toString().contains("\n")) {
            String[] brokenStrings = tv.getText().toString().split("\\n");
            for (String each : brokenStrings) {
                if (each.length() > longestString.length())
                    longestString = each;
            }
        } else {
            longestString = tv.getText().toString();
        }
        tv.getPaint().getTextBounds(longestString, 0, longestString.length(), bound);
        int contentWidth = bound.width();
        return getLeft() + getWidth() / 2 + contentWidth / 2;
    }

    @Override
    public int getContentBottom() {
        Paint.FontMetrics metrics = tv.getPaint().getFontMetrics();
        float contentHeight = metrics.bottom - metrics.top;
        return (int) (getHeight() / 2 + contentHeight / 2);
    }

    public int getSelectedColor() {
        return mSelectedColor;
    }

    public void setSelectedColor(int selectedColor) {
        mSelectedColor = selectedColor;
    }

    public int getNormalColor() {
        return mNormalColor;
    }

    public void setNormalColor(int normalColor) {
        mNormalColor = normalColor;
    }
}
