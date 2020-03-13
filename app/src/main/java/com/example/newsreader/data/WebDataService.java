package com.example.newsreader.data;

import androidx.lifecycle.MutableLiveData;

import com.example.newsreader.model.Feed;
import com.example.newsreader.model.Record;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class WebDataService implements IAsyncDataService {
    private static final int MAX_RESULTS = 20;

    private ArrayList<String> m_listJSON;
    private ArrayList<Record> m_list;
    private String m_topArticlesJSON;
    private ArrayList<String> m_topArticles;

    private IAsyncDataService m_localDataService;
    private int m_maxResults;

    private MutableLiveData<Feed> m_feed;
    public WebDataService(MutableLiveData<Feed> feed){
        init(feed);
        m_localDataService =null;
        m_maxResults=MAX_RESULTS;
    }
    public WebDataService(MutableLiveData<Feed> feed,IAsyncDataService localDataService){
        init(feed);
        m_localDataService = localDataService;
        m_maxResults=MAX_RESULTS;
    }
    public WebDataService(MutableLiveData<Feed> feed,IAsyncDataService localDataService,int maxResults){
        init(feed);
        m_localDataService =localDataService;
        m_maxResults =maxResults;
    }

    private void init(MutableLiveData<Feed> feed){
        m_feed=feed;
        m_topArticlesJSON="";
        m_listJSON = new ArrayList<>();
        m_list = new ArrayList<>();
        m_topArticles = new ArrayList<>();
    }
    @Override
    public void startDataFetch() {
        new AsyncDataTask(){
            @Override
            protected void doInBackground() {
                m_topArticlesJSON = fetchString("https://hacker-news.firebaseio.com/v0/topstories.json");
            }

            @Override
            protected void onPostExecute() {
                parseTopSites(m_topArticlesJSON);
                fetchRecordList(m_topArticles);
            }
        };
    }

    @Override
    public void postData(MutableLiveData<Feed> feed) {

    }

    @Override
    public void deleteData(MutableLiveData<Feed> feed) {

    }

    private void fetchRecordList(final ArrayList<String> topArticles) {
        new AsyncDataTask(){
            @Override
            protected void doInBackground() {
                int i=0;
                for(String article:topArticles){
                    if(i>=m_maxResults) return;
                    String url = "https://hacker-news.firebaseio.com/v0/item/"+article+".json";
                    m_listJSON.add(fetchString(url));
                    i++;
                }
            }

            @Override
            protected void onPostExecute() {
                parseList(m_listJSON);
                refreshModel(m_feed);
                if(m_localDataService!=null)
                    m_localDataService.postData(m_feed);
            }
        };
    }


    @Override
    public void refreshModel(MutableLiveData<Feed> feed) {
        feed.setValue(feed.getValue().addRecords(m_list));
    }

    private String fetchString(String url){
        String stringToFetch="";
        HttpURLConnection urlconnection;
        try {
            URL topPostsUrl = new URL(url);
            urlconnection = (HttpURLConnection)topPostsUrl.openConnection();
            InputStream in = urlconnection.getInputStream();
            InputStreamReader reader=new InputStreamReader(in);
            int data =reader.read();
            while (data!=-1){
                char c = (char)data;
                stringToFetch +=c;
                data =reader.read();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringToFetch;
    }

    private void parseTopSites(String topSites){
        try {
            JSONArray jsonArray = new JSONArray(topSites);
            for(int i=0;i<jsonArray.length();i++){
                String topSite = Integer.toString((int)jsonArray.get(i));
                m_topArticles.add(topSite);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void parseList(ArrayList<String> m_listJSON) {
        for (String jsonStr:m_listJSON){
            try {
                JSONObject jsonObject = new JSONObject(jsonStr);
                String title = jsonObject.getString("title");
                String url=null;
                if(jsonObject.has("url"))
                     url = jsonObject.getString("url");
                String author=null;
                if(jsonObject.has("by"))
                    author = jsonObject.getString("by");

                m_list.add(new Record(title,author,url));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
