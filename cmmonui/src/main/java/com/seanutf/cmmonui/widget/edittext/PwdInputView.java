package com.seanutf.cmmonui.widget.edittext;

/**
 * Created by sean on 2018/12/21.
 */

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatEditText;

import com.seanutf.cmmonui.R;

import static android.graphics.Paint.ANTI_ALIAS_FLAG;

/**
 * 自定义支付密码输入框
 */

public class PwdInputView extends AppCompatEditText {

    private final static int pwdType_rect = 0;
    private final static int pwdType_bottomLine = 1;
    private Context mContext;
    /**
     * 第一个圆开始绘制的圆心坐标
     */
    private float startX;
    private float startY;
    private float cX;
    /**
     * 实心圆的半径
     */
    private int radius = 10;
    /**
     * view的高度
     */
    private int height;
    private int width;
    /**
     * 当前输入密码位数
     */
    private int textLength = 0;
    private int bottomLineLength;
    /**
     * 最大输入位数
     */
    private int maxCount = 6;
    /**
     * 圆的颜色   默认BLACK
     */
    private int circleColor = Color.BLACK;
    /**
     * 底部线的颜色   默认GRAY
     */
    private int bottomLineColor = Color.GRAY;
    /**
     * 分割线的颜色
     */
    private int borderColor = 0xFF888888;
    /**
     * 分割线的画笔
     */
    private Paint borderPaint;
    /**
     * 分割线开始的坐标x
     */
    private int divideLineWStartX;
    /**
     * 分割线的宽度  默认2
     */
    private int divideLineWidth = 2;
    /**
     * 竖直分割线的颜色
     */
    private int divideLineColor = 0xFF979797;
    private int focusedColor = Color.BLUE;
    private RectF rectF = new RectF();
    private RectF focusedRecF = new RectF();
    private int pwdType = 0;
    /**
     * 矩形边框的圆角
     */
    private int rectAngle = 0;
    /**
     * 竖直分割线的画笔
     */
    private Paint divideLinePaint;
    /**
     * 圆的画笔
     */
    private Paint circlePaint;
    /**
     * 底部线的画笔
     */
    private Paint bottomLinePaint;

    /**
     * 需要对比的密码  一般为上次输入的
     */
    private String mComparePassword = null;


    /**
     * 当前输入的位置索引
     */
    private int position = 0;

    private PasswordListener mListener;

    public PwdInputView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;

        getAtt(attrs);
        initPaint();

        this.setBackgroundColor(Color.TRANSPARENT);
        this.setCursorVisible(false);
        this.setFilters(new InputFilter[]{new InputFilter.LengthFilter(maxCount)});

        setFocusable(true);
        setFocusableInTouchMode(true);

    }

    private void getAtt(AttributeSet attrs) {
        TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.PwdInputView);
        maxCount = typedArray.getInt(R.styleable.PwdInputView_maxCount, maxCount);
        circleColor = typedArray.getColor(R.styleable.PwdInputView_circleColor, circleColor);
        bottomLineColor = typedArray.getColor(R.styleable.PwdInputView_bottomLineColor, bottomLineColor);
        radius = typedArray.getDimensionPixelOffset(R.styleable.PwdInputView_radius, radius);

        divideLineWidth = typedArray.getDimensionPixelSize(R.styleable.PwdInputView_divideLineWidth, divideLineWidth);
        divideLineColor = typedArray.getColor(R.styleable.PwdInputView_divideLineColor, divideLineColor);
        pwdType = typedArray.getInt(R.styleable.PwdInputView_pwdType, pwdType);
        rectAngle = typedArray.getDimensionPixelOffset(R.styleable.PwdInputView_rectAngle, rectAngle);
        focusedColor = typedArray.getColor(R.styleable.PwdInputView_focusedColor, focusedColor);

        typedArray.recycle();
    }

    /**
     * 初始化画笔
     */
    private void initPaint() {

        circlePaint = getPaint(5, Paint.Style.FILL, circleColor);

        bottomLinePaint = getPaint(2, Paint.Style.FILL, bottomLineColor);

        borderPaint = getPaint(3, Paint.Style.STROKE, borderColor);

        divideLinePaint = getPaint(divideLineWidth, Paint.Style.FILL, borderColor);

    }

    /**
     * 设置画笔
     *
     * @param strokeWidth 画笔宽度
     * @param style       画笔风格
     * @param color       画笔颜色
     * @return
     */
    private Paint getPaint(int strokeWidth, Paint.Style style, int color) {
        Paint paint = new Paint(ANTI_ALIAS_FLAG);
        paint.setStrokeWidth(strokeWidth);
        paint.setStyle(style);
        paint.setColor(color);
        paint.setAntiAlias(true);

        return paint;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //Log.d("width" + w);
        if (w == 0)
            return;
        height = h;
        width = w;

        divideLineWStartX = w / maxCount;

        startX = w / maxCount / 2;
        startY = h / 2;

        bottomLineLength = w / (maxCount + 2);

        rectF.set(0, 0, width, height);

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        //不删除的画会默认绘制输入的文字
        //       super.onDraw(canvas);

        switch (pwdType) {
            case pwdType_rect:
                drawWeChatBorder(canvas);
                //                drawItemFocused(canvas, position);
                break;
            case pwdType_bottomLine:
                drawBottomBorder(canvas);
                break;
        }

        drawPwdCircle(canvas);
    }

    /**
     * 画微信支付密码的样式
     *
     * @param canvas
     */
    private void drawWeChatBorder(Canvas canvas) {

        canvas.drawRoundRect(rectF, rectAngle, rectAngle, borderPaint);

        for (int i = 0; i < maxCount - 1; i++) {
            canvas.drawLine((i + 1) * divideLineWStartX,
                    0,
                    (i + 1) * divideLineWStartX,
                    height,
                    divideLinePaint);
        }

    }

    private void drawItemFocused(Canvas canvas, int position) {
        if (position > maxCount - 1) {
            return;
        }
        focusedRecF.set(position * divideLineWStartX, 0, (position + 1) * divideLineWStartX,
                height);
        canvas.drawRoundRect(focusedRecF, rectAngle, rectAngle, getPaint(3, Paint.Style.STROKE, focusedColor));
    }

    /**
     * 画底部显示的分割线
     *
     * @param canvas
     */
    private void drawBottomBorder(Canvas canvas) {

        for (int i = 0; i < maxCount; i++) {
            cX = startX + i * 2 * startX;
            canvas.drawLine(cX - bottomLineLength / 2,
                    height,
                    cX + bottomLineLength / 2,
                    height, bottomLinePaint);
        }
    }

    /**
     * 画密码实心圆
     *
     * @param canvas
     */
    private void drawPwdCircle(Canvas canvas) {
        for (int i = 0; i < textLength; i++) {
            canvas.drawCircle(startX + i * 2 * startX,
                    startY,
                    radius,
                    circlePaint);
        }
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
        this.position = start + lengthAfter;
        textLength = text.toString().length();

        if (textLength == maxCount) {
            if (mListener != null) {
                if (TextUtils.isEmpty(mComparePassword)) {
                    mListener.inputFinished(getPassword());
                } else {
                    if (TextUtils.equals(mComparePassword, getPassword())) {
                        mListener.onEqual(getPassword());
                    } else {
                        mListener.onDifference(mComparePassword, getPassword());
                    }
                }
            }
        }

        invalidate();

    }

    @Override
    protected void onSelectionChanged(int selStart, int selEnd) {
        super.onSelectionChanged(selStart, selEnd);

        //保证光标始终在最后
        if (selStart == selEnd) {
            setSelection(getText().length());
        }
    }

    /**
     * 获取输入的密码
     *
     * @return
     */
    public String getPassword() {
        return getText().toString().trim();
    }

    public void setComparePassword(String comparePassword, PasswordListener listener) {
        mComparePassword = comparePassword;
        mListener = listener;
    }

    public void setComparePasswordListener(PasswordListener listener) {
        mListener = listener;
    }

    public void setComparePassword(String psd) {
        mComparePassword = psd;
    }

    /**
     * 清空密码
     */
    public void cleanPsd() {
        setText("");
    }

    /**
     * 密码比较监听
     */
    public interface PasswordListener {
        void onDifference(String oldPsd, String newPwd);

        void onEqual(String pwd);

        void inputFinished(String inputPwd);
    }
}
