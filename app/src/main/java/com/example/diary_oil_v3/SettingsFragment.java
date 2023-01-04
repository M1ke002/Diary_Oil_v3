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
    public static final String OCD = "oilchangedis";
    public static final String OCT = "oilchangetime";
    public static final String MTD = "maintenancedis";
    public static final String MTT = "maintenancetime";
    public SharedPreferences sharedPreferences;

    @Override
    public void onCreatePreferencesFix(@Nullable Bundle savedInstanceState, String rootKey) {
        getPreferenceManager().setSharedPreferencesName(SHARED_PREFS);
        getPreferenceManager().setSharedPreferencesMode(0);
        sharedPreferences= getPreferenceManager().getSharedPreferences();

        SharedPreferences sharedPreferences= getPreferenceManager().getSharedPreferences();
        int oct = sharedPreferences.getInt(OCT,0);
        int ocd = sharedPreferences.getInt(OCD,0);
        int mtd = sharedPreferences.getInt(MTD,0);
        int mtt = sharedPreferences.getInt(MTT,0);
        String unit_dis = sharedPreferences.getString("distance-unit","km");
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("oilchangetimeString",Integer.toString(oct));
        editor.putString("oilchangedisString",Integer.toString(ocd));
        editor.putString(MTD+"String",Integer.toString(mtd));
        editor.putString(MTT+"String",Integer.toString(mtt));
        editor.apply();


        addPreferencesFromResource(R.xml.test_preferences2);

        Preference oilchangetimeString = (Preference) findPreference("oilchangetimeString");
        Preference oilchangedisString = (Preference) findPreference("oilchangedisString");
        Preference maintenancedisString = (Preference) findPreference("maintenancedisString");
        Preference maintenancetimeString = (Preference) findPreference("maintenancetimeString");
        Preference distance_unit = (Preference) findPreference("distance-unit");
        Preference currency_unit = (Preference) findPreference("currency-unit");
        oilchangetimeString.setSummary(Integer.toString(oct)+" days ");
        oilchangedisString.setSummary(Integer.toString(ocd)+" "+unit_dis);
        maintenancedisString.setSummary(Integer.toString(mtd)+" "+unit_dis);
        maintenancetimeString.setSummary(Integer.toString(mtt)+" days ");


    }

    @Override
    public void onPause() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(OCD, Integer.parseInt(sharedPreferences.getString(OCD+"String","0")));
        editor.putInt(OCT, Integer.parseInt(sharedPreferences.getString(OCT+"String","0")));
        editor.putInt(MTD, Integer.parseInt(sharedPreferences.getString(MTD+"String","0")));
        editor.putInt(MTT, Integer.parseInt(sharedPreferences.getString(MTT+"String","0")));
        editor.apply();
        super.onPause();
    }
}