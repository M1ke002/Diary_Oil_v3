package com.example.diary_oil_v3;

import static com.example.diary_oil_v3.CameraVieActivity.EVENT_LIST;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.charts.Scatter;
import com.anychart.core.cartesian.series.Column;
import com.anychart.core.cartesian.series.Stick;
import com.anychart.core.scatter.series.Line;
import com.anychart.core.scatter.series.Marker;
import com.anychart.data.Mapping;
import com.anychart.data.Set;
import com.anychart.enums.Anchor;
import com.anychart.enums.HAlign;
import com.anychart.enums.HoverMode;
import com.anychart.enums.MarkerType;
import com.anychart.enums.Position;
import com.anychart.enums.ScaleStackMode;
import com.anychart.enums.TooltipDisplayMode;
import com.anychart.enums.TooltipPositionMode;
import com.anychart.graphics.vector.GradientKey;
import com.anychart.graphics.vector.SolidFill;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import event_class.Event;
import event_class.EventDataEntry;
import event_class.EventList;


public class ProfileFragment extends Fragment {


    public ProfileFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        AnyChartView anyChartView = view.findViewById(R.id.any_chart_view);
        anyChartView.setProgressBar(view.findViewById(R.id.progress_bar));
        Cartesian cartesian = AnyChart.column();

        List<DataEntry> data = new ArrayList<>();
        List<Event> dateset = getlistuser();
        Collections.reverse(dateset);
        Iterator<Event> it = dateset.iterator();
        int n=10;
        int i =0;
        while(it.hasNext() && i<n) {
            i++;
            Event element = it.next();


            switch (element.getType())
            {
                case "Record":
                    data.add(new EventDataEntry(element.date, Integer.parseInt( element.odo),null,null,null,element.getType()));
                    break;
                case "Oil Change":
                    data.add(new EventDataEntry(element.date,null, Integer.parseInt( element.odo),null,null,element.getType()));
                    break;
                case "Maintenance":
                    data.add(new EventDataEntry(element.date,null,null, Integer.parseInt( element.odo),null,element.getType()));
                    break;
                case "Fuel Change":
                    data.add(new EventDataEntry(element.date,null,null,null, Integer.parseInt( element.odo),element.getType()));
                    break;
                default:
                    break;
            }

        }

        Set set = Set.instantiate();
        set.data(data);
        Mapping series1Data = set.mapAs("{ x: 'x', value: 'value' }");
        Mapping series2Data = set.mapAs("{ x: 'x', value: 'value2' }");
        Mapping series3Data = set.mapAs("{ x: 'x', value: 'value3' }");
        Mapping series4Data = set.mapAs("{ x: 'x', value: 'value4' }");


        Column column1 = cartesian.column(series1Data);
        column1.name("Records");
        //column1.fill(new SolidFill("#56D7BC",1d));

        column1.hovered().stroke("3 #f7f3f3");


        Column column2 = cartesian.column(series2Data);
        column2.name("Oil Change");
        //column2.fill(new SolidFill("#44CCFC",1d));

        column2.hovered().stroke("3 #f7f3f3");

        Column column3 = cartesian.column(series3Data);
        column3.name("Maintenance");
        //column3.fill(new SolidFill("#BB86FC",1d));

        column3.hovered().stroke("3 #f7f3f3");

        Column column4 = cartesian.column(series4Data);
        column4.name("Fuel Change");
        //column4.fill(new SolidFill("#D75656",1d));

        column4.hovered().stroke("3 #f7f3f3");




        cartesian.animation(true);

        cartesian.yScale().stackMode(ScaleStackMode.VALUE);
        cartesian.title("Last 10 Records");
        cartesian.legend().enabled(true);

        cartesian.yScale().minimum(0d);
        cartesian.tooltip()
                .titleFormat("{%X}")
                .position(Position.CENTER_BOTTOM)
                .anchor(Anchor.CENTER_BOTTOM)
                .offsetX(0d)
                .offsetY(5d)
                .format("Odo: {%Value} Type: {%Record_type}");
        cartesian.yAxis(0).labels();

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);
        cartesian.interactivity().hoverMode(HoverMode.BY_X);

        cartesian.xAxis(0).title("Date");
        cartesian.yAxis(0).title("Odometer");

        anyChartView.setChart(cartesian);

        return view;
    }


    private List<Event> getlistuser() {
        Gson gson = new GsonBuilder().setDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").create();
        SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences(MainActivity.SHARED_PREFS, this.getActivity().MODE_PRIVATE);
        String json = sharedPreferences.getString(EVENT_LIST,"");

        EventList eventList;
        if (json=="")
        {eventList = new EventList();}
        else
        {
            eventList = gson.fromJson(json,EventList.class);
        }
        return (List<Event>) eventList.buildTimeline().clone();
    }

}