package com.example.diary_oil_v3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class On_Boa1 extends AppCompatActivity {

    private Button btn1;
    private Button btn2;
    private EditText Name;
    private EditText Vehicle;
    private String text,text2;
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String NAME = "name";
    public static final String VEHICLE = "vehicle";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setuppage1);

        Name = (EditText) findViewById(R.id.editTextTextPersonName);
        Vehicle = (EditText) findViewById(R.id.editTextTextVehicleName);
        btn1 = (Button) findViewById(R.id.next_btn_1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
                Intent intent = new Intent(On_Boa1.this, On_Boa2.class);
                startActivity(intent);

            }
        });

        btn2 = (Button) findViewById(R.id.return_btn_1);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(On_Boa1.this, On_Boa.class);
                startActivity(intent);
            }
        });
    loadData();
    UpdateView();
    }

    public void saveData(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(NAME, Name.getText().toString());
        editor.putString(VEHICLE, Vehicle.getText().toString());
        editor.apply();
        Toast.makeText(this, "Data Saved" ,Toast.LENGTH_SHORT);

    }

    public void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        text = sharedPreferences.getString(NAME, "");
        text2 = sharedPreferences.getString(VEHICLE, "");
    }

    public void UpdateView(){
        Name.setText(text);
        Vehicle.setText(text2.toUpperCase());
    }
}