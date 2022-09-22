package com.example.diary_oil_v3;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SettingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SettingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SettingFragment newInstance(String param1, String param2) {
        SettingFragment fragment = new SettingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_setting, container, false);
        Button reset_btn = view.findViewById(R.id.reset_stuffs);
        reset_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               resetdata();
            }
        });

        Button onboa_btn = view.findViewById(R.id.onboa_stuffs);
        onboa_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            backtoonboard();
            }
        });


        Intent intent = new Intent(this.getContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this.getContext(), 0, intent, 0);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this.getContext(), MainActivity.CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_for_noti)
                .setContentTitle("Time for oil change !!!")
                .setContentText("Your bike need a oil change now")
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(getString(R.string.large_text)))
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        Button noti_btn = view.findViewById(R.id.notiboi);
        noti_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    putnoti(mBuilder);
            }
        });


        return view;
    }

    private void backtoonboard() {
        Intent intent = new Intent(this.getActivity(), On_Boa.class);
        startActivity(intent);
    }

    private void resetdata() {
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("sharedPrefs", this.getActivity().MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(CameraVieActivity.EVENT_LIST,"");
        editor.apply();

    }

    private void putnoti(NotificationCompat.Builder a)
    {
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this.getContext());
        notificationManager.notify(1234, a.build());

    }




}