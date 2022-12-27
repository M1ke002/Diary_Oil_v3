package com.example.diary_oil_v3;

import android.os.Bundle;
import androidx.preference.PreferenceFragmentCompat;

public class SettingsPart2Fragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
    }
}