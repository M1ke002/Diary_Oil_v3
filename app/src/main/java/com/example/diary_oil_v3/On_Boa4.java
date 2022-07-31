package com.example.diary_oil_v3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class On_Boa4 extends AppCompatActivity {

    private ImageButton btn1;
    private Button btn2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setuppage4);

        btn1 = (ImageButton) findViewById(R.id.imageButton);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(On_Boa4.this, CameraVieActivity.class);
                startActivity(intent);
            }
        });

        btn2 = (Button) findViewById(R.id.return_btn_4);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(On_Boa4.this, On_Boa3.class);
                startActivity(intent);
            }
        });


    }
}