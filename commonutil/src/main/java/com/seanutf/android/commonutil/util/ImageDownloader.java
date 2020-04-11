package com.seanutf.android.commonutil.util;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.seanutf.android.commonutil.AppInfo;
import com.seanutf.android.commonutil.CommonUtilApp;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ImageDownloader extends AsyncTask<String, Void, ImageDownloader.Result> {

    private static final String TAG = ImageDownloader.class.getSimpleName();
    boolean isVideo;
    DownloadImageCallback callback;

    public ImageDownloader(boolean isVideo) {
        this.isVideo = isVideo;
    }

    public ImageDownloader setCallback(DownloadImageCallback callback) {
        this.callback = callback;
        return this;
    }

    @Override
    protected Result doInBackground(String... params) {
        Result result = new Result();
        result.isSuccess = true;
        result.isVideo = isVideo;
        for (int i = 0; i < params.length; i++) {
            try {

                String url = params[i];

                if (StringUtil.isNotEmptyStringAbsolute(url)) {

                    String path = FileUtils.getMaterialDir();

                    File picDir = new File(path);
                    if (!picDir.exists()) {
                        picDir.mkdirs();
                    }

                    long startTime = System.currentTimeMillis();


                    String fileName = startTime + ".jpg";

                    if (isVideo) {
                        fileName = startTime + ".mp4";
                    }

                    File file = new File(picDir, fileName);

                    if (AppInfo.isDebug()) {
                        String startLog = "download url is " + url + "  download path is " + file.getAbsolutePath() + "   startTime = " + startTime;
                        Log.d(TAG, startLog);
                    }

                    URL picURL = new URL(url);
                    HttpURLConnection conn = (HttpURLConnection) picURL.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setConnectTimeout(10 * 1000);
                    int code = conn.getResponseCode();
                    if (code == 200) {
                        InputStream is = new BufferedInputStream(
                                picURL.openStream(), 8192);
                        if (is == null)
                            throw new RuntimeException("stream is null");
                        boolean isSuccess;
                        FileOutputStream fos = new FileOutputStream(file);
                        if (!isVideo) {
                            Bitmap bitmap = BitmapFactory.decodeStream(is);
                            isSuccess = bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                            result.isSuccess = isSuccess;
                        } else {
                            byte buf[] = new byte[1024];
                            do {
                                //循环读取
                                int numread = is.read(buf);
                                if (numread == -1) {
                                    isSuccess = true;
                                    break;
                                }
                                fos.write(buf, 0, numread);
                                //更新进度条
                            } while (true);
                        }

                        fos.flush();
                        fos.close();
                        is.close();

                        if (isSuccess) {
                            if (AppInfo.isDebug()) {
                                String startLog = "download url is " + url + "   is success" + "   totalTime = " + (System.currentTimeMillis() - startTime);
                                //Log.d(startLog, TAG);
                            }
                            Uri uri = Uri.fromFile(file);
                            CommonUtilApp.instance.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
                        } else {
                            if (AppInfo.isDebug()) {
                                String startLog = "download url is " + url + "   is fail";
                                //Log.d(startLog, TAG);
                            }
                        }

                    } else {
                        if (code == 301 || code == 302) {
                            //得到重定向的地址
                            String location = conn.getHeaderField("Location");
                            if (StringUtil.isNotEmptyString(location)) {
                                URL resetUrl = new URL(location);
                                HttpURLConnection resetConn = (HttpURLConnection) resetUrl.openConnection();
                                resetConn.setRequestMethod("GET");
                                resetConn.setConnectTimeout(10 * 1000);
                                int resetCode = resetConn.getResponseCode();
                                if (resetCode == 200) {
                                    InputStream is = new BufferedInputStream(
                                            resetUrl.openStream(), 8192);
                                    if (is == null)
                                        throw new RuntimeException("stream is null");
                                    boolean isSuccess;
                                    FileOutputStream fos = new FileOutputStream(file);
                                    if (!isVideo) {
                                        Bitmap bitmap = BitmapFactory.decodeStream(is);
                                        isSuccess = bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                                        result.isSuccess = isSuccess;
                                    } else {
                                        byte buf[] = new byte[1024];
                                        do {
                                            //循环读取
                                            int numread = is.read(buf);
                                            if (numread == -1) {
                                                isSuccess = true;
                                                break;
                                            }
                                            fos.write(buf, 0, numread);
                                            //更新进度条
                                        } while (true);
                                    }

                                    fos.flush();
                                    fos.close();
                                    is.close();

                                    if (isSuccess) {
                                        if (AppInfo.isDebug()) {
                                            String startLog = "download url is " + url + "   is success" + "   totalTime = " + (System.currentTimeMillis() - startTime);
                                            //Log.d(startLog, TAG);
                                        }
                                        Uri uri = Uri.fromFile(file);
                                        CommonUtilApp.instance.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
                                    } else {
                                        if (AppInfo.isDebug()) {
                                            String startLog = "download url is " + url + "   is fail";
                                            //Log.d(startLog, TAG);
                                        }
                                    }

                                } else {
                                    if (AppInfo.isDebug()) {
                                        String startLog = "download url is " + url + "   is fail";
                                        //Log.d(startLog, TAG);
                                    }
                                    result.isSuccess = false;
                                    result.responseCode = code;
                                    if (params.length > 0)
                                        result.url = params[0];
                                }
                            }
                        } else {
                            if (AppInfo.isDebug()) {
                                String startLog = "download url is " + url + "   is fail";
                                //Log.d(startLog, TAG);
                            }
                            result.isSuccess = false;
                            result.responseCode = code;
                            if (params.length > 0)
                                result.url = params[0];
                        }
                    }
                }
            } catch (Exception e) {
                result.e = e;
                result.isSuccess = false;
                //result.responseCode = VidVideoUtil.VID_VIDEO_DAWMLOAD_URL_ERROR;
                result.responseCode = -1;
                if (params.length > 0)
                    result.url = params[0];
                break;
            }
        }
        return result;
    }

    protected void onPostExecute(Result result) {
        super.onPostExecute(result);
        if (callback != null && !isCancelled()) {
            callback.handle(result.isSuccess, result);
        }
    }

    public interface DownloadImageCallback {
        void handle(boolean isSuccess, Result result);
    }

    public static class Result {
        public boolean isSuccess;
        public Exception e;
        public boolean isVideo;
        public int responseCode;
        public String url;
    }
}
