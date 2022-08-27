package com.example.diary_oil_v3;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import event_class.Event;

public class ItemDetailActivity extends AppCompatActivity {

    private TextView tv1,tv2,ds,os;
    private TextView tv3,tv4,tv5,tv6;
    private LinearLayout frameLayout;
    private Button button;
    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        Bundle bundle = getIntent().getExtras();
        if (bundle==null)
        {
            return;
        }

        button = findViewById(R.id.return_btn_5);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

            }
        });
        Event event = (Event) bundle.get("Object_item");
        frameLayout = findViewById(R.id.bg_color);
        tv3 = findViewById(R.id.length);
        tv4 = findViewById(R.id.distance);
        tv5 = findViewById(R.id.tag1);
        tv6 = findViewById(R.id.tag2);


        frameLayout.setBackgroundColor(getResources().getColor(event.getColor()));
        ds = findViewById(R.id.date_snap);
        ds.setText(event.date);
        os = findViewById(R.id.odo_snap);
        os.setText(event.odo);
        TextView a = findViewById(R.id.status_name);
        a.setText(event.getStatusName());
        TextView textView = findViewById(R.id.finish_date);
        textView.setText(event.date);
        TextView textView2 = findViewById(R.id.due);
        textView2.setText(event.date);
        tv1 = findViewById(R.id.textView10);
        tv1.setText(event.getType());
        imageView = findViewById(R.id.status_i);
        imageView.setImageResource(event.getStatus());
        if (event.getInttype()==0||event.getInttype()==3)
        {
            tv5.setVisibility(View.INVISIBLE);
            tv6.setVisibility(View.INVISIBLE);
            tv4.setText(Integer.toString(event.getDistance()));
            tv3.setText(Integer.toString(event.getDays()));

        }

    }
}