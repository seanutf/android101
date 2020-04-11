package com.seanutf.android.commonutil.util;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Paint;
import android.graphics.Rect;
import android.net.Uri;
import android.text.Layout;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.TextAppearanceSpan;
import android.widget.TextView;

import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;


/**
 * Created by sean on 16/3/24.
 */
public class StringUtil {

    /**
     * 相比直接使用{@link TextUtils}，增加对字符串前后空格的处理
     */
    public static boolean isNotEmptyString(String str) {
        if (str == null || str.length() == 0) {
            return false;
        }
        str = str.trim();
        return str.length() > 0;
    }

    public static boolean isNotMsgEmptyString(String str) {
        if (isNotEmptyString(str) && !str.equals("0")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 该字符串不为空的情况下且内容也不可以为"null"
     */
    public static boolean isNotEmptyStringAbsolute(String str) {
        if (str == null || str.length() == 0 || str.equals("")) {
            return false;
        }
        str = str.trim();
        return !str.equals("null") && str.length() > 0;
    }

    public static boolean isEquals(String str1, String str2) {
        if (str1 == null && str2 == null) {
            return true;
        }
        if ((str1 == null && str2 != null) ||
                (str2 == null && str1 != null)) {
            return false;
        }

        if ((str1 == null && str2 == "") ||
                (str2 == null && str1 == "")) {
            return false;
        }

        return str1.equals(str2);
    }

    /**
     * 适用只改变字符串中一段文字的颜色
     *
     * @param text  全部字符串
     * @param size  要改变部分的字号大小, 为0 即采用原始的正常的 size大小
     * @param start 要改变部分的开始位置
     * @param end   要改变部分的结束位置
     */
    public static SpannableStringBuilder partTextChangeSize(String text, int color, int size, int start, int end) {
        return partTextChangeColorOrSize(text, color, size, 0, start, end);
    }


    /**
     * 适用只改变字符串中一段文字的颜色
     *
     * @param text  全部字符串
     * @param color 16进制的字色，如：0xffff0000
     * @param start 要改变部分的开始位置
     * @param end   要改变部分的结束位置
     */
    public static SpannableStringBuilder partTextChangeColor(String text, int color, int start, int end) {
        return partTextChangeColorOrSize(text, color, 0, 0, start, end);
    }

    /**
     * 适用只改变字符串中一段文字的颜色和字号
     *
     * @param text  全部字符串
     * @param color 16进制的字色，如：0xffff0000
     * @param size  要改变部分的字号大小, 为0 即采用原始的正常的 size大小
     * @param start 要改变部分的开始位置
     * @param end   要改变部分的结束位置
     * @param style 要改变部分的字体风格， 为0 即是正常的，还有Typeface.BOLD(粗体) Typeface.ITALIC(斜体)等
     */
    public static SpannableStringBuilder partTextChangeColorOrSize(String text, int color, int size, int style, int start, int end) {
        ColorStateList changeColors = ColorStateList.valueOf(color);
        SpannableStringBuilder spanBuilder = new SpannableStringBuilder(text);
        spanBuilder.setSpan(new TextAppearanceSpan(null, style, size, changeColors, null), start, end, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);

        return spanBuilder;
    }

    /**
     * 判断String类型字符串是否为数字
     *
     * @param str
     * @return
     */
    public static boolean thisStringIsInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

    public static boolean isNumeric(String str) {
        try {
            new BigDecimal(str);
        } catch (Exception e) {
            return false;//异常 说明包含非数字。
        }
        return true;
    }


    /**
     * 判断String类型字符串是否为16进制数字
     *
     * @param str
     * @return
     */
    public static boolean isHexNumberRex(String str) {
        String validate = "(?i)[0-9a-f]+";
        return str.matches(validate);
    }

    /**
     * 判断字符串是否包含数字
     */
    public static boolean isHasDigit(String content) {
        Pattern p = Pattern.compile(".*\\d+.*");
        Matcher m = p.matcher(content);
        if (m.matches()) {
            return true;
        }
        return false;
    }

    /**
     * 把一段字符串中的除过数字和小数点的其他都去掉
     *
     * @param str
     * @return
     */
    public static String stringFilterForMoney(String str) {
        // 清除掉所有特殊字符
        str = str.replaceAll("[^\\d.]+", "");
        return str;
    }

    /**
     * 过滤字符串中的特殊字符
     *
     * @param str 可能含有特殊字符的字符串
     * @return
     */
    public static String stringFilter(String str) throws PatternSyntaxException {
        // 只允许字母和数字 // String regEx ="[^a-zA-Z0-9]";
        // 清除掉所有特殊字符
        String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？\\\\]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }


    public static String getEllipsized(final Context context, final TextView textView, final String originalText, final int ellipsizeLine, String more) {
        if (textView == null || TextUtils.isEmpty(originalText) || TextUtils.isEmpty(textView.getText().toString())) {
            return "";
        }

        int moreWidth = 0;
        if (more != null && more.length() > 0) {
            TextPaint paint = new TextPaint();
            paint.setTextSize(textView.getTextSize());
            moreWidth = (int) paint.measureText(more);
        }

        if (textView.getLineCount() >= ellipsizeLine) {
            String result = "";

            Layout layout = textView.getLayout();
            String text = textView.getText().toString();
            int start = 0;
            int end;
            for (int i = 0; i < textView.getLineCount(); i++) {
                end = layout.getLineEnd(i);

                String line = text.substring(start, end); //指定行的内容
                if (i < ellipsizeLine - 1) {
                    result += line;
                } else if (i == ellipsizeLine - 1) {
                    line = TextUtils.ellipsize(line, textView.getPaint(), (float) textView.getWidth() - moreWidth, TextUtils.TruncateAt.END).toString();
                    result += line;
                    break;
                }

                start = end;
            }

            return result;
        }
        return originalText;
    }

    public static String getEllipsizedByMin(final TextView textView, final String originalText, final int ellipsizeLine, int ellipsizeLineMin, String more) {
        if (textView == null || TextUtils.isEmpty(originalText) || TextUtils.isEmpty(textView.getText().toString())) {
            return "";
        }

        int moreWidth = 0;
        if (more != null && more.length() > 0) {
            TextPaint paint = new TextPaint();
            paint.setTextSize(textView.getTextSize());
            moreWidth = (int) paint.measureText(more);
        }

        if (textView.getLineCount() > ellipsizeLine) {
            String result = "";
            Layout layout = textView.getLayout();
            String text = textView.getText().toString();
            int start = 0;
            int end;
            for (int i = 0; i < textView.getLineCount(); i++) {
                end = layout.getLineEnd(i);

                String line = text.substring(start, end); //指定行的内容
                if (i < ellipsizeLineMin - 1) {
                    result += line;
                } else if (i == ellipsizeLineMin - 1) {
                    line = TextUtils.ellipsize(line, textView.getPaint(), (float) textView.getWidth() - moreWidth, TextUtils.TruncateAt.END).toString();
                    result += line;
                    break;
                }

                start = end;
            }

            return result;
        }

        return originalText;
    }

    public static String trim(String string, char trims) {
        if (TextUtils.isEmpty(string)) {
            return string;
        }

        int start = 0, last = string.length() - 1;
        int end = last;
        while ((start <= end) && (string.charAt(start) == trims)) {
            start++;
        }
        while ((end >= start) && (string.charAt(end) == trims)) {
            end--;
        }

        if (start == 0 && end == last) {
            return string;
        }

        if (end + 1 <= start) {
            return string;
        }

        return string.substring(start, end + 1);
    }

    /**
     * 计算字符长度
     *
     * @param text  输入字符串
     * @param paint textview的paint
     * @return 字符串绘制像素Rect
     */
    public static Rect pxWidthFromText(String text, Paint paint) {
        Rect rect = new Rect(0, 0, 0, 0);
        if (paint != null && text != null) {
            paint.getTextBounds(text, 0, text.length(), rect);
        }
        return rect;
    }


    /*
     * MD5
     * */
    public static String getMD5(String info) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(info.getBytes(StandardCharsets.UTF_8));
            byte[] encryption = md5.digest();

            StringBuffer strBuf = new StringBuffer();
            for (int i = 0; i < encryption.length; i++) {
                if (Integer.toHexString(0xff & encryption[i]).length() == 1) {
                    strBuf.append("0").append(Integer.toHexString(0xff & encryption[i]));
                } else {
                    strBuf.append(Integer.toHexString(0xff & encryption[i]));
                }
            }

            return strBuf.toString();
        } catch (NoSuchAlgorithmException e) {
            return "";
        } catch (Exception e) {
            return "";
        }
    }


    /*
     *   Url encoding
     * */
    public static String urlEncode(String url) {
        //        return URLEncoder.encode(url);
        return Uri.encode(url);
    }

    /*
     *   Url decoding
     * */
    public static String urlDecode(String url) {
        //        return URLDecoder.decode(url);
        return Uri.decode(url);
    }

    public static Map<String, String> urlParamToMap(String uri) {
        Uri tempUri = Uri.parse(uri);
        Map<String, String> result = new HashMap<>();
        if (tempUri != null) {
            Set<String> names = tempUri.getQueryParameterNames();
            for (Iterator<String> iter = names.iterator(); iter.hasNext(); ) {
                String name = iter.next();
                //这里value不需要手动decode,getQueryParameter自动decode
                String value = tempUri.getQueryParameter(name);
                result.put(name, value);
            }
        }

        return result;
    }

    /**
     * iOS 相对应的java方法
     */
    public static String stringByAddingPercentEscapesUsingEncoding(String input) {
        byte[] bytes = input.getBytes(Charset.forName("UTF-8"));
        StringBuilder sb = new StringBuilder(bytes.length);
        for (int i = 0; i < bytes.length; ++i) {
            int cp = bytes[i] < 0 ? bytes[i] + 256 : bytes[i];
            if (cp <= 0x20 || cp >= 0x7F || (
                    cp == 0x22 || cp == 0x25 || cp == 0x3C ||
                            cp == 0x3E || cp == 0x20 || cp == 0x5B ||
                            cp == 0x5C || cp == 0x5D || cp == 0x5E ||
                            cp == 0x60 || cp == 0x7b || cp == 0x7c ||
                            cp == 0x7d
            )) {
                sb.append(String.format("%%%02X", cp));
            } else {
                sb.append((char) cp);
            }
        }
        return sb.toString();
    }

    public static String floatPriceToStringWithoutTail(float price) {
        String tempString = "";
        int temp = Math.round(price);
        if (Math.abs(price - temp) < 0.00001) {
            tempString = String.format("%d", temp);
        } else {
            tempString = String.format("%.2f", price);
        }
        return tempString;
    }


    /**
     * 带表情的字符串长度计算
     */
    public static Range utf16RangeWithRange(String inString, Range range) {
        if (inString == null)
            return new Range(0, 0);

        int length = inString.length();
        char utf16 = 0;

        //判断不合法输入
        if (range.location > length - 1
                || range.length > length) {
            return new Range(0, 0);
        }

        Range adjustedRange = range;
        int count = 0;

        //调整location
        if (range.location == 0) {
            //从0开始不需要转换
        } else {
            //逐个数unicode，直到range.location
            for (int i = 0; i < range.location; i++) {
                if (i + count + 1 < inString.length()) {
                    String subStr = inString.substring(i + count, i + count + 1);
                    utf16 = subStr.charAt(0);

                    if (utf16 > 0xd800 && utf16 < 0xdbff) {
                        count++;
                    }
                }
            }
        }
        adjustedRange.location += count;

        count = 0;
        //调整length
        for (int i = 0; i < range.length; i++) {
            int start = adjustedRange.location + i + count;
            int end = adjustedRange.location + i + count + 1;
            if (end < inString.length()) {
                String subStr = inString.substring(start, end);
                utf16 = subStr.charAt(0);

                if (utf16 > 0xd800 && utf16 < 0xdbff) {
                    count++;
                }
            }
        }
        adjustedRange.length += count;

        if (adjustedRange.location + adjustedRange.length > length) {
            adjustedRange.length = length - adjustedRange.location - 1;
        }
        return adjustedRange;
    }

    public static class Range {
        public int location;
        public int length;

        public Range(int loct, int len) {
            location = loct;
            length = len;
        }
    }


    /**
     * 手机号中间4位隐藏
     *
     * @param phonestr 手机号
     * @return
     */
    public static String phoneType(String phonestr) {
        if (isNotEmptyString(phonestr)) {
            if (phonestr.length() < 8) {
                return "";
            }
            return String.format("%s****%s", phonestr.substring(0, 3), phonestr.substring(7));
        } else {
            return "";
        }
    }


    /**
     * 整形数字转成带万字的字符串取小数点后一位
     *
     * @param num long值的
     * @return
     */
    public static String longToWanFloat(long num) {
        if (num < 10000) {
            return String.valueOf(num);
        } else {
            String s = "";
            long p1 = num / 10000;
            long p2 = (num % 10000) / 1000;
            if (p2 > 0) {
                s = String.valueOf(p1) + "." + String.valueOf(p2) + "w";
            } else {
                s = String.valueOf(p1) + "w";
            }
            return s;
        }
    }
}
