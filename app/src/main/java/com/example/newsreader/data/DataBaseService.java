package com.example.newsreader.data;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.example.newsreader.data.database.ArticleDatabase;
import com.example.newsreader.data.database.table.Article;
import com.example.newsreader.model.Feed;
import com.example.newsreader.model.Record;

import java.util.ArrayList;
import java.util.List;

public class DataBaseService implements IAsyncDataService {
    private MutableLiveData<Feed> m_feed;
    private Context m_context;
    private ArrayList<Record> m_list;
    public DataBaseService(MutableLiveData<Feed> feed, Context context){
        m_feed=feed;
        m_context = context;
        m_list = new ArrayList<>();
    }

    @Override
    public void startDataFetch() {
        new AsyncDataTask(){

            @Override
            protected void doInBackground() {
                ArticleDatabase db =ArticleDatabase.getInstance(m_context);
                ArrayList<Article> articles = (ArrayList<Article>)db.articleDao().getAll();
                for(Article article:articles){
                    m_list.add(new Record(article.id,article.title,article.author,article.site));
                }
            }

            @Override
            protected void onPostExecute() {
                refreshModel(m_feed);
            }
        };
    }

    @Override
    public void postData(MutableLiveData<Feed> feed) {
        final MutableLiveData<Feed> finalfeed = feed;
        new AsyncDataTask(){

            @Override
            protected void doInBackground() {
                if(finalfeed.getValue().getRecords().size()<=0)
                    return;
                List<Article> recList = new ArrayList<>();
                ArticleDatabase db =ArticleDatabase.getInstance(m_context);
                for(Record rec:finalfeed.getValue().getRecords()){
                    recList.add(new Article(rec.get_id().toString(),rec.get_title(),rec.get_author(),rec.get_site()));
                }
                db.articleDao().insert(recList);
            }

            @Override
            protected void onPostExecute() {

            }
        };
    }

    @Override
    public void deleteData(MutableLiveData<Feed> feed) {
        new AsyncDataTask(){

            @Override
            protected void doInBackground() {
                ArticleDatabase db =ArticleDatabase.getInstance(m_context);
                db.articleDao().deleteAll();
            }

            @Override
            protected void onPostExecute() {
                refreshModel(m_feed);
            }
        };
    }

    @Override
    public void refreshModel(MutableLiveData<Feed> feed) {
        feed.setValue(feed.getValue().addRecords(m_list));
    }
}
