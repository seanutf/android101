package com.seanutf.cmmonui.util;

import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;

import androidx.core.content.res.ResourcesCompat;

import com.seanutf.android.commonutil.CommonUtilApp;
import com.seanutf.cmmonui.R;
import com.seanutf.cmmonui.textspan.CustomTypefaceSpan;


public class FontUtil {

    public static SpannableString getDefaultTypefaceStr(CharSequence chars) {
        return getDefaultTypefaceStr(chars, false, false);
    }

    public static SpannableString getDefaultTypefaceStr(CharSequence chars, boolean isBold, boolean isItalic) {
        if (TextUtils.isEmpty(chars)) {
            return new SpannableString("");
        }
        SpannableString s = new SpannableString(chars);
        Typeface typeface = getDefaultTypeface(isBold, isItalic);
        s.setSpan(new CustomTypefaceSpan("", typeface), 0, s.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        return s;
    }


    public static Typeface getDefaultTypeface(boolean isBold, boolean isItalic) {
        int type = Typeface.NORMAL;
        if (isBold && isItalic) {
            type = Typeface.BOLD_ITALIC;
        } else if (isBold) {
            type = Typeface.BOLD;
        } else if (isItalic) {
            type = Typeface.ITALIC;
        }
        return Typeface.create(ResourcesCompat.getFont(CommonUtilApp.instance, R.font.noto_sans), type);
    }


}
