package com.example.diary_oil_v3;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class On_Boa3 extends AppCompatActivity {

    private Button btn1;
    private Button btn2,btn3;
    private Button db1,db2;
    private EditText Lastoil;
    private EditText Lastmain;
    private DatePickerDialog datePickerDialog, datePickerDialog2;
    private String text,text2;
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String LO = "lastoil";
    public static final String LM = "lastmain";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setuppage3);
        initDatePicker();
        initDatePicker2();
        db1 = (Button) findViewById(R.id.date_picker_actions_btn);
        Lastoil = (EditText) findViewById(R.id.editTextTextPersonName);
        Lastmain = (EditText) findViewById(R.id.editTextTextVehicleName);
        btn1 = (Button) findViewById(R.id.next_btn_3);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
                Intent intent = new Intent(On_Boa3.this, On_Boa4.class);
                startActivity(intent);


            }
        });

        btn2 = (Button) findViewById(R.id.return_btn_3);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(On_Boa3.this, On_Boa2.class);
                startActivity(intent);
            }
        });

        btn3 = (Button) findViewById(R.id.skip_btn_3);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                skipData();
                Intent intent = new Intent(On_Boa3.this, On_Boa4.class);
                startActivity(intent);
            }
        });


    loadData();
    UpdateView();
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month +1;
                String date = day + "/" + month + "/" + year;
                Lastoil.setText(date);

            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int style = AlertDialog.THEME_HOLO_LIGHT;
        datePickerDialog = new DatePickerDialog( this, style, dateSetListener , year , month , day);
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

    }

    private void initDatePicker2() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month +1;
                String date = day + "/" + month + "/" + year;
                Lastmain.setText(date);

            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int style = AlertDialog.THEME_HOLO_LIGHT;
        datePickerDialog2 = new DatePickerDialog( this, style, dateSetListener , year , month , day);
        datePickerDialog2.getDatePicker().setMaxDate(System.currentTimeMillis());

    }

    public void saveData(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(LO, Lastoil.getText().toString());
        editor.putString(LM, Lastmain.getText().toString());
        editor.apply();

    }

    public void skipData(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(LO, "");
        editor.putString(LM, "");
        editor.apply();

    }

    public void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        text = sharedPreferences.getString(LO, "");
        text2 = sharedPreferences.getString(LM, "");
    }

    public void UpdateView(){
        Lastoil.setText(text);
        Lastmain.setText(text2);
    }

    public void OpenDatePicker1(View view)
    {
        datePickerDialog.show();
    }

    public void OpenDatePicker2(View view)
    {
        datePickerDialog2.show();
    }
}