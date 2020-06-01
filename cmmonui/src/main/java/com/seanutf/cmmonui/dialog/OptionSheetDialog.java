package com.seanutf.cmmonui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.seanutf.cmmonui.R;
import com.seanutf.cmmonui.util.DensityUtil;

import java.util.List;

/**
 * Created by sean on 2017/11/2.
 * 通用的选项弹窗
 */

public class OptionSheetDialog extends Dialog {
    private Context mContext;
    private OnClickSheetListener mListener;
    private LinearLayout mMainLayout;

    public OptionSheetDialog(@NonNull Context context, List<String> list) {
        super(context);
        mContext = context;
        setShowList(list);
    }

    public OptionSheetDialog setShowList(List<String> list) {
        View mView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_select_sheet_menu, null);
        int screenWidth = (int) DensityUtil.getScreenWidth();
        mMainLayout = (LinearLayout) mView.findViewById(R.id.option_sheet_menu_dialog);
        for (int i = 0; i < list.size(); i++) {
            String text = list.get(i);
            if (!TextUtils.isEmpty(text)) {
                if (i != 0) {
                    LinearLayout line = new LinearLayout(mContext);
                    line.setBackgroundColor(mContext.getResources().getColor(R.color.app_color_font_eeeeee));
                    int margin = mContext.getResources().getDimensionPixelSize(R.dimen.distance_40);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(screenWidth - margin * 2, mContext.getResources().getDimensionPixelSize(R.dimen.distance_1));
                    line.setLayoutParams(params);
                    mMainLayout.addView(line);
                }

                TextView textView = new TextView(mContext);
                textView.setGravity(Gravity.CENTER);
                textView.setTextColor(mContext.getResources().getColor(R.color.app_color_font_333333));
                textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mContext.getResources().getDimensionPixelSize(R.dimen.font_15));
                textView.setText(text);

                int margin = mContext.getResources().getDimensionPixelSize(R.dimen.distance_30);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(screenWidth - margin, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.topMargin = mContext.getResources().getDimensionPixelSize(R.dimen.distance_15);
                layoutParams.bottomMargin = mContext.getResources().getDimensionPixelSize(R.dimen.distance_15);
                textView.setLayoutParams(layoutParams);
                mMainLayout.addView(textView);

                final int finalI = i;
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mListener != null) {
                            mListener.onClickSheet(finalI);
                        }
                    }
                });
            }
        }

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new BitmapDrawable());
        super.setContentView(mView);
        return this;
    }

    public OptionSheetDialog setColorList(int color, int index) {
        int tempIndex = 0;
        for (int i = 0; i < mMainLayout.getChildCount(); i++) {
            Object object = mMainLayout.getChildAt(i);
            if (object instanceof TextView) {
                if (tempIndex == index) {
                    ((TextView) object).setTextColor(color);
                }

                tempIndex++;
            }
        }
        return this;
    }

    public OptionSheetDialog setListener(OnClickSheetListener listener) {
        mListener = listener;
        return this;
    }

    public interface OnClickSheetListener {
        void onClickSheet(int index);
    }
}
