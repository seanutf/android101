package com.seanutf.android.base.homwiki.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.seanutf.android.base.media.data.MediaItem;

import java.util.List;

@Entity
public class WikiData {

    @PrimaryKey
    public long id;

    @ColumnInfo(name = "title")
    public String title;

    @ColumnInfo(name = "content")
    public String content;

    @ColumnInfo(name = "mediaList")
    public List<MediaItem> mediaList;

    @ColumnInfo(name = "tagList")
    public List<Tag> tagList;

    @ColumnInfo(name = "sortId")
    public int sortId;
    public String imgUrl;
}
