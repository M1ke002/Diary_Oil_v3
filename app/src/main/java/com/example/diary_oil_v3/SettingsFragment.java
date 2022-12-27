package com.example.diary_oil_v3;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.takisoft.preferencex.PreferenceFragmentCompat;

import androidx.annotation.Nullable;
import androidx.preference.*;
import androidx.preference.PreferenceScreen;


public class SettingsFragment extends PreferenceFragmentCompat {
    public static final String SHARED_PREFS = "sharedPrefs";



    @Override
    public void onCreatePreferencesFix(@Nullable Bundle savedInstanceState, String rootKey) {
        getPreferenceManager().setSharedPreferencesName(SHARED_PREFS);
        getPreferenceManager().setSharedPreferencesMode(0);
        addPreferencesFromResource(R.xml.test_preferences2);
        Preference github = (Preference) findPreference("github");
        assert github != null;
        github.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/NoodleGodz/Diary_Oil_v3"));
                startActivity(browserIntent);
                return true;

            }
        });


    }




}