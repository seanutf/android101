package com.seanutf.android.base.media.load;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.TransitionOptions;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.BaseTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.seanutf.android.commonutil.util.FileUtils;
import com.seanutf.cmmonui.CommonUIApp;
import com.seanutf.cmmonui.util.DensityUtil;

import java.io.File;

/**
 * Created by Sean on 2018/9/22.
 */
public class ImageLoader {


    /**
     * 私有构造方法(防止创建对象)
     */
    private ImageLoader() {
    }

    /**
     * 初始化
     *
     * @param context 上下文
     * @return Option
     */
    public static Build with(Context context) {
        return new Build(context);
    }

    /**
     * 加载头像
     *
     * @param context 上下文
     * @param url     头像url
     * @param imgView 要显示的ImageView
     */
    public static void loadAvatar(Context context, Object url, ImageView imgView) {
        loadRoundImage(context, url, imgView);
    }

    /**
     * 加载圆形图片
     *
     * @param context 上下文
     * @param url     图片url
     * @param imgView 要显示的ImageView
     */
    public static void loadRoundImage(Context context, Object url, ImageView imgView) {
        loadRoundImageWithListener(context, url, imgView, null);
    }

    /**
     * 加载不要求View大小的图片
     *
     * @param context 上下文
     * @param url     图片url
     * @param imgView 要显示的ImageView
     */
    public static void loadImage(Context context, Object url, ImageView imgView) {
        loadImageFull(context, url, imgView, false, 0, 0, 0, 0, null, null);
    }

    public static void loadImage(Context context, Object url, int errResId, ImageView imgView) {
        loadImageFull(context, url, imgView, false, 0, 0, 0, 0, errResId, null, null);
    }

    public static void loadImage(Context context, Object url, int placeholderResId, int errResId, ImageView imgView) {
        loadImageFull(context, url, imgView, false, 0, 0, 0, placeholderResId, errResId, null, null);
    }

    /**
     * 加载要求View大小的图片
     *
     * @param context 上下文
     * @param url     图片url
     * @param imgView 要显示的ImageView
     * @param width   要显示的ImageView的宽度
     * @param height  要显示的ImageView的高度
     */
    public static void loadImageWithSize(Context context, Object url, ImageView imgView, int width, int height) {
        loadImageFull(context, url, imgView, false, width, height, 0, 0, null, null);
    }

    /**
     * 加载要求有圆角的图片
     *
     * @param context  上下文
     * @param url      图片url
     * @param imgView  要显示的ImageView
     * @param cornerDp 四个角的dp值
     */
    public static void loadImageWithCorner(Context context, Object url, ImageView imgView, int cornerDp) {
        loadImageFull(context, url, imgView, false, 0, 0, cornerDp, 0, null, null);
    }

    public static void loadImageWithCorner(Context context, Object url, int errResId, ImageView imgView, int cornerDp) {
        loadImageFull(context, url, imgView, false, 0, 0, cornerDp, 0, errResId, null, null);
    }

    /**
     * 加载要求有固定大小的圆角图片
     *
     * @param context  上下文
     * @param url      图片url
     * @param imgView  要显示的ImageView
     * @param cornerDp 四个角的dp值
     * @param width    要显示的ImageView的宽度
     * @param height   要显示的ImageView的高度
     */
    public static void loadImageWithSizeCorner(Context context, Object url, ImageView imgView, int width, int height, int cornerDp) {
        loadImageFull(context, url, imgView, false, width, height, cornerDp, 0, null, null);
    }

    /**
     * 加载自定义Transformation的图片
     *
     * @param context        上下文
     * @param url            图片url
     * @param imgView        要显示的ImageView
     * @param transformation 自定义的Transformation
     */
    public static void loadImageWithTransformation(Context context, Object url, ImageView imgView, Transformation<android.graphics.Bitmap> transformation) {
        loadImageFull(context, url, imgView, false, 0, 0, 0, 0, transformation, null);
    }

    /**
     * 加载自定义Placeholder的图片
     *
     * @param context          上下文
     * @param url              图片url
     * @param imgView          要显示的ImageView
     * @param placeholderResId 自定义的Placeholder资源id
     */
    public static void loadImageWithPlaceholder(Context context, Object url, ImageView imgView, int placeholderResId) {
        loadImageFull(context, url, imgView, false, 0, 0, 0, placeholderResId, null, null);
    }

    /**
     * 加载自定义Placeholder和Transformation的图片
     *
     * @param context          上下文
     * @param url              图片url
     * @param imgView          要显示的ImageView
     * @param placeholderResId 自定义的Placeholder资源id
     * @param transformation   自定义的Transformation
     */
    public static void loadImageWithPlaceholderTransformation(Context context, Object url, ImageView imgView, int placeholderResId, Transformation<android.graphics.Bitmap> transformation) {
        loadImageFull(context, url, imgView, false, 0, 0, 0, placeholderResId, transformation, null);
    }

    /**
     * 加载自定义监听事件的图片
     *
     * @param context          上下文
     * @param url              图片url
     * @param imgView          要显示的ImageView
     * @param downloadListener 自定义的加载监听事件
     */
    public static void loadImageWithListener(Context context, Object url, ImageView imgView, BaseTarget<Drawable> downloadListener) {
        loadImageFull(context, url, imgView, false, 0, 0, 0, 0, null, downloadListener);
    }

    public static void loadImageWithListener(Context context, Object url, ImageView imgView, int placeholderResId, BaseTarget<Drawable> downloadListener) {
        loadImageFull(context, url, imgView, false, 0, 0, 0, placeholderResId, null, downloadListener);

    }

    /**
     * 当加载的图片是静态图时，添加自定义监听事件
     * 为分享到微信的专用
     * todo 在外部的监听调用中手动回收了图片，使得
     * 图片无法被复用，这里采用临时解决方案
     * 记得优化外部的监听调用，不进行手动回收
     *
     * @param context          上下文
     * @param url              图片url
     * @param imgView          要显示的ImageView
     * @param downloadListener 自定义的加载监听事件
     */
    public static void loadBitmapImageForShareWithListener(Context context, Object url, ImageView imgView, BaseTarget<Drawable> downloadListener) {
        //这里不能缓存 解决分享到微信小程序的图片被recycled的问题
        with(context).load(url).isCache(false).setListener(downloadListener).into(imgView);
    }

    /**
     * 当加载的图片是静态图时，添加自定义监听事件
     *
     * @param context          上下文
     * @param url              图片url
     * @param imgView          要显示的ImageView
     * @param downloadListener 自定义的加载监听事件
     */
    public static void loadBitmapImageWithListener(Context context, Object url, ImageView imgView, BaseTarget<Drawable> downloadListener) {
        loadImageFull(context, url, imgView, true, 0, 0, 0, 0, null, downloadListener);
    }

    public static void loadBitmapImageWithListener(Context context, Object url, ImageView imgView, int errResId, BaseTarget<Drawable> downloadListener) {
        loadImageFull(context, url, imgView, true, 0, 0, 0, 0, errResId, null, downloadListener);
    }

    /**
     * 加载自定义监听事件的圆形图片
     *
     * @param context          上下文
     * @param url              图片url
     * @param imgView          要显示的ImageView
     * @param downloadListener 自定义的加载监听事件
     */
    public static void loadRoundImageWithListener(Context context, Object url, ImageView imgView, BaseTarget<Drawable> downloadListener) {
        with(context).load(url).isCache(true).setListener(downloadListener).isRound(true).into(imgView);
    }

    /**
     * 加载自定义监听事件的圆形图片
     *
     * @param context          上下文
     * @param url              图片url
     * @param imgView          要显示的ImageView
     * @param width            要显示的ImageView的宽度
     * @param height           要显示的ImageView的高度
     * @param placeholderResId 自定义的Placeholder资源id
     * @param transformation   自定义的Transformation
     * @param downloadListener 自定义的加载监听事件
     */
    private static void loadImageFull(Context context, Object url, ImageView imgView, boolean isBitmap, int width, int height, int cornerDp, int placeholderResId, int errResId, Transformation<android.graphics.Bitmap> transformation, BaseTarget<Drawable> downloadListener) {
        with(context).load(url).isCache(true).isBitmap(isBitmap).size(width, height).isCorner(cornerDp).placeholder(placeholderResId).error(errResId).setTransformation(transformation).setListener(downloadListener).into(imgView);
    }

    private static void loadImageFull(Context context, Object url, ImageView imgView, boolean isBitmap, int width, int height, int cornerDp, int placeholderResId, Transformation<android.graphics.Bitmap> transformation, BaseTarget<Drawable> downloadListener) {
        loadImageFull(context, url, imgView, isBitmap, width, height, cornerDp, placeholderResId, 0, transformation, downloadListener);
    }

    /**
     * 下载网络图片
     */
    public static void downloadImage(Context context, String url, DownloadImgCallback callback) {
        Glide.with(context).downloadOnly().load(url).into(new SimpleTarget<File>() {
            @Override
            public void onResourceReady(@NonNull File file, @Nullable Transition<? super File> transition) {
                boolean downloadSuccess = FileUtils.saveImgFile(file, true);
                if (callback != null) {
                    callback.onImgDownload(file, downloadSuccess);
                }
            }
        });
    }

    public static void preloadImage(String url) {
        Glide.with(CommonUIApp.instance).downloadOnly().load(url).into(new SimpleTarget<File>() {//避免activity不存活导致crash
            @Override
            public void onResourceReady(@NonNull File file, @Nullable Transition<? super File> transition) {
                //Log.d("预加载图片：" + "---" + url, "image");
            }
        });
    }

    /**
     * 通过url获取Drawable
     *
     * @param url
     * @param customTarget
     */
    public static void getDrawableByUrl(String url, @NonNull Target<Drawable> customTarget) {
        Glide.with(CommonUIApp.instance).asDrawable().load(url).into(customTarget);
    }

    public interface DownloadImgCallback {
        void onImgDownload(File file, boolean downloadSuccess);
    }

    /**
     * 加载设置项
     */
    public static class Build {

        private Context context;
        private Object model;// Url, Res, File

        private int loadingResId;// 加载占位图
        private int errorResId;// 错误占位图
        private String thumbUrl;
        private boolean isGif = false;// 是否Gif图
        private boolean isRound = false;// 是否加载圆形
        private boolean isCache = true;// 是否缓存
        private boolean isBitmap = false;// 是否是静态图
        private Transformation<android.graphics.Bitmap> transformation;
        private TransitionOptions transitionOptions;
        private int cornerPx; // 是否加载圆角,圆角的像素值
        private BaseTarget<Drawable> downloadListener;
        private int width;
        private int height;

        /**
         * 初始化
         *
         * @param context 上下文
         */
        private Build(Context context) {
            this.context = context;
        }

        /**
         * 加载
         *
         * @param model model
         */
        private Build loadGeneric(Object model) {
            this.model = model;
            return this;
        }

        /**
         * 加载
         *
         * @param model model
         *              支持：url resId file
         */
        public Build load(Object model) {
            loadGeneric(model);
            return this;
        }

        /**
         * 加载占位图
         *
         * @param loadingResId loadingResId
         */
        public Build placeholder(int loadingResId) {
            this.loadingResId = loadingResId;
            return this;
        }

        /**
         * 错误占位图
         *
         * @param errorResId errorResId
         */
        public Build error(int errorResId) {
            this.errorResId = errorResId;
            return this;
        }

        /**
         * 缩略图
         *
         * @param thumbUrl thumbUrl
         */
        public Build thumb(String thumbUrl) {
            this.thumbUrl = thumbUrl;
            return this;
        }

        /**
         * 是否加载圆角
         *
         * @param cornerDp isCorner
         */
        public Build isCorner(int cornerDp) {
            this.cornerPx = DensityUtil.dp2px(cornerDp);
            return this;
        }

        /**
         * 是否加载圆形
         *
         * @param isRound isRound
         */
        public Build isRound(boolean isRound) {
            this.isRound = isRound;
            return this;
        }

        /**
         * 是否缓存
         *
         * @param isCache isCache
         */
        public Build isCache(boolean isCache) {
            this.isCache = isCache;
            return this;
        }

        /**
         * 是否是静态图
         *
         * @param isBitmap isBitmap
         */
        public Build isBitmap(boolean isBitmap) {
            this.isBitmap = isBitmap;
            return this;
        }

        /**
         * 是否缓存
         *
         * @param width  isCache
         * @param height isCache
         */
        public Build size(int width, int height) {
            this.width = width;
            this.height = height;
            return this;
        }


        /**
         * 设置Transformation
         *
         * @param transformation
         */
        public Build setTransformation(Transformation<android.graphics.Bitmap> transformation) {
            this.transformation = transformation;
            return this;
        }

        public Build setTransitionOptions(TransitionOptions transitionOptions) {
            this.transitionOptions = transitionOptions;
            return this;
        }

        /**
         * 设置加载监听回调
         *
         * @param downloadListener isCache
         */
        public Build setListener(BaseTarget<Drawable> downloadListener) {
            this.downloadListener = downloadListener;
            return this;
        }

        /**
         * 开始加载
         */
        public void into(ImageView imgView) {

            // 初始化加载设置
            RequestOptions options = new RequestOptions();

            if (loadingResId != 0) {
                options = options.placeholder(loadingResId);// 加载过程中的图片
            }
            if (errorResId != 0) {
                options = options.error(errorResId);// 加载错误的图片
            }

            options = options.priority(Priority.HIGH);

            if (width > 0 && height > 0) {
                options = options.override(width, height);
            }

            if (isRound) {
                options = options.transform(new CircleCrop());// 圆形
            } else {
                if (cornerPx > 0) {
                    if (imgView != null && imgView.getScaleType() == ImageView.ScaleType.CENTER_CROP) {
                        //imagview设置了CENTER_CROP，使用下面的设置圆角失败
                        options = options.transforms(new CenterCrop(), new RoundedCorners(cornerPx));// 圆角
                    } else {
                        options = options.transform(new RoundedCorners(cornerPx));// 圆角
                    }
                } else if (transformation != null) {
                    options = options.transform(transformation);
                } else {
                    //                    options = options.dontTransform();
                }
            }

            if (isCache) {
                options = options.diskCacheStrategy(DiskCacheStrategy.ALL);// 缓存全尺寸
            } else {
                options = options.skipMemoryCache(true);// 跳过内存缓存
                options = options.diskCacheStrategy(DiskCacheStrategy.NONE);// 不缓存磁盘
            }


            // 开始加载
            RequestBuilder requestBuilder;

            RequestManager requestManager = Glide.with(context);
/*            if (isBitmap) {
                requestBuilder = requestManager.asBitmap();
            } else {
                requestBuilder = requestManager.asDrawable();
            }*/

            requestBuilder = requestManager.load(model);

            if (transitionOptions != null) {
                requestBuilder.transition(transitionOptions);
            }

            if (!TextUtils.isEmpty(thumbUrl)) {
                requestBuilder.thumbnail(Glide.with(context).load(thumbUrl));
            }

            requestBuilder.apply(options);// 应用设置

            if (downloadListener != null) {
                requestBuilder.into(downloadListener);// 图片控件
            } else {
                requestBuilder.into(imgView);// 图片控件
            }
        }
    }
}
