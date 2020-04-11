package com.seanutf.cmmonui.util;

import android.content.ClipData;
import android.content.ClipboardManager;

import com.seanutf.android.commonutil.CommonUtilApp;

import static android.content.Context.CLIPBOARD_SERVICE;

public class CopyTextUtil {

    /**
     * 静默复制文案，无提示给用户
     */
    public static void copyTextForSilent(String copyText) {
        ClipboardManager clipboard = (ClipboardManager) CommonUtilApp.instance.getSystemService(CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText("text", copyText);
        if (clipboard != null) {
            clipboard.setPrimaryClip(clipData);
        }
    }

    /**
     * 复制文案，Toast级别提示给用户
     */
    public static void copyTextForToast(String copyText) {
        copyTextForSilent(copyText);
        //ToastUtils.showToast(R.string.app_sure);
    }


    /**
     * 获取剪切板中的文案
     */
    public static String getClipboardText() {
        ClipboardManager clipboard = (ClipboardManager) CommonUtilApp.instance.getSystemService(CLIPBOARD_SERVICE);
        //剪切板中存在内容且是文本信息
        if (clipboard != null && clipboard.hasPrimaryClip()) {
            ClipData cdText = clipboard.getPrimaryClip();

            if (cdText == null || cdText.getItemCount() == 0)
                return "";

            ClipData.Item item = cdText.getItemAt(0);
            if (item != null && item.getText() != null) {
                //剪切板内容
                return item.getText().toString();
            }
        }

        return "";
    }

    public static void deleteClipboardText() {
        ClipboardManager clipboard = (ClipboardManager) CommonUtilApp.instance.getSystemService(CLIPBOARD_SERVICE);
        if (clipboard != null) {
            clipboard.setPrimaryClip(ClipData.newPlainText(null, ""));
        }
    }
}
