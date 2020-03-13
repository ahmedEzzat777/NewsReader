package com.example.newsreader.data;

import androidx.lifecycle.MutableLiveData;

import com.example.newsreader.model.Feed;
import com.example.newsreader.model.Record;

import java.util.ArrayList;

public class MockDataService implements IAsyncDataService {
    private MutableLiveData<Feed> feed;
    private ArrayList<Record> list;
    public MockDataService(MutableLiveData<Feed> feed){
        this.feed=feed;
        list = new ArrayList<>();
    }
    @Override
    public void startDataFetch() {
        new AsyncDataTask(){
            @Override
            protected void doInBackground() {
                list.add(new Record("google","search website","https://www.google.com/"));
                list.add(new Record("reddit","social website","https://www.reddit.com/"));
                list.add(new Record("google","search website","https://www.google.com/"));
                list.add(new Record("reddit","social website","https://www.reddit.com/"));
                list.add(new Record("google","search website","https://www.google.com/"));
                list.add(new Record("reddit","social website","https://www.reddit.com/"));
                list.add(new Record("google","search website","https://www.google.com/"));
                list.add(new Record("reddit","social website","https://www.reddit.com/"));

            }

            @Override
            protected void onPostExecute() {
                refreshModel(feed);
            }
        };

    }

    @Override
    public void postData(MutableLiveData<Feed> feed) {

    }

    @Override
    public void deleteData(MutableLiveData<Feed> feed) {

    }

    @Override
    public void refreshModel(MutableLiveData<Feed> feed) {
        feed.setValue(feed.getValue().addRecords(list));
    }
}
