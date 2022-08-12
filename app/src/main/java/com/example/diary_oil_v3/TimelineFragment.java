package com.example.diary_oil_v3;

import static com.example.diary_oil_v3.CameraVieActivity.EVENT_LIST;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;

import java.util.List;

import event_class.Event;
import event_class.EventList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TimelineFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TimelineFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static final String SHARED_PREFS = "sharedPrefs";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TimelineFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TimelineFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TimelineFragment newInstance(String param1, String param2) {
        TimelineFragment fragment = new TimelineFragment();
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

    private RecyclerView rcvData;
    private EventAdapter eventAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_timeline, container, false);
        rcvData = view.findViewById(R.id.event_viewlist);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getActivity());
        rcvData.setLayoutManager(linearLayoutManager);
        eventAdapter = new EventAdapter(this.getContext(), getlistuser());
        rcvData.setAdapter(eventAdapter);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this.getContext(),DividerItemDecoration.VERTICAL);
        rcvData.addItemDecoration(itemDecoration);

        return view;
    }

    private List<Event> getlistuser() {
        Gson gson = new Gson();
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences(SHARED_PREFS, this.getActivity().MODE_PRIVATE);
        String json = sharedPreferences.getString(EVENT_LIST,"");

        EventList eventList;
        if (json=="")
        {eventList = new EventList();}
        else
        {
            eventList = gson.fromJson(json,EventList.class);
        }
        return eventList.buildTimeline();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (eventAdapter != null)
        {
            eventAdapter.release();
        }
    }
}