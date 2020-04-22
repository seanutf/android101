package com.seanutf.android.base.homwiki.data;

import com.seanutf.android.base.media.data.MediaItem;

import java.util.List;

public class WikiData {
    public long id;
    public String title;
    public String content;
    public List<MediaItem> mediaList;
    public List<Tag> tagList;
    public int sortId;
}
