package com.frank.shengziben.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {ChineseWord.class},version = 1,exportSchema = false)
public abstract class ChineseWordDatabase extends RoomDatabase {
    public abstract ChineseWordDao getChineseWordDao();
}
