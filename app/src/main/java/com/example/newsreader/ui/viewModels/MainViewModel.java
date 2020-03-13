package com.example.newsreader.ui.viewModels;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsreader.data.DataBaseService;
import com.example.newsreader.data.IAsyncDataService;
import com.example.newsreader.data.WebDataService;
import com.example.newsreader.model.Feed;
import com.example.newsreader.ui.recyclerViewAdapters.MainRecyclerViewAdapter;

public class MainViewModel extends AndroidViewModel{
    private IAsyncDataService dataService;
    public MutableLiveData<Feed> m_model;
    public RecyclerView.Adapter m_adapter;

    public MainViewModel(Application application){
        super(application);
        m_model = new MutableLiveData<>();
        m_model.setValue(new Feed());
        SharedPreferences sharedPreferences = application.getApplicationContext().getSharedPreferences("com.example.newsreader", Context.MODE_PRIVATE);
        if(!sharedPreferences.getBoolean("dataStored",false)){
            sharedPreferences.edit().putBoolean("dataStored",true).apply();
            DataBaseService dbService = new DataBaseService(m_model,application.getApplicationContext());
            dataService = new WebDataService(m_model,dbService);
        } else {
            dataService = new DataBaseService(m_model,application.getApplicationContext());
        }

        m_adapter = new MainRecyclerViewAdapter(application.getApplicationContext(),m_model);
        dataService.startDataFetch();
    }
}
