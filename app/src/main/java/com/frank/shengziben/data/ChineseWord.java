package com.frank.shengziben.data;

import androidx.lifecycle.ViewModel;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ChineseWord implements Comparable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String word;
    private String pinyin;

    public ChineseWord(String word, String pinyin) {
        this.word = word;
        this.pinyin = pinyin;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getWord() { return word; }
    public void setWord(String word) { this.word = word; }

    public String getPinyin() { return pinyin; }
    public void setPinyin(String pinyin) { this.pinyin = pinyin; }

    @Override
    public int compareTo(Object o) {
        return -1;
    }
}
