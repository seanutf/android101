package com.seanutf.android.base.homwiki.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface WikiDao {

    @Query("SELECT * FROM wiki")
    List<WikiData> getAll();

    @Query("SELECT * FROM wiki WHERE id IN (:userIds)")
    List<WikiData> loadAllByIds(int[] wikiIds);

    @Query("SELECT * FROM wiki WHERE first_name LIKE :first AND " +
            "last_name LIKE :last LIMIT 1")
    WikiData findByName(String first, String last);

    @Insert
    void insertAll(WikiData... data);

    @Delete
    void delete(WikiData user);
}
