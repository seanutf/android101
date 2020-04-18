package com.seanutf.android.base.media.data;

/**
 * Created by sean on 2016/7/5.
 */
public class AlbumData {

    /**
     * 对于目录来讲，可能有多个相册和集合目录
     */
    public static final String DIR_ALBUM_ALL = ":all";  //媒体集合目录， 自定义的概念目录
    public static final String DIR_ALBUM_IMG = ":img";  //图片集合目录，自定义的概念目录
    public static final String DIR_ALBUM_VIDEO = ":video";  //视频集合目录，自定义的概念目录

    /**
     * 图片的文件夹路径
     */
    private String dir;

    /**
     * 第一张图片的路径
     */
    private String firstImagePath;

    /**
     * 文件夹的名称
     */
    private String name;

    /**
     * 图片的数量
     */
    private int count;

    private boolean isSelected;

    private int type;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
        int lastIndexOf = this.dir.lastIndexOf("/");
        if (lastIndexOf != -1) {
            this.name = this.dir.substring(lastIndexOf);
        }
    }

    public String getFirstImagePath() {
        return firstImagePath;
    }

    public void setFirstImagePath(String firstImagePath) {
        this.firstImagePath = firstImagePath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
