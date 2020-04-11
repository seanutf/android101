package com.seanutf.android.commonutil.util;

import android.content.Context;
import android.view.View;


/**
 * Created by sean on 2019/3/18.
 * 各种popupwindow
 */
public class PopUtil {
    public static void showCopyPop(Context context, View anchor, String text, int gravity, View.OnClickListener onClickListener) {
        showCopyPop(context, anchor, text, gravity, 0, 0, onClickListener);
    }

    public static void showCopyPop(Context context, View anchor, String text, int gravity, int xoff, int yoff,
                                   View.OnClickListener onClickListener) {
        //        EasyPopup popup = EasyPopup.create()
        //                .setContentView(context, R.layout.popup_copy)
        //                .setFocusAndOutsideEnable(true).apply();
        //
        //        TextView btnCopy = popup.findViewById(R.id.btn_copy);
        //        btnCopy.setOnClickListener(new NoDoubleClickListener() {
        //            @Override
        //            public void onNoDoubleClick(View v) {
        //                if (onClickListener != null) {
        //                    onClickListener.onClick(v);
        //                }
        //                CopyTextUtil.copyTextForToast(text);
        //                popup.dismiss();
        //            }
        //        });
        //        if (gravity == Gravity.NO_GRAVITY) {//跟随手指
        //            popup.showAtAnchorView(anchor, YGravity.ABOVE, XGravity.LEFT, xoff + popup.getWidth() / 2,
        //                    yoff - popup.getHeight());
        //
        //        } else if (gravity == Gravity.TOP)
        //            popup.showAtAnchorView(anchor, YGravity.ABOVE, XGravity.CENTER);
        //        else if (gravity == Gravity.CENTER) {
        //            popup.showAtAnchorView(anchor, YGravity.CENTER, XGravity.CENTER);
        //        }
    }

    public static void showReportPop(Context context, View anchor, String text, int gravity, View.OnClickListener onClickListener) {
        showReportPop(context, anchor, text, gravity, 0, 0, onClickListener);
    }

    public static void showReportPop(Context context, View anchor, String text, int gravity, int xoff, int yoff,
                                     View.OnClickListener onClickListener) {
        //        EasyPopup popup = EasyPopup.create()
        //                .setContentView(context, R.layout.popup_report)
        //                .setFocusAndOutsideEnable(true).apply();
        //
        //        TextView btnReport = popup.findViewById(R.id.btn_report);
        //        btnReport.setOnClickListener(new NoDoubleClickListener() {
        //            @Override
        //            public void onNoDoubleClick(View v) {
        //                if (onClickListener != null) {
        //                    onClickListener.onClick(v);
        //                }
        //                popup.dismiss();
        //            }
        //        });
        //        if (gravity == Gravity.NO_GRAVITY) {//跟随手指
        //            popup.showAtAnchorView(anchor, YGravity.ABOVE, XGravity.LEFT, xoff + popup.getWidth() / 2,
        //                    yoff - popup.getHeight());
        //
        //        } else if (gravity == Gravity.TOP)
        //            popup.showAtAnchorView(anchor, YGravity.ABOVE, XGravity.CENTER);
        //        else if (gravity == Gravity.CENTER) {
        //            popup.showAtAnchorView(anchor, YGravity.CENTER, XGravity.CENTER);
        //        }
    }
}
