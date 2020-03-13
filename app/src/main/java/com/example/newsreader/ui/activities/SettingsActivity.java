package com.example.newsreader.ui.activities;

import android.os.Bundle;
import android.os.PatternMatcher;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.example.newsreader.R;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings, new SettingsFragment())
                .commit();
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
            findPreference("numberOfArticles").setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    String valStr = (String) newValue;
                    if(valStr.matches("-?(0|[1-9]\\d*)")){
                        int res = Integer.parseInt(valStr);
                        if(res<=500 && res>0)
                            return true;
                    }
                    Toast.makeText(getContext(), "Wrong Input! must be a number from 1 to 500", Toast.LENGTH_SHORT).show();
                    return false;
                }
            });
        }
    }
}