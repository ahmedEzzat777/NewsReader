package com.example.newsreader.data.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.newsreader.data.database.table.Article;

import java.util.ArrayList;
import java.util.List;
@Dao
public interface ArticleDao {
    @Query("SELECT * FROM Articles")
    List<Article> getAll();
    @Query("SELECT * FROM Articles WHERE id = :id LIMIT 1")
    Article get(String id);

    @Insert
    void insert(Article article);
    @Insert
    void insert(List<Article> articles);
    @Update
    void update(Article article);
    @Delete
    void delete(Article article);
    @Query("DELETE FROM Articles")
    void deleteAll();
}
