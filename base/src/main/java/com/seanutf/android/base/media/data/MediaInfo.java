package com.seanutf.android.base.media.data;

import java.io.Serializable;
import java.util.List;

public class MediaInfo implements Serializable {
    public List<MediaItem> allVideoList;
    public List<MediaItem> imageVideoList;
    public List<MediaItem> imageList;
    public List<AlbumData> albumList;
}
