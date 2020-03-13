package com.example.newsreader.data;

import androidx.lifecycle.MutableLiveData;

import com.example.newsreader.model.Feed;

public interface IAsyncDataService {
    void startDataFetch();
    void postData(MutableLiveData<Feed> feed);
    void refreshModel(MutableLiveData<Feed> feed);
}
