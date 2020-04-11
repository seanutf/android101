package com.seanutf.cmmonui.util;

import android.graphics.Typeface;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;

import com.seanutf.android.commonutil.CommonUtilApp;
import com.seanutf.cmmonui.R;
import com.seanutf.cmmonui.textspan.CustomTypefaceSpan;

public class ViewUtil {

    /**
     * 对EditText输入内容字数限制的提示
     */
    public static void etSetTextChangeListener(EditText et, final int MAX_NAME_LENGTH) {
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == MAX_NAME_LENGTH) {
                    ToastUtils.showToast("限制" + String.valueOf(MAX_NAME_LENGTH) + "字以内！");
                } else {
                }
            }
        });
    }

    /*
     * 限制回车键，不支持换行
     * */
    public static void setOnEditorActionListener(EditText editText) {
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    return true;
                }
                return false;
            }
        });
    }

    static SpannableString getDefaultTypefaceStr(CharSequence chars) {
        if (TextUtils.isEmpty(chars)) {
            return new SpannableString("");
        }
        SpannableString s = new SpannableString(chars);
        Typeface typeface = Typeface.create(ResourcesCompat.getFont(CommonUtilApp.instance, R.font.noto_sans), Typeface.BOLD);
        s.setSpan(new CustomTypefaceSpan("", typeface), 0, s.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        return s;
    }
}
