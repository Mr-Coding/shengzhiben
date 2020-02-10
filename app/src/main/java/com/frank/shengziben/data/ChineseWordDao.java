package com.frank.shengziben.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ChineseWordDao {
    @Insert
    void insetChineseWord(ChineseWord word);

    @Delete
    void deleteChineseWord(ChineseWord word);

    @Query("SELECT * FROM ChineseWord")
    List<ChineseWord> getAllChineseWord();
}
