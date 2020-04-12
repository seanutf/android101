package com.seanutf.cmmonui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.seanutf.cmmonui.R;

/**
 * 通用dialog
 * Created by sean on 2018/12/18.
 */
public class CommonDialog extends Dialog {

    private View view;
    private TextView btn_cancel;
    private TextView btn_ok;
    private TextView tv_title;
    private TextView tv_content;

    public CommonDialog(Context context) {
        super(context);
        initView();
    }

    private void initView() {
        view = LayoutInflater.from(getContext()).inflate(R.layout.common_dialog, null);
        btn_cancel = view.findViewById(R.id.btn_cancel);
        btn_ok = view.findViewById(R.id.btn_ok);
        tv_title = view.findViewById(R.id.tv_title);
        tv_content = view.findViewById(R.id.tv_content);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(view);

        setCanceledOnTouchOutside(false);
        setCancelable(false);
    }


    private CommonDialog apply(final Builder builder) {

        if (!TextUtils.isEmpty(builder.title)) {
            tv_title.setText(builder.title);
        } else {
            tv_title.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(builder.content)) {
            tv_content.setText(builder.content);
        }

        if (!TextUtils.isEmpty(builder.posText)) {
            btn_ok.setText(builder.posText);
        }
        btn_ok.setOnClickListener(v -> {
            dismiss();
            if (builder.posListener != null)
                builder.posListener.onClick(btn_ok);

        });
        if (!TextUtils.isEmpty(builder.negText)) {
            btn_cancel.setText(builder.negText);
        }
        btn_cancel.setOnClickListener(v -> {
            dismiss();
            if (builder.negListener != null)
                builder.negListener.onClick(btn_cancel);

        });
        return this;
    }

    public static final class Builder {

        public String posText;
        public View.OnClickListener posListener;
        public String negText;
        public View.OnClickListener negListener;
        public Context context;
        public CharSequence title;
        public CharSequence content;


        public Builder(Context context) {
            this.context = context;
        }

        public Builder setTitle(CharSequence title) {
            this.title = title;

            return this;
        }

        public Builder setContent(CharSequence content) {

            this.content = content;
            return this;
        }

        public Builder setPositiveButton(String text, View.OnClickListener listener) {

            posText = text;
            posListener = listener;
            return this;
        }

        public Builder setNegativeButton(String text, View.OnClickListener listener) {
            negText = text;
            negListener = listener;
            return this;
        }

        private CommonDialog create() {

            CommonDialog dialog = new CommonDialog(context);
            dialog.apply(this);
            return dialog;

        }

        public CommonDialog show() {
            CommonDialog wwDialog = create();
            wwDialog.show();
            return wwDialog;

        }

    }


}
