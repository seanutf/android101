package com.seanutf.cmmonui;

import android.app.Activity;
import android.os.Bundle;

import com.alibaba.android.arouter.launcher.ARouter;
import com.seanutf.android.commonutil.CommonUtilApp;
import com.seanutf.android.commonutil.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

import static com.seanutf.android.commonutil.AppInfo.isDebug;

public class CommonUIApp extends CommonUtilApp {

    public static CommonUIApp instance;

    private int activityCount = 0;

    private List<Activity> activityList = new ArrayList<>();

    private Activity mCurrentActivity = null;

    public Activity getCurrentActivity() {
        return mCurrentActivity;
    }

    public void setCurrentActivity(Activity mCurrentActivity) {
        this.mCurrentActivity = mCurrentActivity;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        initUIThirdParty();
    }

    private void initUIThirdParty() {
        initARouter();
    }

    private void initARouter() {
        if (isDebug()) {           // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this); // 尽可能早，推荐在Application中初始化
    }

    /**
     * 获取栈顶的activity实例
     */
    public Activity getTopActivityInstance() {
        if (CollectionUtils.isNotEmptyList(activityList)) {
            for (int i = activityList.size() - 1; i >= 0; i--) {
                Activity activity = activityList.get(i);
                if (activity == null || activity.isFinishing() || activity.isDestroyed()) {
                    continue;
                }
                return activity;
            }
        }
        return null;
    }

    /**
     * 获取所有运行中的activity名字
     *
     * @return
     */
    public String getActivityList() {
        StringBuilder sb = new StringBuilder("activity列表\n");
        for (Activity activity : activityList) {
            sb.append(activity.getLocalClassName() + "\n");
        }
        return sb.toString();
    }

    /**
     * 判断前后台计数的
     */
    private int mActivityCount = 0;

    class ActivityLifecycleListener implements ActivityLifecycleCallbacks {

        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            if (activity.getLocalClassName().contains("XGPushActivity") ||
                    activity.getLocalClassName().contains("WXEntryActivity")) {
                return;
            }
            activityCount++;
            activityList.add(activity);
        }

        @Override
        public void onActivityStarted(Activity activity) {
            mActivityCount++;
        }

        @Override
        public void onActivityResumed(Activity activity) {
            mCurrentActivity = activity;
        }

        @Override
        public void onActivityPaused(Activity activity) {
        }

        @Override
        public void onActivityStopped(Activity activity) {
            mActivityCount--;
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            if (activity.getLocalClassName().contains("XGPushActivity") ||
                    activity.getLocalClassName().contains("WXEntryActivity")) {
                return;
            }
            activityCount--;
            activityList.remove(activity);
        }
    }
}
