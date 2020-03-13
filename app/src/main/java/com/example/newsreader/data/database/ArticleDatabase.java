package com.example.newsreader.data.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.newsreader.data.database.dao.ArticleDao;
import com.example.newsreader.data.database.table.Article;

@Database(entities = Article.class,exportSchema = false,version = 1)
public abstract class ArticleDatabase extends RoomDatabase {
    private static final String DB_NAME ="Articles_db";
    private static ArticleDatabase instance;
    public abstract ArticleDao articleDao();

    public static synchronized ArticleDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),ArticleDatabase.class,DB_NAME)
                    .fallbackToDestructiveMigration().build();
        }
        return instance;
    }
}
