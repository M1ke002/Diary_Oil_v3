package com.example.diary_oil_v3;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import env.Utils;
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

            if (event.getInttype()==3)
            {
                tv5.setText("Price (VND)");
                tv3.setText(String.valueOf(event.cash)+ "VND");
                tv6.setVisibility(View.VISIBLE);
                tv6.setText("Price (VND) of each km:");
                tv4.setVisibility(View.VISIBLE);
                float c = event.cash/event.difference;
                tv4.setText(String.valueOf( c) + "VND");
            }
            else
            {
                tv5.setVisibility(View.INVISIBLE);
                tv6.setVisibility(View.INVISIBLE);
                tv4.setVisibility(View.INVISIBLE);
                tv3.setVisibility(View.INVISIBLE);
            }

        }
        else
        {
            tv4.setText(Integer.toString(event.getDistance()));
            tv3.setText(Integer.toString(event.getDays()));
            textView2.setText(Utils.Date_to_String(event.getDue()));
            //textView.setText(Utils.Date_to_String(event.getDue()));
            textView2.setTextColor(getColor(event.getColor()));
            ds.setVisibility(View.INVISIBLE);
            os.setVisibility(View.INVISIBLE);
            GridLayout gridLayout = findViewById(R.id.grid_snapshot);
            Iterator<Map.Entry<Date,Integer>> snap = event.Snap_date();
            while (snap.hasNext()) {

                Map.Entry<Date,Integer> vf = snap.next();

                String date = Utils.Date_to_String(vf.getKey()) ;
                String odo = vf.getValue().toString();
                Log.e("2345",date +"\n"+odo);
                TextView textView1 = new TextView(this.getApplicationContext());
                textView1.setText(date);
                textView1.setTypeface(null, Typeface.BOLD);
                textView1.setTextColor(Color.parseColor("#000000"));
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(60,10,0,0);
                textView1.setLayoutParams(params);

                TextView textView3 = new TextView(this.getApplicationContext());
                textView3.setText(odo);
                textView3.setTextColor(Color.parseColor("#63666A"));
                LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                params2.setMargins(520,10,200,0);
                params2.gravity = Gravity.END;
                textView3.setGravity(Gravity.RIGHT);
                textView3.setLayoutParams(params2);
                gridLayout.addView(textView1);
                gridLayout.addView(textView3);
            }


        }

    }
}