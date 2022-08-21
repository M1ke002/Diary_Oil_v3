package com.example.diary_oil_v3;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Calendar;

import env.Utils;
import event_class.DateTest;
import event_class.Event;
import event_class.EventList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public EventList init_list()
    {
        Gson gson =  new GsonBuilder().setDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").create();
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences(MainActivity.SHARED_PREFS, this.getActivity().MODE_PRIVATE);
        String json = sharedPreferences.getString(CameraVieActivity.EVENT_LIST,"");

        EventList eventList;
        if (json=="")
        {eventList = new EventList();}
        else
        {
            eventList = gson.fromJson(json,EventList.class);
        }
        return eventList;

    }

    private TextView a1,a2;
    private TextView welcome;

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
        View returnView = inflater.inflate(R.layout.fragment_home, container, false);
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences(MainActivity.SHARED_PREFS, this.getActivity().MODE_PRIVATE);
        String o1 = sharedPreferences.getString(CameraVieActivity.LAST_RECORD_ODO,"");
        String o2 = sharedPreferences.getString(CameraVieActivity.LAST_RECORD_DATE,"");
        a1 = (TextView) returnView.findViewById(R.id.last_record_1);
        a2 = (TextView) returnView.findViewById(R.id.last_record_2);
        a1.setText(o1);
        a2.setText(o2);
        welcome = (TextView) returnView.findViewById(R.id.welcome_text);
        String name = sharedPreferences.getString(On_Boa1.NAME,"");
        welcome.setText("Welcome back, "+ name);

        EventList eventList = init_list();
        DateTest predictor = eventList.init_predict();

        Calendar calendar = Calendar.getInstance();
        String predict = Utils.formatstring(String.valueOf(predictor.predict_by_date(calendar))) ;
        String today = Utils.Date_to_String(calendar.getTime());

        TextView a3 = (TextView) returnView.findViewById(R.id.next_record_1);
        TextView a4 = (TextView) returnView.findViewById(R.id.next_record_2);
        a3.setText(predict);
        a4.setText(today);



        return returnView;
    }
}