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
    private Context m_context;
    public MutableLiveData<Feed> Model;
    public RecyclerView.Adapter RecyclerAdapter;

    public MainViewModel(Application application){
        super(application);
        m_context = application.getApplicationContext();
        Model = new MutableLiveData<>();
        Model.setValue(new Feed());
        SharedPreferences sharedPreferences = m_context.getSharedPreferences("com.example.newsreader", Context.MODE_PRIVATE);
        if(!sharedPreferences.getBoolean("dataStored",false)){
            sharedPreferences.edit().putBoolean("dataStored",true).apply();
            DataBaseService dbService = new DataBaseService(Model,m_context);
            dataService = new WebDataService(Model,dbService);
        } else {
            dataService = new DataBaseService(Model,m_context);
        }

        RecyclerAdapter = new MainRecyclerViewAdapter(m_context, Model);
        dataService.startDataFetch();
    }

    public void refreshUI(){
        RecyclerAdapter.notifyDataSetChanged();
    }

    public void refresh(){
        if(m_context == null)
            return;
        Model.setValue(new Feed()); //clear model
        DataBaseService dbService = new DataBaseService(Model,m_context);
        dbService.deleteData(Model);
        dataService = new WebDataService(Model,dbService);
        dataService.startDataFetch();
    }
}
