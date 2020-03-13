package com.example.newsreader.data.database.table;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.UUID;

@Entity(tableName = "Articles")
public class Article {
    @NonNull
    @PrimaryKey
    public String id;
    @ColumnInfo(name = "Title")
    public String title;
    @ColumnInfo(name = "Author")
    public String author;
    @ColumnInfo(name = "Site")
    public String site;

    public Article(String id,String title,String author,String site){
        this.id =id;
        this.title=title;
        this.author=author;
        this.site=site;
    }
}
