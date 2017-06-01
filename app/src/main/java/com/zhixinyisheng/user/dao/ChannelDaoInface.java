package com.zhixinyisheng.user.dao;

import android.content.ContentValues;

import com.zhixinyisheng.user.domain.ChannelItem;

import java.util.List;
import java.util.Map;

/**
 * Created by USER on 2016/10/16.
 */
public interface ChannelDaoInface {
    public boolean addCache(ChannelItem item);

    public boolean deleteCache(String whereClause, String[] whereArgs);

    public boolean updateCache(ContentValues values, String whereClause,
                               String[] whereArgs);

    public Map<String, String> viewCache(String selection,
                                         String[] selectionArgs);

    public List<Map<String, String>> listCache(String selection,
                                               String[] selectionArgs);

    public void clearFeedTable();
}
