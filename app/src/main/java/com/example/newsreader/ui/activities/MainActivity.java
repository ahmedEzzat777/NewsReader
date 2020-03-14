package com.example.newsreader.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.newsreader.R;
import com.example.newsreader.databinding.ActivityMainBinding;
import com.example.newsreader.model.Feed;
import com.example.newsreader.ui.recyclerViewAdapters.MainRecyclerViewAdapter;
import com.example.newsreader.ui.viewModels.MainViewModel;

public class MainActivity extends AppCompatActivity {
    private MainViewModel mainViewModel;
    private boolean isRefreshing;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        RecyclerView.Adapter recyclerAdapter = new MainRecyclerViewAdapter(this, mainViewModel.Model);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setMainViewModel(mainViewModel);
        binding.setRecyclerAdapter(recyclerAdapter);
        binding.setLifecycleOwner(this);
        setupSwipeToRefresh();
    }

    private void setupSwipeToRefresh() {
        isRefreshing = false;
        final SwipeRefreshLayout swipeRefreshLayout = findViewById(R.id.pullToRefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isRefreshing =true;
                mainViewModel.refresh();
            }
        });
        mainViewModel.Model.observe(this, new Observer<Feed>() {
            @Override
            public void onChanged(Feed feed) {
                if(isRefreshing && feed.getRecords().size() > 0){
                    isRefreshing =false;
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.settings:
                Intent intent = new Intent(getApplicationContext(),SettingsActivity.class);
                startActivity(intent);
                return true;
            case R.id.exit:
                System.exit(1);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
