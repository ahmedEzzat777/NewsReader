package com.example.newsreader.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.example.newsreader.R;
import com.example.newsreader.model.Feed;
import com.example.newsreader.ui.viewModels.MainViewModel;
import com.example.newsreader.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private MainViewModel mainViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setMainViewModel(mainViewModel);
        binding.setLifecycleOwner(this);

        mainViewModel.Model.observe(this, new Observer<Feed>() {
            @Override
            public void onChanged(Feed feed) {
                mainViewModel.refreshUI();
            }
        });
    }
}
