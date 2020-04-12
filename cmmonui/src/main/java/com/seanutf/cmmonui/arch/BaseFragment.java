package com.seanutf.cmmonui.arch;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.seanutf.cmmonui.databinding.BaseFragmentBinding;

/**
 * 项目中最最根本的Fragment基类
 */
public abstract class BaseFragment<T extends BaseViewModel> extends Fragment {

    protected T viewModel;
    private boolean customLayout;
    private boolean needDefaultTitleBar;
    BaseFragmentBinding baseVb;

    @Override
    @CallSuper
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        customLayout = isCustomLayout();
        needDefaultTitleBar = isNeedDefaultTitleBar();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        if (customLayout) {
            return getCustomLayout();
        } else {
            baseVb = BaseFragmentBinding.inflate(inflater, container, false);
            baseVb.llContent.addView(getContentLayout());
            return baseVb.getRoot();
        }
    }

    @Override
    @CallSuper
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (!customLayout && !needDefaultTitleBar) {
            baseVb.rlBaseTitle.setVisibility(View.GONE);
        }

        setViews(savedInstanceState);
    }

    @Override
    @CallSuper
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = generateViewModel();
        handle();
        // TODO: Use the ViewModel
    }


    /**
     * 子类复写，由子类提供Fragment具体的业务内容
     * 且子类不需要关注标题栏的具体实现，只需要关注标题栏的显示内容即可
     * 配合{@link BaseContentFragment}使用
     */
    protected View getContentLayout() {
        return null;
    }

    /**
     * 子类复写，由子类提供Fragment全部的显示内容
     * 且子类需要关注标题栏的具体实现和显示内容
     * 配合{@link BaseCustomFragment}使用
     */
    protected View getCustomLayout() {
        return null;
    }

    /**
     * 子类复写，标志子类是否需要全部自定义
     * 配合{@link #getCustomLayout()} 使用
     * 同时一般情况下，开发者不需要重写本方法，
     * 有默认实现的{@link BaseCustomFragment}
     * 继承该类即可
     * */
    protected boolean isCustomLayout() {
        return false;
    }

    /**
     * 子类复写，标志子类是否需要默认的标题栏
     * 配合{@link #getContentLayout()} 使用
     * 同时一般情况下，开发者不需要重写本方法，
     * 有默认实现的{@link BaseContentFragment}
     * 继承该类即可
     * */
    protected boolean isNeedDefaultTitleBar() {
        return true;
    }

    /**
     * 子类复写，用于子类开始处理页面业务逻辑
     * 在{@link #onActivityCreated(Bundle)}方法中初始化
     * {@link androidx.lifecycle.ViewModel}完成后调用
     */
    protected abstract void handle();

    /**
     * 子类复写，用于子类初始化自己的{@link androidx.lifecycle.ViewModel}
     * 在{@link #onActivityCreated(Bundle)}方法中调用
     */
    protected abstract T generateViewModel();

    /**
     * 由于新版本的Fragment使用了ViewBinding技术，
     * 在工程代码中免去了调用findViewById()方法初始化View的步骤，
     * 所以在{@link #onViewCreated(View, Bundle)}方法中调用
     * {@link #setViews(Bundle)}方法用于对View进行初步的设置
     * */
    protected abstract void setViews(@Nullable Bundle savedInstanceState);

}
