package com.example.newsreader.ui.viewModels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsreader.data.IAsyncDataService;
import com.example.newsreader.data.MockDataService;
import com.example.newsreader.data.WebDataService;
import com.example.newsreader.model.Feed;
import com.example.newsreader.ui.recyclerViewAdapters.MainRecyclerViewAdapter;

public class MainViewModel extends AndroidViewModel{
    private IAsyncDataService dataService;
    public MutableLiveData<Feed> m_model;
    public RecyclerView.Adapter m_adapter;

    public MainViewModel(Application application){
        super(application);
        m_model = new MutableLiveData<Feed>();
        m_model.setValue(new Feed());
        dataService = new WebDataService(m_model);
        m_adapter = new MainRecyclerViewAdapter(application.getApplicationContext(),m_model);
        dataService.startRecordFetch();
    }
}
