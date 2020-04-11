package com.seanutf.android.commonutil.util;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import androidx.core.content.FileProvider;

import com.seanutf.android.commonutil.CommonUtilApp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileUtils {

    /**
     * 获取应用自身文件存储的根目录
     */
    private static String getAppFileRoot() {
        return getAppFileRoot(false);
    }

    /**
     * 获取应用自身文件存储的根目录
     *
     * @param alwaysSave 根据相关业务需求，所需文件是否需要永久保存,
     *                   永久保存的场景会返回外部存储空间中自定义的应用目录，需要读写SD卡的权限
     *                   非永久保存的会返回外部存储空间中的应用私有目录，不需要读写SD卡的权限，
     *                   但数据可能会被用户清除数据
     *                   todo 对android Q 的兼容
     */
    private static String getAppFileRoot(boolean alwaysSave) {
        if (!alwaysSave) {
            File appRootFile = CommonUtilApp.instance.getExternalFilesDir(null);
            if (appRootFile != null) {
                return appRootFile.getAbsolutePath();
            }
        }

        String state = Environment.getExternalStorageState();
        File rootDir = state.equals(Environment.MEDIA_MOUNTED) ? Environment.getExternalStorageDirectory() : CommonUtilApp.instance.getCacheDir();
        return rootDir.getAbsolutePath() + "/bonbon/wanwu/";
    }

    public static String getMaterialDir() {
        String state = Environment.getExternalStorageState();
        File rootDir = state.equals(Environment.MEDIA_MOUNTED) ? Environment.getExternalStorageDirectory() : CommonUtilApp.instance.getCacheDir();
        return rootDir.getAbsolutePath() + "/Pictures/wanwu/";
    }

    public static String getPicturesDir() {
        String state = Environment.getExternalStorageState();
        File rootDir = state.equals(Environment.MEDIA_MOUNTED) ? Environment.getExternalStorageDirectory() : CommonUtilApp.instance.getCacheDir();
        return rootDir.getAbsolutePath() + "/Pictures/";
    }

    public static String getAppCacheDir() {
        File cacheFileDir = CommonUtilApp.instance.getExternalCacheDir();
        if (cacheFileDir != null && cacheFileDir.exists()) {
            return cacheFileDir.getAbsolutePath();
        } else {
            return CommonUtilApp.instance.getCacheDir().getAbsolutePath();
        }
    }

    public static String getImageDownloadDir() {
        return Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "dearxy";//
    }

    public static String getSnapshotDir() {
        return getAppFileRoot() + "Snapshot";
    }

    public static String getChatVideoCompressionDir() {
        return getVideoCompressionDir();
    }

    public static String getVideoCompressionDir() {
        return getAppCacheDir() + "/VideoCompress/";
    }

    public static String getChatVideoCropDir() {
        return getAppCacheDir() + "/VideoCrop/";
    }

    public static String getVideoCropDir() {
        return getAppFileRoot() + "/VideoCrop/";
    }

    public static String getTTVideoDir() {
        String path = getAppFileRoot() + "/TTVideo/";
        File fileDir = new File(path);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        return path;
    }

    /**
     * 发布时的保存任务相关文件的文件夹
     * todo 对android Q 的兼容
     */
    public static String getPostTaskDir() {
        return getAppFileRoot() + "/postTask/";
    }

    public static String getAppUpdateDir() {
        return getAppFileRoot() + "/appUpdate/";
    }

    /**
     * 删除文件夹和文件夹里面的文件
     *
     * @param pPath
     * @return
     */
    public static void deleteDir(final String pPath) {
        File dir = new File(pPath);
        deleteDirAndFile(dir);
    }

    public static void deleteDirAndFile(File dir) {
        if (dir == null || !dir.exists())
            return;
        if (!dir.isDirectory()) {
            dir.delete();
            return;
        }

        for (File file : dir.listFiles()) {
            if (file.isFile())
                file.delete(); // 删除所有文件
            else if (file.isDirectory())
                deleteDirAndFile(file); // 递规的方式删除文件夹
        }
        dir.delete();// 删除目录本身
    }

    /**
     * 保存图片，不通知系统
     */
    public static boolean saveBitmap(Bitmap saveBitmap) {
        return saveBitmap(saveBitmap, false);
    }

    /**
     * 方法作用：本地生成截图后保存图片
     * 方法使用：适用明知或想要图片格式是.jpg的情况
     */
    public static boolean saveBitmap(Bitmap saveBitmap, boolean broadcast) {
        if (saveBitmap == null)
            return false;
        String filePath = getPicturesDir();
        File fileDir = new File(filePath);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }

        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(filePath, fileName);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            //通过io流的方式来压缩保存图片
            boolean isSuccess = saveBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
            saveBitmap.recycle();
            if (isSuccess) {
                //保存图片后是否需要发送广播通知更新数据库
                if (broadcast) {
                    Uri uri2 = Uri.fromFile(file);
                    CommonUtilApp.instance.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri2));
                }
            }

            return isSuccess;
        } catch (IOException e) {
            e.printStackTrace();
            saveBitmap.recycle();
        }
        return false;
    }


    /**
     * 方法作用：本地保存图片返回图片路径
     * 方法使用：适用分享图片到微信的情况
     */
    public static String saveBitmapToShare(Bitmap saveBitmap) {
        if (saveBitmap == null)
            return "";
        String filePath = getAppCacheDir();
        File fileDir = new File(filePath);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }

        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(filePath, fileName);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            //通过io流的方式来压缩保存图片
            boolean isSuccess = saveBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
            saveBitmap.recycle();
            if (isSuccess) {
                return file.getAbsolutePath();
            } else {
                return "";
            }
        } catch (IOException e) {
            e.printStackTrace();
            saveBitmap.recycle();
        }
        return "";
    }

    /**
     * 方法作用：已经有下载好位于缓存目录中的图片需要复制到图片目录
     * 方法使用：因为方法中有判断图片格式的逻辑，适用于下载网络图片
     * 可结合ImageLoader中的downloadImage()方法
     */
    public static boolean saveImgFile(File imgCacheFile, boolean broadcast) {
        if (imgCacheFile == null)
            return false;

        //1.判断图片的格式
        String cacheFilePath = imgCacheFile.getPath();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(cacheFilePath, options);

        //outMimeType是以--”image/png”、”image/jpeg”、”image/gif”…….这样的方式返回的
        String mimeType = options.outMimeType;
        Log.d("glideTest", "图片类型：" + mimeType);

        String newImgFileName = "";
        switch (mimeType) {
            case "image/png":
                newImgFileName = System.currentTimeMillis() + ".png";
                break;
            case "image/gif":
                newImgFileName = System.currentTimeMillis() + ".gif";
                break;
            case "image/jpeg":
            default:
                newImgFileName = System.currentTimeMillis() + ".jpg";
                break;
        }

        String filePath = getPicturesDir();
        File fileDir = new File(filePath);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }

        File fil = new File(filePath, newImgFileName);
        InputStream input = null;
        OutputStream output = null;
        try {
            input = new FileInputStream(imgCacheFile);
            output = new FileOutputStream(fil);
            byte[] buf = new byte[1024];
            int bytesRead;
            while ((bytesRead = input.read(buf)) > 0) {
                output.write(buf, 0, bytesRead);
            }

            //保存图片后是否需要发送广播通知更新数据库
            if (broadcast) {
                Uri uri2 = Uri.fromFile(fil);
                CommonUtilApp.instance.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri2));
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (input != null) {
                    input.close();
                }

                if (output != null) {
                    output.close();
                }
            } catch (Exception e) {
                e.printStackTrace();

            }
        }
    }

    /**
     * 创建文件
     */
    public static boolean createFile(String fileName) {
        File file = new File(fileName);//判断文件夹是否存在,如果不存在则创建文件夹
        return file.exists() || file.mkdirs();
    }

    /**
     * 删除文件
     */
    public static boolean deleteFile(String filePath) {
        try {
            File file = new File(filePath);
            return file.exists() && file.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static Uri buildFileUri(String filePath) {
        Uri fileUri = null;
        if (StringUtil.isNotEmptyString(filePath)) {
            File file = new File(filePath);
            if (file.exists()) {
                fileUri = FileProvider.getUriForFile(CommonUtilApp.instance, CommonUtilApp.instance.getPackageName() + ".provider", file);
            }
        }
        return fileUri;
    }

    public static int getFileSize(String filePath) {
        File file = new File(filePath);
        int size = 0;
        try {
            FileInputStream fis = new FileInputStream(file);
            size = fis.available();
        } catch (Exception e) {

        }
        return size;
    }
}
