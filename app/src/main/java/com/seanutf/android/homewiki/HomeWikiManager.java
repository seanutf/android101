package com.seanutf.android.homewiki;

import com.seanutf.android.base.homwiki.data.WikiData;

public class HomeWikiManager {

    WikiData data;

    public void savePost(WikiData data) {
        this.data = data;
    }

    public WikiData getPost() {
        return this.data;
    }


    private HomeWikiManager() {
    }

    private static class HomeWikiManagerHolder {
        static HomeWikiManager instance = new HomeWikiManager();
    }

    public static HomeWikiManager getInstance() {
        return HomeWikiManagerHolder.instance;
    }
}
