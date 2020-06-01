package com.seanutf.cmmonui.widget.textview;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.Layout;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

import com.seanutf.cmmonui.R;

/**
 * 支持展开收起的TextView
 */
public class SpannableFoldTextView extends AppCompatTextView implements View.OnClickListener {
    private static final String ELLIPSIZE_END = "...";
    private static final int MAX_LINE = 4;
    private static final String EXPAND_TIP_TEXT = "收起";
    private static final String FOLD_TIP_TEXT = "展开";
    private static final int TIP_COLOR = Color.BLACK;
    /**
     * 全文显示的位置
     */
    private static final int END = 0;
    private final ImageSpan arrow_up;
    /**
     * 是否展开
     */
    public boolean isExpand;
    boolean isFromOnclick = false;
    private int mShowMaxLine;
    /**
     * 折叠文本
     */
    private String mFoldText;
    /**
     * 展开文本
     */
    private String mExpandText;
    /**
     * 原始文本
     */
    private CharSequence mOriginalText;
    /**
     * 全文显示的位置
     */
    private int mTipGravity;
    /**
     * 全文文字的颜色
     */
    private int mTipColor;
    /**
     * 全文是否可点击
     */
    private boolean mTipClickable;
    /**
     * 全文点击的span
     */
    private ExpandSpan expandSpan;

    /**
     * 是否是Span的点击
     */
    //    private boolean isExpandSpanClick=false;
    private boolean flag;
    /**
     * 展开后是否显示文字提示
     */
    private boolean isShowTipAfterExpand;
    /**
     * 父view是否设置了点击事件
     */
    private boolean isParentClick;
    private OnClickListener listener;
    private ToggleListener toggleListener;
    private ImageSpan arrow_down;
    private boolean isExpandSpanClick;

    public SpannableFoldTextView(Context context) {
        this(context, null);
    }


    public SpannableFoldTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SpannableFoldTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mShowMaxLine = MAX_LINE;
        expandSpan = new ExpandSpan();
        if (attrs != null) {
            TypedArray arr = context.obtainStyledAttributes(attrs, R.styleable.FoldTextView);
            mShowMaxLine = arr.getInt(R.styleable.FoldTextView_showMaxLine, MAX_LINE);
            mTipGravity = arr.getInt(R.styleable.FoldTextView_tipGravity, END);
            mTipColor = arr.getColor(R.styleable.FoldTextView_tipColor, TIP_COLOR);
            mTipClickable = arr.getBoolean(R.styleable.FoldTextView_tipClickable, true);
            mFoldText = arr.getString(R.styleable.FoldTextView_foldText);
            mExpandText = arr.getString(R.styleable.FoldTextView_expandText);
            isShowTipAfterExpand = arr.getBoolean(R.styleable.FoldTextView_showTipAfterExpand, true);
            isParentClick = arr.getBoolean(R.styleable.FoldTextView_isSetParentClick, false);
            arr.recycle();
        }
        if (TextUtils.isEmpty(mExpandText)) {
            mExpandText = EXPAND_TIP_TEXT;
        }
        if (TextUtils.isEmpty(mFoldText)) {
            mFoldText = FOLD_TIP_TEXT;
        }
        Drawable drawable = getResources().getDrawable(R.drawable.ic_arrow_down);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        arrow_down = new ImageSpan(drawable, ImageSpan.ALIGN_BASELINE);
        Drawable drawable1 = getResources().getDrawable(R.drawable.ic_arrow_up);
        drawable1.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        arrow_up = new ImageSpan(drawable1, ImageSpan.ALIGN_BASELINE);

        setOnClickListener(null);
    }

    @Override
    public void setTextColor(int color) {
        super.setTextColor(color);
    }

    /**
     * 使用这个方法
     *
     * @param text
     * @param isExpand
     */
    public void setText(CharSequence text, boolean isExpand) {
        this.isExpand = isExpand;
        setText(text);
    }

    /**
     * 不要直接调用这个方法
     *
     * @param text
     * @param type
     */
    @Override
    public void setText(final CharSequence text, final BufferType type) {
        if (TextUtils.isEmpty(text) || mShowMaxLine == 0) {
            super.setText(text, type);
        } else if (isExpand) {
            //文字展开
            mOriginalText = text;

            if (getLayout() != null && getLayout().getLineCount() < mShowMaxLine) {
                return;
            }
            SpannableStringBuilder spannable = new SpannableStringBuilder(mOriginalText);
            addTip(spannable, type);
        } else {
            if (!flag) {
                getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                    @Override
                    public boolean onPreDraw() {
                        getViewTreeObserver().removeOnPreDrawListener(this);
                        flag = true;
                        formatText(text, type);
                        return true;
                    }
                });
            } else {
                formatText(text, type);
            }
        }
    }

    /**
     * 增加提示文字
     *
     * @param span
     * @param type
     */
    private void addTip(SpannableStringBuilder span, BufferType type) {
        if (!(isExpand && !isShowTipAfterExpand)) {
            //折叠或者展开并且展开后显示提示
            int length;
            if (isExpand) {
                span.append("\n");
                TextPaint paint = getPaint();
                StringBuilder spaces = new StringBuilder();
                float oneWidth = paint.measureText("  ");
                float expandWidth = paint.measureText(mExpandText);
                float maxWidth = getWidth() - expandWidth * 1.5f;
                int count = (int) (maxWidth / oneWidth);
                for (int i = 0; i < count; i++) {
                    spaces.append("  ");
                }
                span.append(spaces);

                span.append(mExpandText);
                length = mExpandText.length();
                span.setSpan(arrow_up, span.length() - length - 1, span.length() - length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            } else {

                if (mTipGravity == END) {
                    span.append("    ");
                } else {
                    span.append("\n");
                }
                span.append(mFoldText);
                length = mFoldText.length();
                span.setSpan(arrow_down, span.length() - length - 1, span.length() - length, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            }
            if (mTipClickable) {
                span.setSpan(expandSpan, span.length() - length, span.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                if (isParentClick) {
                    setMovementMethod(MyLinkMovementMethod.getInstance());
                    setClickable(false);
                    setFocusable(false);
                    setLongClickable(false);
                } else {
                    setMovementMethod(LinkMovementMethod.getInstance());
                }
            }
            //            span.setSpan(new ForegroundColorSpan(mTipColor), span.length() - length, span.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        }
        super.setText(span, type);
    }

    private void formatText(CharSequence text, final BufferType type) {
        mOriginalText = text;
        Layout layout = getLayout();
        if (layout == null || !layout.getText().equals(mOriginalText)) {
            super.setText(mOriginalText, type);
            layout = getLayout();
        }
        if (layout == null) {
            getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    translateText(getLayout(), type);
                }
            });
        } else {
            translateText(layout, type);
        }
    }

    private void translateText(Layout layout, BufferType type) {
        if (layout.getLineCount() > mShowMaxLine) {
            SpannableStringBuilder span = new SpannableStringBuilder();
            int start = layout.getLineStart(mShowMaxLine - 1);
            int end = layout.getLineVisibleEnd(mShowMaxLine - 1);
            TextPaint paint = getPaint();
            StringBuilder builder = new StringBuilder(ELLIPSIZE_END);
            if (mTipGravity == END) {
                builder.append("          ").append(mFoldText);
            }
            //paint.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            end -= paint.breakText(mOriginalText, start, end, false, paint.measureText(builder.toString()), null);
            CharSequence subSequence = mOriginalText.subSequence(0, end);
            span.append(subSequence);
            span.append(ELLIPSIZE_END);
            addTip(span, type);
        }
    }

    public void setShowMaxLine(int mShowMaxLine) {
        this.mShowMaxLine = mShowMaxLine;
    }

/*
    public void setFoldText(String mFoldText) {
        this.mFoldText = mFoldText;
    }

    public void setExpandText(String mExpandText) {
        this.mExpandText = mExpandText;
    }
*/

    public void setTipGravity(int mTipGravity) {
        this.mTipGravity = mTipGravity;
    }

    public void setTipColor(int mTipColor) {
        this.mTipColor = mTipColor;
    }

    public void setTipClickable(boolean mTipClickable) {
        this.mTipClickable = mTipClickable;
    }


    public void setShowTipAfterExpand(boolean showTipAfterExpand) {
        isShowTipAfterExpand = showTipAfterExpand;
    }

/*    public void setExpandState(boolean isExpand) {
        if (this.isExpand == isExpand) {
            return;
        } else {
            toggle();
        }
    }*/

    public void setParentClick(boolean parentClick) {
        isParentClick = parentClick;
    }

 /*   private void toggle() {
        isExpand = !isExpand;
        //        isExpandSpanClick = true;
        setText(mOriginalText);
        if (toggleListener != null) {
            toggleListener.onToggle(isExpand);
        }
    }

    @Override
    public void onClick(View v) {
        listener.onClick(v);
    }

    public void onSpanClick(View v) {
        expandSpan.onClick(v);
    }

    private class ExpandSpan extends ClickableSpan {

        @Override
        public void onClick(View widget) {

        }

        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setColor(mTipColor);
            ds.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            ds.setUnderlineText(false);
        }
    }*/

    /**
     * 重写，解决span跟view点击同时触发问题
     *
     * @param l
     */
    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        listener = l;
        super.setOnClickListener(this);
    }

    public void setToggleListener(ToggleListener toggleListener) {
        this.toggleListener = toggleListener;
    }

    @Override
    public void onClick(View v) {

        if (listener == null) {//没有设置过其他的点击事件
            if (isExpandSpanClick) {
                isExpandSpanClick = false;
            } else {
                //                expandSpan.onClick(v);
                //下面的代码跟expandSpan的onclick几乎一样
                isExpand = !isExpand;
                setText(mOriginalText, isExpand);
                if (toggleListener != null) {
                    toggleListener.onToggle(isExpand);
                }
            }
        } else {
            if (isExpandSpanClick) {
                isExpandSpanClick = false;
            } else {
                listener.onClick(v);
            }
        }
    }

    public void onSpanClick(View v) {
        expandSpan.onClick(v);
    }

    public interface ToggleListener {
        void onToggle(boolean isExpand);
    }

    private class ExpandSpan extends ClickableSpan {

        @Override
        public void onClick(View widget) {
            isExpand = !isExpand;

            isExpandSpanClick = true;
            setText(mOriginalText, isExpand);
            if (toggleListener != null) {
                toggleListener.onToggle(isExpand);
            }
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setColor(mTipColor);
            ds.setUnderlineText(false);
            ds.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        }
    }


}
