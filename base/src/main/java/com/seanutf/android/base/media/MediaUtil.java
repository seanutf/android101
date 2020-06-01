package com.seanutf.android.base.media;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.seanutf.android.base.BaseApp;
import com.seanutf.android.base.media.data.AlbumData;
import com.seanutf.android.base.media.data.Media;
import com.seanutf.android.base.media.data.MediaInfo;
import com.seanutf.android.base.media.data.MediaItem;
import com.seanutf.android.commonutil.util.StringUtil;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class MediaUtil {

    public static String makeDuration(long longDuration) {
        double duration = (double) longDuration / 1000;
        int hour = ((int) duration / (60 * 60));
        int minute = ((int) duration % (60 * 60) / 60);
        float second = (float) (duration % (60 * 60) % 60);
        return String.format("%02d:%02d:%02.2f", hour, minute, second);
    }

    public static MediaInfo scanImageFileAndAlbum(String newSegment) {
        if (StringUtil.isEquals(newSegment, AlbumData.DIR_ALBUM_ALL) ||
                StringUtil.isEquals(newSegment, AlbumData.DIR_ALBUM_VIDEO) ||
                StringUtil.isEquals(newSegment, AlbumData.DIR_ALBUM_IMG)) {
            newSegment = null;
        }
        HashSet<String> mDirPaths = new HashSet<String>();// 临时的辅助类，用于防止同一个文件夹的多次扫描
        List<AlbumData> albumList = new ArrayList<>();
        MediaInfo mediaInfo = new MediaInfo();
        List<MediaItem> mediaItems = new ArrayList<>();
        ContentResolver resolver = BaseApp.instance.getContentResolver();
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] objs = {
                MediaStore.Images.Media.DISPLAY_NAME,//视频文件在sdcard中的名字
                MediaStore.Images.Media.SIZE,
                MediaStore.Images.Media.DATA,//SDCARD的据对地址
                MediaStore.Images.Media.WIDTH,
                MediaStore.Images.Media.HEIGHT,
                MediaStore.Images.Media.DATE_MODIFIED,
        };

        String selection = null;
        String[] selectionArgs = null;
        if (newSegment != null && newSegment.length() > 0) {
            selection = MediaStore.Images.Media.DATA + " like ?";
            selectionArgs = new String[]{newSegment + "%"};
        }
        Cursor cursor = resolver.query(uri, objs, selection, selectionArgs, MediaStore.MediaColumns.DATE_MODIFIED + " DESC");
        if (cursor != null) {
            while (cursor.moveToNext()) {
                MediaItem mediaItem = new MediaItem();
                mediaItem.name = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME));
                mediaItem.size = cursor.getLong(cursor.getColumnIndex(MediaStore.Images.Media.SIZE));
                mediaItem.mediaPath = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                mediaItem.mediaWidth = cursor.getInt(cursor.getColumnIndex(MediaStore.Images.Media.WIDTH));
                mediaItem.mediaHeight = cursor.getInt(cursor.getColumnIndex(MediaStore.Images.Media.HEIGHT));
                mediaItem.dateModified = cursor.getLong(cursor.getColumnIndex(MediaStore.Images.Media.DATE_MODIFIED));
                mediaItem.mediaType = Media.TYPE_MEDIA_IMAGE;
                mediaItems.add(mediaItem);
                if (mediaItem.mediaPath == null)
                    continue;
                // 获取该图片的父路径名
                File parentFile = new File(mediaItem.mediaPath).getParentFile();
                if (parentFile == null)
                    continue;
                String dirPath = parentFile.getAbsolutePath();
                AlbumData albumData = null;
                // 利用一个HashSet防止多次扫描同一个文件夹（不加这个判断，图片多起来还是相当恐怖的~~）
                if (mDirPaths.contains(dirPath)) {
                    continue;
                } else {
                    mDirPaths.add(dirPath);
                    // 初始化imageFloder
                    albumData = new AlbumData();
                    albumData.setDir(dirPath);
                    albumData.setFirstImagePath(mediaItem.mediaPath);
                }
                if (parentFile.list() == null)
                    continue;
                int picSize = parentFile.list(new FilenameFilter() {
                    @Override
                    public boolean accept(File dir, String filename) {
                        if (filename.endsWith(".jpg")
                                || filename.endsWith(".png")
                                || filename.endsWith(".jpeg"))
                            return true;
                        return false;
                    }
                }).length;
                albumData.setCount(picSize);
                albumList.add(albumData);

            }
            cursor.close();
        }
        mediaInfo.imageList = mediaItems;
        mediaInfo.albumList = albumList;
        return mediaInfo;
    }

    public static List<MediaItem> scanVideoFile(String newSegment) {
        if (StringUtil.isEquals(newSegment, AlbumData.DIR_ALBUM_ALL) ||
                StringUtil.isEquals(newSegment, AlbumData.DIR_ALBUM_VIDEO) ||
                StringUtil.isEquals(newSegment, AlbumData.DIR_ALBUM_IMG)) {
            newSegment = null;
        }
        List<MediaItem> mediaItems = new ArrayList<>();
        ContentResolver resolver = BaseApp.instance.getContentResolver();
        Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        String[] objs = {
                MediaStore.Video.Media.DISPLAY_NAME,//视频文件在sdcard中的名字
                MediaStore.Video.Media.DURATION,
                MediaStore.Video.Media.SIZE,
                MediaStore.Video.Media.DATA,//SDCARD的据对地址
                MediaStore.Video.Media.ARTIST,//艺术家
                MediaStore.Video.Media.WIDTH,
                MediaStore.Video.Media.HEIGHT,
                MediaStore.Video.Media.DATE_MODIFIED,
        };

        String selection = null;
        String[] selectionArgs = null;
        if (newSegment != null && newSegment.length() > 0) {
            selection = MediaStore.Video.Media.DATA + " like ?";
            selectionArgs = new String[]{newSegment + "%"};
        }

        Cursor cursor = resolver.query(uri, objs, selection, selectionArgs, MediaStore.MediaColumns.DATE_MODIFIED + " DESC");
        if (cursor != null) {
            while (cursor.moveToNext()) {
                MediaItem mediaItem = new MediaItem();
                mediaItem.name = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DISPLAY_NAME));
                mediaItem.duration = cursor.getLong(cursor.getColumnIndex(MediaStore.Video.Media.DURATION));
                mediaItem.size = cursor.getLong(cursor.getColumnIndex(MediaStore.Video.Media.SIZE));
                mediaItem.mediaPath = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA));
                mediaItem.artist = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.ARTIST));
                mediaItem.mediaWidth = cursor.getInt(cursor.getColumnIndex(MediaStore.Video.Media.WIDTH));
                mediaItem.mediaHeight = cursor.getInt(cursor.getColumnIndex(MediaStore.Video.Media.HEIGHT));
                mediaItem.dateModified = cursor.getLong(cursor.getColumnIndex(MediaStore.Video.Media.DATE_MODIFIED));
                mediaItem.mediaType = Media.TYPE_MEDIA_VIDEO;
                mediaItems.add(mediaItem);
            }
            cursor.close();
        }

        return mediaItems;
    }

    public static MediaInfo scanMediaFile(List<MediaItem> videoList, String newSegment) {
        MediaInfo mediaInfo = scanImageFileAndAlbum(newSegment);
        // List<MediaItem> imageList = scanImageFile(newSegment);
        List<MediaItem> imageList = mediaInfo.imageList;
        List<MediaItem> tempList = new ArrayList<>(imageList);
        tempList.addAll(videoList);

        int p1 = videoList.size() - 1, p2 = imageList.size() - 1;
        int q = videoList.size() + imageList.size() - 1;

        while (p1 >= 0 && p2 >= 0) { //二者长度相等部分
            if (videoList.get(p1).dateModified <= imageList.get(p2).dateModified) {
                tempList.set(q, videoList.get(p1));
                p1--;
                q--;
            } else {
                tempList.set(q, imageList.get(p2));
                q--;
                p2--;
            }
        }

        while (p1 >= 0) { //假如a数组多出来
            tempList.set(q, videoList.get(p1));
            q--;
            p1--;
        }

        while (p2 >= 0)  //假如b数组多出来
        {
            tempList.set(q, imageList.get(p2));
            q--;
            p2--;
        }

        mediaInfo.imageVideoList = tempList;
        return mediaInfo;
    }
}
