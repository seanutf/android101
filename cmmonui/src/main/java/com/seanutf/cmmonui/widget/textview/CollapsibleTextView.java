package com.seanutf.cmmonui.widget.textview;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;

import androidx.annotation.Nullable;

import com.seanutf.android.commonutil.util.StringUtil;
import com.seanutf.cmmonui.R;

/**
 * Created by sean on 2017/6/29.
 */

public class CollapsibleTextView extends androidx.appcompat.widget.AppCompatTextView {
    private Context mContext;
    private int mCollapsedLines = 1;

    private String mText;

    private OnClickListener mCustomClickListener;

    private boolean mExpanded = false;

    private onClickAllTextListener mListener;

    private String
            mCollapsedText = " Show All",
            mExpandedText = " Hide";
    private String mSeeAllText;
    private OnClickListener mClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {

            if (mCustomClickListener != null) {
                mCustomClickListener.onClick(v);
            }
        }
    };
    private ClickableSpan mClickSpanListener
            = new ClickableSpan() {
        @Override
        public void onClick(View widget) {
            if (mListener != null) {
                mListener.onClickAll();
            }
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            super.updateDrawState(ds);
            ds.setUnderlineText(false);
        }
    };

    public CollapsibleTextView(Context context) {
        this(context, null);
        init(context);
    }

    public CollapsibleTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
        init(context);
    }

    public CollapsibleTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray attributes = context.getTheme()
                .obtainStyledAttributes(attrs, R.styleable.CollapsibleTextView, defStyleAttr, 0);

        mCollapsedLines = attributes.getInt(R.styleable.CollapsibleTextView_collapsedLines, 1);

        this.mText = getText() == null ? null : getText().toString();
        setMovementMethod(LinkMovementMethod.getInstance());
        super.setOnClickListener(mClickListener);
        init(context);
    }

    public void init(Context context) {
        mContext = context;
        mSeeAllText = mContext.getResources().getString(R.string.app_text_full);
    }

    public void setSeeAllText(String seeAllText) {
        mSeeAllText = seeAllText;
    }

    public void setFullString(final String str) {
        mText = str;
        if (mExpanded) {
            setText(mText);
        } else {
            getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    if (getWidth() > 0) {
                        getViewTreeObserver().removeOnPreDrawListener(this);
                        setText(str);
                        if (!mExpanded) {
                            String note = StringUtil.getEllipsized(mContext,
                                    CollapsibleTextView.this,
                                    mText,
                                    mCollapsedLines,
                                    mSeeAllText);
                            setText(note);
                        }
                    }
                    return true;
                }
            });
        }
    }

    public void setFullStringByMin(final String str, final int lineMin) {
        mText = str;
        if (mExpanded) {
            setText(mText);
        } else {
            getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                @Override
                public boolean onPreDraw() {
                    if (getWidth() > 0) {
                        getViewTreeObserver().removeOnPreDrawListener(this);
                        setText(str);
                        if (!mExpanded) {
                            String note = StringUtil.getEllipsizedByMin(
                                    CollapsibleTextView.this,
                                    mText,
                                    mCollapsedLines,
                                    lineMin,
                                    mSeeAllText);
                            setText(note);
                        }
                    }
                    return true;
                }
            });
        }
    }

    @Override
    public void setOnClickListener(OnClickListener l) {
        mCustomClickListener = l;
    }

    public void setListener(onClickAllTextListener listener) {
        mListener = listener;
    }

    public boolean isExpanded() {
        return mExpanded;
    }

    public void setExpanded(boolean expanded) {
        if (mExpanded != expanded) {
            mExpanded = expanded;
        }
    }

    public int getCollapsedLines() {
        return mCollapsedLines;
    }

    public void setCollapsedLines(int mCollapsedLines) {
        this.mCollapsedLines = mCollapsedLines;
        setText(mText);
    }

    public String getCollapsedText() {
        return mCollapsedText;
    }

    public void setCollapsedText(String mCollapsedText) {
        this.mCollapsedText = mCollapsedText;
    }

    public String getExpandedText() {
        return mExpandedText;
    }

    public void setExpandedText(String mExpandedText) {
        this.mExpandedText = mExpandedText;
    }

    public interface onClickAllTextListener {
        void onClickAll();
    }
}
